package section3

fun main() {
    val fruits = listOf(
        MyFruit("사과",1000L),
        MyFruit("바나나",3000L),
    )

//    val avg = fruits
//        .filter { it.name == "사과" }
//        .map { it.price }
//        .take(10_000)
//        .average()

    val avg = fruits.asSequence()
        .filter { it.name == "사과" }
        .map { it.price }
        .take(10_000)
        .average()
}

data class MyFruit(
    val name: String,
    val price: Long,
)