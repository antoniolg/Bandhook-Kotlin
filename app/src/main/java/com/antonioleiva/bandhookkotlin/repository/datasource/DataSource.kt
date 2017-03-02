package com.antonioleiva.bandhookkotlin.repository.datasource

import com.antonioleiva.bandhookkotlin.Result

/**
 *
 */
interface DataSource<E, A, ID> {
    fun get(id: ID): Result<E, A>
}
