package section2.generic

fun main() {
    // 제네릭 x
    val cage = Cage()
    cage.put(Carp("잉어"))
    val carp: Carp = cage.getFirst() as Carp


    // 제네릭 o
    val cage2 = Cage2<Carp>()
    cage2.put(Carp("잉어"))
    val carp2: Carp = cage2.getFirst()

    val goldFishCage = Cage2<GoldFish>()
    goldFishCage.put(GoldFish("금붕어"))

    val fishCage = Cage2<Fish>()
    fishCage.moveFrom(goldFishCage) // 컴파일 에러 사라짐
    val fish: Fish = fishCage.getFirst()


    val fishCage2 = Cage2<Fish>()

    val goldFishCage2 = Cage2<GoldFish>()
    goldFishCage2.put(GoldFish("금붕어"))
    goldFishCage2.moveTo(fishCage2) // 컴파일 에러 사라짐

    // 공변(co-variance) : 타입 파라미터의 상속 관계가 제네릭 클래스에서 유지
    // 반공변(contra-variance) : 타입 파라미터의 상속 관계가 제네릭 클래스에서 유지되지만 반대로 유지됨
}

class Cage {
    private val animals: MutableList<Animal> = mutableListOf()

    fun getFirst(): Animal {
        return animals.first()
    }

    fun put(animal: Animal) {
        animals.add(animal)
    }

    fun moveFrom(cage: Cage) {
        animals.addAll(cage.animals)
    }
}


