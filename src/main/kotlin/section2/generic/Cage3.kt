package section2.generic

fun main() {
    val fishCage = Cage3<Fish>()
    val animalCage: Cage3<Animal> = fishCage // Cage3 클래스에 out 적용
}

// 생산만 하는 클래스 -> 클래스 자체를 공변나게 만듦
class Cage3<out T> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T {
        return this.animals.first()
    }

    fun getAll(): List<T> {
        return this.animals
    }
}
