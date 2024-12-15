package section2.generic

import javax.net.ssl.SSLParameters

fun main() {
//    Cage5<Int> -> 제한 조건 때문에 막힘

    val cage = Cage5(mutableListOf(Eagle(), Sparrow()))
    cage.printAfterSorting()
}

// class Cage5<T : Animal> -> 타입 파라미터의 상한(upper bound)을 Animal 으로 정할 수 있음
// class Cage5<T> where T : Animal, T : Comparable<T> -> 타입 파라미터의 상한을 Animal, Comparable 으로 정할 수 있음
class Cage5<T>(
    private val animals: MutableList<T> = mutableListOf()
) where T : Animal, T : Comparable<T> {


    fun getFirst(): T {
        return animals.first()
    }

    fun put(animal: T) {
        animals.add(animal)
    }

    fun moveFrom(otherCage: Cage5<T>) {
        animals.addAll(otherCage.animals)
    }

    fun moveTo(otherCage: Cage5<T>) {
        otherCage.animals.addAll(this.animals)
    }

    fun printAfterSorting() {
        this.animals.sorted()
            .map { it.name }
            .let { println(it) }
    }
}

abstract class Bird(
    name: String,
    private val size: Int,
) : Animal(name), Comparable<Bird> {
    override fun compareTo(other: Bird): Int {
        return this.size.compareTo(other.size)
    }
}

class Sparrow : Bird("참새", 100)
class Eagle : Bird("독수리", 500)


