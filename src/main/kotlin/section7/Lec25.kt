package section7

import java.lang.RuntimeException
import kotlin.reflect.KClass

class Lec25 {
}

tailrec fun factorialV2(n: Int, curr: Int = 1): Int {
    return if (n <= 1) {
        curr
    } else {
        factorialV2(n - 1, n * curr)
    }
}


@JvmInline
value class Key(val key: String)

fun logic(n: Int) {
    when {
        n > 0 -> throw AException()
        n == 0 -> throw BException()
    }
}

class AException : RuntimeException()
class BException : RuntimeException()
class CException : RuntimeException()


fun main() {
    try {

    } catch (e: Exception) {
        when (e) {
            is AException,
            is BException -> TODO()

            is CException -> TODO()
        }
        throw e
    }

    runCatching { logic(10) }
        .onError(AException::class, BException::class) {
            println("A 또는 B 예외가 발생했습니다.")
        }.onError(CException::class) {

        }

//    val key = Key("비밀 번호")
//    println(key)
}

class ResultWrapper<T>(
    private val result: Result<T>,
    private val knownExceptions: MutableList<KClass<out Throwable>>,
) {
    fun toResult(): Result<T> {
        return this.result
    }

    fun onError(vararg exceptions: KClass<out Throwable>, action: (Throwable) -> Unit): ResultWrapper<T> {
        this.result.exceptionOrNull()?.let {
            if (it::class in exceptions && it::class !in this.knownExceptions) {
                action(it)
            }
        }
        return this
    }
}

fun <T> Result<T>.onError(vararg exceptions: KClass<out Throwable>, action: (Throwable) -> Unit): ResultWrapper<T> {
    exceptionOrNull()?.let {
        if (it::class in exceptions) {
            action(it)
        }
    }
    return ResultWrapper(this, exceptions.toMutableList())
}
