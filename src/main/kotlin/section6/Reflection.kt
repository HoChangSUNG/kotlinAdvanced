package section6

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.cast
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.createType
import kotlin.reflect.full.hasAnnotation

@Target(AnnotationTarget.CLASS)
annotation class Executable

@Executable
class Reflection {

    fun a() {
        println("A입니다.")
    }

    fun b(n: Int) {
        println("B입니다.")
    }
}

fun executeAll(obj: Any) {
    val kClass = obj::class
    if ( !kClass.hasAnnotation<Executable>()) {
        return
    }

    // member 중에 함수만 필터링
    val callableFunctions = kClass.members.filterIsInstance<KFunction<*>>()
        .filter { it.returnType == Unit::class.createType() }
        // 인스턴스의 함수는 자기 자신을 기본적으로 넘겨서 파라미터 개수가 +1 -> 넘겨받은 타입이 자기 자신인지 확인
        .filter { it.parameters.size == 1 && it.parameters[0].type == kClass.createType() }

    callableFunctions.forEach { function -> function.call(kClass.createInstance()) }

}

fun main() { // KClass<T> Class<T>
    // kclass 얻는 법
    val kClass: KClass<Reflection> = Reflection::class

    val ref = Reflection()
    val kClass2: KClass<out Reflection> = ref::class

    val kClass3: KClass<out Any> = Class.forName("section6.Reflection").kotlin

    kClass.java // -> Class<Reflection>
    kClass.java.kotlin// -> KClass<Reflection>

    // KType -> 타입을 표현
    val kType: KType = GoldFish::class.createType()

    val goldFish = GoldFish("금붕이")
    goldFish::class.members.first { it.name == "print" }.call(goldFish)


    executeAll(Reflection())
}


class GoldFish(val name: String) {
    fun print() {
        println("금붕어 이름은 ${name} 입니다.")
    }
}

fun castToGoldFish(obj: Any):GoldFish {
    // obj를 GoldFish 로 캐스팅함( obj as GoldFish 와 동일)
    return GoldFish::class.cast(obj)
}