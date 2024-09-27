package org.benedetto.data.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class AsynchronousFlow {

    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // pretend we are doing something useful here
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // pretend we are doing something useful here, too
        return 29
    }

    fun testAsync() = runBlocking {
        val time = measureTimeMillis {
            val one = async {
                doSomethingUsefulOne()

            }
            val two = async {
                doSomethingUsefulTwo()
            }
            log("AsynchronousFlow: The answer is ${one.await() + two.await()}")
        }
        log("AsynchronousFlow: Completed in $time ms")
        /*
        output:
      AsynchronousFlow: The answer is 42
      AsynchronousFlow: Completed in 1002 ms
         */
    }


    fun test() = runBlocking {
        val time = measureTimeMillis {
            val one = doSomethingUsefulOne()
            val two = doSomethingUsefulTwo()
            log("NonAsynchronousFlow: The answer is ${one + two}")
        }
        log("NonAsynchronousFlow: Completed in $time ms")

        /*
        output:
      NonAsynchronousFlow: The answer is 42
      NonAsynchronousFlow: Completed in 2001 ms
         *
         */
    }


    private fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            Thread.sleep(100) // pretend we are computing it in CPU-consuming way
            log("AsynchronousFlow: Emitting $i")
            emit(i) // emit next value
        }
    }.flowOn(Dispatchers.Default) // RIGHT way to change context for CPU-consuming code in flow builder

    public fun beginSimple() {
        runBlocking<Unit> {
            simple().collect { value ->
                log("AsynchronousFlow $value")
            }
        }
    }
}

/*
runBlocking{}
Runs a new coroutine and blocks the current thread interruptibly until its completion.
This function should not be used from a coroutine. It is designed to bridge regular blocking code to libraries that are written in suspending style,
to be used in main functions and in tests.
 */

