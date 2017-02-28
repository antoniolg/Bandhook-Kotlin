package com.antonioleiva.bandhookkotlin.data

import com.antonioleiva.bandhookkotlin.Result
import com.antonioleiva.bandhookkotlin.ResultT
import nl.komponents.kovenant.functional.bind
import nl.komponents.kovenant.task
import org.funktionale.either.Disjunction
import retrofit2.Call

inline fun <T, U> Call<T>.unwrapCall(f: T.() -> U) = execute().body().f()

fun <E, T, U> Call<T>.asResult(f: T.() -> Disjunction<E, U>): Result<E, U> {
    return task {
        execute().body().f()
    } bind { fa ->
        ResultT.fromDisjunction(fa)
    } fail { e ->
        ResultT.raiseUnknownError<Nothing, U>(e)
    }
}