@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.github.finecinnamon

import org.junit.Test

class bindingTest {

    @Test
    fun bindPure() {
        binding<Exception, Int> {
            val one = it binds Result.pure<Exception, Int>(1)
            it returns one
        }
    }

    @Test
    fun bindAsync() {
        binding<Exception, Int> {
            val one = it binds Result.async {
                3 + 2
            }
            it returns one
        }
    }

    @Test
    fun bindAp() {
        binding<Exception, Int> {
            val one = it binds Result.ap(Result.pure<Exception, (Int) -> Int>({ it }), Result.pure(1))
            it returns one
        }
    }

    @Test
    fun bindDisjunctionLeft() {
        binding<Exception, Int> {
            val one = it binds Result.fromDisjunction(1.left()).swap()
            it returns one
        }
    }

    @Test
    fun bindAsyncDisjunctionLeft() {
        binding<Exception, Int> {
            val one = it binds Result.asyncOf {
                Thread.sleep(100)
                1.left()
            }.swap()
            it returns one
        }
    }

    @Test
    fun bindDisjunctionRight() {
        binding<Exception, Int> {
            val one = it binds Result.fromDisjunction(1.right())
            it returns one
        }
    }

    @Test
    fun bindAsyncDisjunctionRight() {
        binding<Exception, Int> {
            val one = it binds Result.asyncOf {
                Thread.sleep(100)
                1.right()
            }
            it returns one
        }
    }
}
