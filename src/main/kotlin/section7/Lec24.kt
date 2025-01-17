package section7

import java.lang.IllegalStateException
import kotlin.system.measureTimeMillis

class Lec24 {
}


//todo 해야 함
fun main() {
    repeat(3) {
        println("hello world")
    }

    // 시간 측정
    val measureTimeMillis = measureTimeMillis {
        val a = 1
        val b = 2
    }

    val result: Result<Int> = runCatching { 1/0 }

    TODO("main 함수 구현")

}

fun acceptOnlyTwo(num : Int) {
    require(num==2) {"2만 허용!"}

    if( num != 2){
        throw IllegalArgumentException("2만 허용!")
    }
}

class Person{
    val status: PersonStatus = PersonStatus.PLAYING

    fun sleep() {
        check(this.status == PersonStatus.PLAYING) {"에러 메시지"}
        if(this.status != PersonStatus.PLAYING) {
            throw IllegalStateException()
        }
    }
    enum class PersonStatus {
        PLAYING, SLEEPING
    }
}