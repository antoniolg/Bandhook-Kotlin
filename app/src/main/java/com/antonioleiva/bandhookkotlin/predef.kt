package com.antonioleiva.bandhookkotlin

import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.functional.bind
import nl.komponents.kovenant.then
import org.funktionale.collections.tail
import org.funktionale.either.Disjunction
import org.funktionale.either.flatMap
import org.funktionale.option.Option
import org.funktionale.option.getOrElse

/**
 * A Result is a deferred promise containing either a controlled error and successful value or an unknown exception.
 * The containing Disjunction is right biased on A
 */
typealias Result<E, A> = Promise<Disjunction<E, A>, Exception>

/**
 * A poor man's concrete EitherT like transformer over Promises due to lack of Higher Kinds in Kotlin
 */
object ResultT {

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
        return Promise.ofSuccess(fa)
    }

    /**
     * Raise an error placing it in the left of the contained disjunction on an already completed promise
     */
    fun <E, A> raiseError(e: E): Result<E, A> {
        return Promise.ofSuccess(Disjunction.left(e))
    }

    /**
     * Raise an unknown error placing it in the failed case on an already completed promise
     */
    fun <E, A> raiseUnknownError(e: Exception): Result<E, A> {
        return Promise.ofFail(e)
    }

    /**
     * fold over the contained disjunction contemplating both error and success cases
     */
    fun <E, A, B> fold(r: Result<E, A>, fe: (E) -> B, fa: (A) -> B): Promise<B, Exception> {
        return r.then { it ->
            it.fold(fl = { l -> fe(l) }, fr = { r -> fa(r) })
        }
    }

    /**
     * map over the right successful case
     */
    fun <E, A, B> map(r: Result<E, A>, fa: (A) -> B): Result<E, B> {
        return r.then { it ->
            it.map { fa(it) }
        }
    }

    /**
     * map over the left exceptional case
     */
    fun <E, A, EE> recover(r: Result<E, A>, fa: (E) -> EE): Result<EE, A> {
        return r.then { it ->
            it.swap().map { fa(it) }.swap()
        }
    }

    /**
     * map over the left exceptional case
     */
    fun <E, A> swap(r: Result<E, A>): Result<A, E> = r.then { it.swap() }


    /**
     * flatMap over the left exceptional case
     */
    fun <E, A, EE> recoverWith(r: Result<E, A>, fa: (E) -> Result<EE, A>): Result<EE, A> {
        return r.swap().flatMap { fa(it).swap() }.swap()
    }

    /**
     * Monadic bind allows sequential chains of promises
     */
    fun <E, A, B> flatMap(r: Result<E, A>, fa: (A) -> Result<E, B>): Result<E, B> {
        return r.bind {
            it.fold(fl = { l -> raiseError<E, B>(l) }, fr = { r -> fa(r) })
        }
    }

    /**
     * Side effects
     */
    fun <E, A> onComplete(
            r: Result<E, A>,
            onSuccess: (A) -> Unit,
            onError: (E) -> Unit,
            onUnhandledException: (Exception) -> Unit): Unit {
        r.success { it.fold(onError, onSuccess) } fail onUnhandledException
    }

    /**
     * Combine the results of r1 with r2 given both promises are successful
     * Since promises may have already started this operation is non-deterministic as there is
     * no guarantees as to which one finishes first.
     */
    fun <E, A, B> zip(r1: Result<E, A>, r2: Result<E, B>): Result<E, Pair<A, B>> {
        return r1.flatMap { a -> r2.map { b -> Pair(a, b) } }
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

fun <E, A> A.result(a: A): Result<E, A> {
    return ResultT.pure(a)
}

fun <E, A> E.raiseError(e: E): Result<E, A> {
    return ResultT.raiseError(e)
}

fun <E, A> Exception.raiseAsUnknownError(e: Exception): Result<E, A> {
    return ResultT.raiseUnknownError(e)
}

fun <E, A, B> Result<E, A>.fold(fe: (E) -> B, fa: (A) -> B): Promise<B, Exception> {
    return ResultT.fold(this, fe, fa)
}

fun <E, A> Result<E, A>.swap(): Result<A, E> {
    return ResultT.swap(this)
}

fun <E, A, B> Result<E, A>.map(fa: (A) -> B): Result<E, B> {
    return ResultT.map(this, fa)
}

fun <E, A> Result<E, A>.onComplete(
        onSuccess: (A) -> Unit,
        onError: (E) -> Unit,
        onUnhandledException: (Exception) -> Unit): Unit {
    ResultT.onComplete(this, onSuccess, onError, onUnhandledException)
}

fun <E, A, EE> Result<E, A>.recover(fa: (E) -> EE): Result<EE, A> {
    return ResultT.recover(this, fa)
}

fun <E, A, EE> Result<E, A>.recoverWith(fa: (E) -> Result<EE, A>): Result<EE, A> {
    return ResultT.recoverWith(this, fa)
}

fun <E, A, B> Result<E, A>.flatMap(fa: (A) -> Result<E, B>): Result<E, B> {
    return ResultT.flatMap(this, fa)
}

fun <E, A, B> Result<E, A>.zip(that: Result<E, B>): Result<E, Pair<A, B>> {
    return ResultT.zip(this, that)
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



