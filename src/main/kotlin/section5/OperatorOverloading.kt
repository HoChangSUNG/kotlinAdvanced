package section5

import java.time.LocalDate

class OperatorOverloading {
}

data class Point(
    val x: Int,
    val y: Int,
) {
    fun zeroPointSymmetry(): Point = Point(-x, -y)

    operator fun unaryMinus(): Point {
        return Point(-x, -y)
    }

    // 다른 타입 리턴 가능
    operator fun unaryPlus(): Int {
        return x
    }

    operator fun inc(): Point = Point(x + 1, y + 1)

    operator fun dec(): Point = Point(x + 1, y + 1)
}

data class Days(val day:Long)

operator fun LocalDate.plus(days: Days):LocalDate {
    return this.plusDays(days.day)
}

val Int.d: Days
    get() = Days(this.toLong())

fun main() {
    var point = Point(20, -10)
    println(point.zeroPointSymmetry())
    println(-point)
    println(++point)

    LocalDate.of(2023,1,1) + Days(3)
    LocalDate.of(2023,1,1) + 3.d
}