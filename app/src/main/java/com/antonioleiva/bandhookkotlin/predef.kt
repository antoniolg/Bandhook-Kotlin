package com.antonioleiva.bandhookkotlin

import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.functional.*
import nl.komponents.kovenant.then
import org.funktionale.either.Disjunction

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
        return Promise.ofSuccess(Disjunction.right(a))
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
    fun <E, A, EE> mapLeft(r: Result<E, A>, fa: (E) -> EE): Result<EE, A> {
        return r.then { it ->
            it.swap().map { fa(it) }.swap()
        }
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
     * Combine the results of r1 with r2 given both promises are successful
     */
    fun <E, A, B> zip(r1: Result<E, A>, r2: Result<E, B>): Result<E, Pair<A, B>> {
        return r1.flatMap { a -> r2.map { b -> Pair(a, b) } }
    }

    /**
     * Given a result and a function in the Promise context, applies the
     * function to the result returning a transformed result.
     */
    fun <E, A, B> ap(ff: Result<E, (A) -> B>, fa: Result<E, A>): Result<E, B> {
        return fa.zip(ff).map { it.second(it.first) }
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

fun <E, A, B> Result<E, A>.map(fa: (A) -> B): Result<E, B> {
    return ResultT.map(this, fa)
}

fun <E, A, EE> Result<E, A>.mapLeft(fa: (E) -> EE): Result<EE, A> {
    return ResultT.mapLeft(this, fa)
}

fun <E, A, B> Result<E, A>.flatMap(fa: (A) -> Result<E, B>): Result<E, B> {
    return ResultT.flatMap(this, fa)
}

fun <E, A, B> Result<E, A>.zip(that: Result<E, B>): Result<E, Pair<A, B>> {
    return ResultT.zip(this, that)
}