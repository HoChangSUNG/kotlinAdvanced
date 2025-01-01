package section4


fun main() {
    val filter: StringFilter = object : StringFilter {
        override fun predicate(str: String?): Boolean {
            return str?.startsWith("A") ?: false
        }
    }

    // 람다식을 이용해 바로 인스턴스화는 불가능 -> SAM 생성자 사용
    val filter2: StringFilter = StringFilter { s -> s.startsWith("A") }
    // 파라미터로 넘기는 경우는 SAM 생성자 말고 바로 람다식 넘겨도 인스턴스화 가능
    consumeFilter { s -> s.startsWith("A") }
}

fun consumeFilter(filter: StringFilter) {}