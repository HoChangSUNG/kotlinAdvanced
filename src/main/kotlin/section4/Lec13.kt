package section4

fun main() {
    //람다식
    compute(5, 3) { a, b -> a + b }

    //익명함수
    compute(5, 3, fun(a: Int, b: Int) = a + b)

    iterate(listOf(1, 2, 3, 4, 5), fun(num) {
        if (num == 3) {
            return
        }
        println(num)
    })

    val result = Operator.PLUS(1, 2)
    println(result)
}

fun compute(num1: Int, num2: Int, op: (Int, Int) -> Int): Int {
    return op(num1, num2)
}

//fun iterate(numbers: List<Int>, exec: (Int) -> Unit) {
//    for (number in numbers) {
//        exec(number)
//    }
//}

//fun calculate(num1: Int, num2: Int, oper: Operator) = oper.calcFun(num1, num2)
fun calculate(num1: Int, num2: Int, oper: Operator): Int = oper(num1, num2)

enum class Operator(
    private val oper: Char,
    val calcFun: (Int, Int) -> Int
) {
    PLUS('+', { a, b -> a + b }),
    MINUS('-', { a, b -> a - b }),
    MULTIPLY('*', { a, b -> a * b }),
    DEVIDE('/', { a, b ->
        if (b == 0) {
            throw IllegalArgumentException("0으로 나눌 수 없습니다!")
        } else {
            a / b
        }
    }),
    ;

    operator fun invoke(num1: Int, num2: Int): Int {
        return this.calcFun(num1, num2)
    }
}