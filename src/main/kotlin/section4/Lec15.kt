package section4

class Lec15 {
}

fun main() {
    repeat(2) { println("Hello world") }
    iterate(listOf(1, 2, 3, 4, 5)) { num ->
        if (num == 3) {
            // inline과 같이 사용 + 가장 가까운 main 함수가 리턴됨
            // non-local return
            return
        }
        println(num)
    }

    /*iterateWithCrossInline(listOf(1, 2, 3, 4, 5)) { num ->
        if (num == 3) {
            return //-> crossinline 으로 인해 non-local return 금지됨
        }
        println(num)
    }*/
}

inline fun iterate(numbers: List<Int>, exec: (Int) -> Unit) {
    for (number in numbers) {
        exec(number)
    }
}

inline fun iterateWithCrossInline(numbers: List<Int>, crossinline exec: (Int) -> Unit) {
    for (number in numbers) {
        exec(number)
    }
}

inline fun repeat(
    times: Int,
    noinline exec: () -> Unit
) {
    for (i in 1..times) {
        exec()
    }
}