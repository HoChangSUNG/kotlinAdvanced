package section2.generic

fun main() {
//    val numbers: List = listOf(1, 2, 3) // raw Type으로는 생성 불가

    val num = 3
    num.toSuperString() // "Int: 3"

    val str = "ABC"
    str.toSuperString() // "String: ABC"


}

/*fun <T> T.toSuperString() {
     T가 무엇인지 런타임 때도 알 수 없기 때문에 오류가 난다
    println("${T::class.java.name}: $this")
}*/

inline fun <reified T> T.toSuperString() {
    println("${T::class.java.name}: $this")
}

inline fun <reified T> List<*>.hasAnyInstanceOf(): Boolean {
    return this.any { it is T }
}


fun checkList(data: Any) {
    // data가 List인지 확인 가능, 하지만 List<Int>인지 List<String>인지 확인 불가 -> 런타임에는 타입 소거되어서
    // * -> star projection (해당 타입 파라미터에 어떤 타입이 들어 있을지는 모른다는 의미)
    if (data is List<*>) {

    }
}

class TypeErase {
}
