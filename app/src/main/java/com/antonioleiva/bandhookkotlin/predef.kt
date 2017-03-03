package com.antonioleiva.bandhookkotlin

import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.functional.bind
import nl.komponents.kovenant.task
import nl.komponents.kovenant.then
import org.funktionale.collections.tail
import org.funktionale.either.Disjunction
import org.funktionale.option.Option

/**
 * A Result is a deferred promise containing either a controlled error and successful value or an unknown exception.
 * The containing Disjunction is right biased on A
 */

class Result<E, A>(private val value: Promise<Disjunction<E, A>, Exception>) {

    /**
     * map over the right successful case
     */
    fun <B> map(fa: (A) -> B): Result<E, B> {
        return Result(value.then { it ->
            it.map { fa(it) }
        })
    }

    /**
     * map over the left exceptional case
     */
    fun <EE> recover(r: Result<E, A>, fa: (E) -> EE): Result<EE, A> {
        return Result(value.then { it ->
            it.swap().map { fa(it) }.swap()
        })
    }

    /**
     * map over the left exceptional case
     */
    fun swap(): Result<A, E> = Result(value.then { it.swap() })


    /**
     * flatMap over the left exceptional case
     */
    fun <EE> recoverWith(fa: (E) -> Result<EE, A>): Result<EE, A> =
            swap().flatMap { fa(it).swap() }.swap()


    /**
     * Monadic bind allows sequential chains of promises
     */
    fun <B> flatMap(fa: (A) -> Result<E, B>): Result<E, B> =
            Result(value.bind {
                it.fold(fl = { l -> raiseError<E, B>(l).value }, fr = { r -> fa(r).value })
            })

    /**
     * Side effects
     */
    fun onComplete(
            onSuccess: (A) -> Unit,
            onError: (E) -> Unit,
            onUnhandledException: (Exception) -> Unit): Unit {
        value.success { it.fold(onError, onSuccess) } fail onUnhandledException
    }

    /**
     * Combine the results of r1 with r2 given both promises are successful
     * Since promises may have already started this operation is non-deterministic as there is
     * no guarantees as to which one finishes first.
     */
    fun <B> zip(r2: Result<E, B>): Result<E, Pair<A, B>> {
        return flatMap { a -> r2.map { b -> Pair(a, b) } }
    }

    companion object Factory {

        fun <A> async(f: () -> A): Result<Nothing, A> = asyncOf { f().right() }

        fun <E, A> asyncOf(f: () -> Disjunction<E, A>): Result<E, A> =
                Result(task {
                    f()
                } bind { fa ->
                    fromDisjunction(fa).value
                } fail { e ->
                    raiseUnknownError<Nothing, A>(e).value
                })

        /**
         * Lift any value to the monadic context of a Result
         */
        fun <E, A> pure(a: A): Result<E, A> {
            return fromDisjunction(Disjunction.right(a))
        }

        /**
         * Lift any value to the monadic context of a Result
         */
        fun <E, A> fromDisjunction(fa: Disjunction<E, A>): Result<E, A> {
            return Result(Promise.ofSuccess(fa))
        }

        /**
         * Raise an error placing it in the left of the contained disjunction on an already completed promise
         */
        fun <E, A> raiseError(e: E): Result<E, A> {
            return Result(Promise.ofSuccess(Disjunction.left(e)))
        }

        /**
         * Raise an unknown error placing it in the failed case on an already completed promise
         */
        fun <E, A> raiseUnknownError(e: Exception): Result<E, A> {
            return Result(Promise.ofFail(e))
        }


        /**
         * Given a result and a function in the Promise context, applies the
         * function to the result returning a transformed result. Delegates to zip which is non-deterministic
         */
        fun <E, A, B> ap(ff: Result<E, (A) -> B>, fa: Result<E, A>): Result<E, B> {
            return fa.zip(ff).map { it.second(it.first) }
        }

        fun <E, A, B> firstSuccessIn(fa: List<B>,
                                     acc: Result<E, A>,
                                     f: (B) -> Result<E, A>): Result<E, A> =
                if (fa.isEmpty()) acc
                else {
                    val current = fa[0]
                    val result = f(current)
                    result.recoverWith {
                        firstSuccessIn(fa.tail(), result, f)
                    }
                }
    }

}

fun <E, A> A.result(a: A): Result<E, A> {
    return Result.pure(a)
}

fun <E, A> E.raiseError(e: E): Result<E, A> {
    return Result.raiseError(e)
}

fun <E, A> Exception.raiseAsUnknownError(e: Exception): Result<E, A> {
    return Result.raiseUnknownError(e)
}

fun <A, B> Option<A>.zip(that: Option<B>): Option<Pair<A, B>> {
    return this.flatMap { a -> that.map { b -> Pair(a, b) } }
}

fun <L> L.left(): Disjunction<L, Nothing> {
    return Disjunction.Left<L, Nothing>(this)
}

fun <R> R.right(): Disjunction<Nothing, R> {
    return Disjunction.Right<Nothing, R>(this)
}

class NonEmptyList<out A>(val head: A, vararg t: A) : Collection<A> {
    val tail: List<A> = t.toList()

    val all: List<A> = listOf(head, *t)

    inline fun <reified B> map(f: (A) -> B): NonEmptyList<B> =
            unsafeFromList(all.map(f))

    inline fun <reified B> flatMap(f: (A) -> List<B>): NonEmptyList<B> =
            unsafeFromList(all.flatMap(f))

    override val size: Int
        get() = all.size

    override fun contains(element: @UnsafeVariance A): Boolean =
            all.contains(element)

    override fun containsAll(elements: Collection<@UnsafeVariance A>): Boolean =
            all.containsAll(elements)

    override fun isEmpty(): Boolean = false

    override fun iterator(): Iterator<A> = all.iterator()

    companion object Factory {
        inline fun <reified A> of(h: A, vararg t: A): NonEmptyList<A> = NonEmptyList(h, *t)
        inline fun <reified A> of(h: A, t: List<A>): NonEmptyList<A> = NonEmptyList(h, *t.toTypedArray())
        inline fun <reified A> unsafeFromList(l: List<A>): NonEmptyList<A> = of(l[0], l.tail())
    }
}
