package section4

class Lec14 {
}

fun main() {
    var num = 5
    num += 1
    val plusOne: () -> Unit = { num += 1 }
}