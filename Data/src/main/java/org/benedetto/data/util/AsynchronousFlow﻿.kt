package org.benedetto.data.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

class AsynchronousFlow{

    private fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            Thread.sleep(100) // pretend we are computing it in CPU-consuming way
            log("AsynchronousFlow: Emitting $i")
            emit(i) // emit next value
        }
    }.flowOn(Dispatchers.Default) // RIGHT way to change context for CPU-consuming code in flow builder

    public fun begin() {
        runBlocking<Unit> {
            simple().collect { value ->
                log("AsynchronousFlow $value")
            }
        }
    }
}

