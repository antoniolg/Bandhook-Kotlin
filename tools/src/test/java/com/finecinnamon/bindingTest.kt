@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.finecinnamon

import org.junit.Test

class bindingTest {

    @Test
    fun bindPure() {
        bind<Exception, Int> {
            val one = it binds Result.pure<Exception, Int>(1)
            it returns one
        }
    }

    @Test
    fun bindAsync() {
        bind<Exception, Int> {
            val one = it binds Result.async {
                3 + 2
            }
            it returns one
        }
    }

    @Test
    fun bindAp() {
        bind<Exception, Int> {
            val one = it binds Result.ap(Result.pure<Exception, (Int) -> Int>({ it }), Result.pure(1))
            it returns one
        }
    }

    @Test
    fun bindDisjunctionLeft() {
        bind<Exception, Int> {
            val one = it binds Result.fromDisjunction(1.left()).swap()
            it returns one
        }
    }

    @Test
    fun bindAsyncDisjunctionLeft() {
        bind<Exception, Int> {
            val one = it binds Result.asyncOf {
                Thread.sleep(100)
                1.left()
            }.swap()
            it returns one
        }
    }

    @Test
    fun bindDisjunctionRight() {
        bind<Exception, Int> {
            val one = it binds Result.fromDisjunction(1.right())
            it returns one
        }
    }

    @Test
    fun bindAsyncDisjunctionRight() {
        bind<Exception, Int> {
            val one = it binds Result.asyncOf {
                Thread.sleep(100)
                1.right()
            }
            it returns one
        }
    }
}