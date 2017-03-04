package com.antonioleiva.bandhookkotlin.data

import com.antonioleiva.bandhookkotlin.Result
import org.funktionale.either.Disjunction
import retrofit2.Call

inline fun <T, U> Call<T>.unwrapCall(f: T.() -> U) = execute().body().f()

fun <E, T, U> Call<T>.asResult(f: T.() -> Disjunction<E, U>): Result<E, U> =
        Result.asyncOf {
            execute().body().f()
        }

fun <A> Call<A>.asyncResult(): Result<Nothing, A> =
        Result.async {
            execute().body()
        }