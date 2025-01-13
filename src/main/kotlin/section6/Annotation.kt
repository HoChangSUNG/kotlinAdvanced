package section6

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME) // default
@Target(AnnotationTarget.CLASS)
@Repeatable
annotation class Shape(
    val texts: Array<String>
)

@Shape(texts = ["1","2"])
@Shape(texts = ["1","2"]) // @Repeatable -> @Shape 여러 개 붙을 수 있음
class Annotation {
}

fun main() {
    val clazz: KClass<Annotation> = Annotation::class

}