package section2.generic

class Cage2<T> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T {
        return animals.first()
    }

    fun put(animal: T) {
        animals.add(animal)
    }

    // out : 무공변 -> 공변(변성을 줌), variance annotation 이라고 부름
    fun moveFrom(otherCage: Cage2<out T>) {
        otherCage.getFirst()
//        otherCage.put(Carp("잉어")) -> out이 붙으면 데이터 조회만 가능하고 데이터 저장은 불가능 -> otherCage는 생산자 역할만 가능
        animals.addAll(otherCage.animals)
    }

    // in : 무공변 -> 반공변 : 제네릭으로 상속관계를 유지하지만 역전됨 -> otherCage는 소비자(데이터 받기만 가능, 조회 불가, 저장만 가능)
    fun moveTo(otherCage: Cage2<in T>) {
        otherCage.animals.addAll(this.animals)
    }
}
