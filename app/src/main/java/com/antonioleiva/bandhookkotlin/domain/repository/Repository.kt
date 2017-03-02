package com.antonioleiva.bandhookkotlin.domain.repository

import com.antonioleiva.bandhookkotlin.Result
import com.antonioleiva.bandhookkotlin.recoverWith
import com.antonioleiva.bandhookkotlin.repository.datasource.DataSource
import org.funktionale.collections.tail

/**
 *
 */
interface Repository<E, A, ID> {
    val dataSources: List<DataSource<E, A, ID>>

    /**
     * Trampolined (async) recursion until all DataSources are exhausted or a right result is found
     */
    fun recurseDataSources(currentDataSources: List<DataSource<E, A, ID>>,
                           acc: Result<E, A>, f:
                           (DataSource<E, A, ID>) -> Result<E, A>): Result<E, A> =
            if (currentDataSources.isEmpty()) {
                acc
            } else {
                val current = currentDataSources.get(0)
                f(current).recoverWith {
                    recurseDataSources(currentDataSources.tail(), f(current), f)
                }
            }

    // TODO: The only missing bit to provide a default implementation for get is a constructor /
    // factory for the error
    fun get(id: ID): Result<E, A>
//            = recurseDataSources(
//                    currentDataSources = dataSources,
//                    acc = ResultT.raiseError<E, A>(E(id)),
//                    f = { it.get(id) }
//            )

}
