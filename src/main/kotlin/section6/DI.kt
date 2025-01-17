package section6

import org.reflections.Reflections
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.cast

class DI

object ContainerV1 {
    // 등록한 클래스 보관! = KClass 보관
    private val registeredClasses = mutableSetOf<KClass<*>>()

    fun register(clazz: KClass<*>) {
        registeredClasses.add(clazz)
    }

    fun <T : Any> getInstance(type: KClass<T>): T {
        return registeredClasses.firstOrNull { clazz -> clazz == type }
            ?.let { clazz -> clazz.constructors.first().call() as T }
            ?: throw IllegalArgumentException("해당 인스턴스 타입을 찾을 수 없습니다")
    }
}

fun start(clazz: KClass<*>) {
    val reflections = Reflections(clazz.packageName)
    val jClasses = reflections.getTypesAnnotatedWith(MyClass::class.java)
    jClasses.forEach {
        jClass -> ContainerV2.register(jClass.kotlin)
    }
}

private val KClass<*>.packageName: String
    get() {
        val qualifiedName = this.qualifiedName ?: throw IllegalArgumentException("")
        val hierarchy = qualifiedName.split(".")
        return hierarchy.subList(0,hierarchy.lastIndex).joinToString(".")
    }

object ContainerV2 {
    // 등록한 클래스 보관! = KClass 보관
    private val registeredClasses = mutableSetOf<KClass<*>>()
    private val cachedInstances = mutableMapOf<KClass<*>, Any>()

    fun register(clazz: KClass<*>) {
        registeredClasses.add(clazz)
    }

    fun <T : Any> getInstance(type: KClass<T>): T {
        if(type in cachedInstances) {
            return type.cast(cachedInstances[type])
        }

        val instance =  registeredClasses.firstOrNull { clazz -> clazz == type }
            ?.let { clazz -> instantiate(clazz) as T }
            ?: throw IllegalArgumentException("해당 인스턴스 타입을 찾을 수 없습니다.")
        cachedInstances[type] = instance
        return instance
    }

    private fun <T : Any> instantiate(clazz: KClass<T>): T {
        val constructor = findUsableConstructor(clazz)
        val params = constructor.parameters
            .map { param -> instantiate(param.type.classifier as KClass<*>) }
            .toTypedArray()
        return constructor.call(*params)
    }

    // clazz의 constructor 중, 사용 가능한 constructor
    // 사용 가능한 constructor -> constructor에 넣어야 하는 타입들이 모두 등록된 경우(컨테이너에서 관리하고 있는 경우)
    private fun <T : Any> findUsableConstructor(clazz: KClass<T>): KFunction<T> {
        return clazz.constructors.firstOrNull { constructor -> constructor.parameters.isAllRegistered }
            ?: throw IllegalArgumentException("사용할 수 있는 생성자가 없습니다.")
    }

    private val List<KParameter>.isAllRegistered: Boolean
        get() = this.all { it.type.classifier in registeredClasses }
}

fun main() {

    // v1
    ContainerV1.register(AService::class)
    val aService = ContainerV1.getInstance(AService::class)
    aService.print()

    // v2
    ContainerV2.register(AService::class)
    ContainerV2.register(BService::class)
    val bService = ContainerV2.getInstance(BService::class)
    bService.print()

    val reflections = Reflections("section6")
    val jClasses = reflections.getTypesAnnotatedWith(MyClass::class.java)

    // v3
    start(DI::class)
    val bService2 = ContainerV2.getInstance(BService::class)
    bService2.print()
}

annotation class MyClass

@MyClass
class AService {
    fun print() {
        println("A 서비스입니다.")
    }
}

@MyClass
class BService(
    private val aService: AService
){
    fun print() {
        this.aService.print()
    }
}