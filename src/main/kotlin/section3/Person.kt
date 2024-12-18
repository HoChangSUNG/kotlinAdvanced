package section3

import kotlin.reflect.KProperty

// lateinit -> 초기화를 지연시킨 변수, 초기화 로직이 여러 곳에 위치할 수 있음/ 초기화 없이 호출하면 예외 발생
//  lazy    -> 초기화를 get 호출 전으로 지연시킨 변수, 초기화 로직은 변수 선언과 동시에 한 곳에만 위치할 수 있

class Person {
    lateinit var name: String

    val isKim: Boolean
        get() = this.name.startsWith("김")

    val maskingName: String
        get() = name[0] + (1 until name.length).joinToString("") { "*" }
}

class Person2 {
    // backing property
    private var _name: String? = null
    val name: String
        get() {
            if (_name == null) {
                Thread.sleep(2_000L)
                this._name = "김수한무"
            }
            return this._name!!
        }
}

// by lazy 사용, Person2의 name getter를 아래처림 쉽게 표현 가능
class Person3 {
    // name의 getter가 최초 호출될 때 실행되고, 기본적으로 thread-safe
//    val name: String by Lazy {
//        Thread.sleep(2_000)
//        "김수한무"
//    }

    val name: String by LazyInitProperty {
        Thread.sleep(2_000)
        "김수한무"
    }
}

class Person3Template {
    private val delegateProperty = LazyInitProperty{
        Thread.sleep(2_000)
        "김수한무"
    }

    val name: String
        get() = delegateProperty.value
}

class LazyInitProperty<T>(val init: () -> T) {
    private var _value: T? = null
    val value: T
        get() {
            if (_value == null) {
                this._value = init()
            }
            return _value!!
        }

    operator fun getValue(thsRef: Any, property: KProperty<*>): T{
        return value
    }
}

