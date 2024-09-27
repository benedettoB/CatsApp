package org.benedetto.data.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/*Shared mutable state and concurrency
* Coroutines can be executed parallelly using a multi-threaded dispatcher like the Dispatchers.Default.
* It presents all the usual parallelism problems. The main problem being synchronization of access to shared mutable state.
* Some solutions to this problem in the land of coroutines are similar to the solutions in the multi-threaded world, but others are unique.
* */
object SharedMutableState {

    suspend fun massiveRun(action: suspend () -> Unit) {
        val n = 100  // number of coroutines to launch
        val k = 1000 // times an action is repeated by each coroutine
        val time = measureTimeMillis {
            coroutineScope { // scope for coroutines
                repeat(n) {
                    launch {
                        repeat(k) { action() }
                    }
                }
            }
        }
        log("Completed ${n * k} actions in $time ms")
    }


    private val counterContext = newSingleThreadContext("CounterContext")
    private var counter = 0
    @Volatile // Volatiles are of no help
    private var volatileCounter = 0
    private var atomicCounter = AtomicInteger()
    private val mutex = Mutex()
    private var mutexCounter = 0
    private var synchronizedCounter = 0
    private var synchronizedCounterTwo = 0

    fun beginFineGrainControl() = runBlocking {
        withContext(Dispatchers.Default) {
            massiveRun {
                // confine each increment to a single-threaded context
                withContext(counterContext) {
                    counter++
                }
            }
        }
        log("Fine: Counter = $counter")
    }

    fun beginCoarseGrainControl() = runBlocking {
        withContext(Dispatchers.Default) {
            massiveRun {
                    counter++
                    volatileCounter++
                    atomicCounter.incrementAndGet()
                    mutex.withLock {
                        mutexCounter++
                    }
                incrementSyncronizedCounter()
                synchronized(this){
                    synchronizedCounterTwo++
                }
            }
        }
        log("Coarse: Counter = $counter")
        log("volatileCounter = $volatileCounter")
        log("atomicCounter = $atomicCounter")
        log("mutexCounter = $mutexCounter")
        log("synchronizedCounter = $synchronizedCounter")
        log("synchronizedCounterTwo = $synchronizedCounterTwo")
    }

    @Synchronized
    private fun incrementSyncronizedCounter(){
        synchronizedCounter++
    }
}

/*
    course grain control:
     DefaultDi...-worker-5] Completed 100000 actions in 106 ms
     [DefaultDi...-worker-3] Coarse: Counter = 92384
     [DefaultDi...-worker-3] volatileCounter = 88472
     [DefaultDi...-worker-1] atomicCounter = 100000
     [DefaultDi...-worker-2] synchronizedCounter = 100000
     [DefaultDi...-worker-3] synchronizedCounterTwo = 100000

    fine grain control:
    [DefaultDi...-worker-3] Completed 100000 actions in 1428 ms
    [DefaultDi...-worker-1] Fine: Counter = 100000

    Mutex() Mutual exclusion solution to the problem is to protect all modifications of the shared state with a critical section that is never executed concurrently. In a blocking world you'd typically use synchronized or ReentrantLock for that. Coroutine's alternative is called Mutex. It has lock and unlock functions to delimit a critical section. The key difference is that Mutex.lock() is a suspending function. It does not block a thread.
 */



