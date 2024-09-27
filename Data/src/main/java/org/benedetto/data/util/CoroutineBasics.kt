package org.benedetto.data.util

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

/*
A coroutine is an instance of a suspendable computation. It is conceptually similar to a thread, in the sense that it takes a block of code to run that works concurrently
with the rest of the code. However, a coroutine is not bound to any particular thread. It may suspend its execution in one thread and resume in another one.
Coroutines can be thought of as light-weight threads, but there is a number of important differences that make their real-life usage very different from threads.
 */
object CoroutineBasics {


    fun begin() = runBlocking {
        //firstCoroutine()
        launch { // launch a new coroutine and continue
            delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
            log("firstCoroutine() World!") // print after delay
        }
        log("firstCoroutine() Hello") // main coroutine continues while a previous one is delayed

        //secondCoroutine()
        launch {
            secondCoroutine()
        }
        log(" secondCoroutine() Hello")

        //thirdCoroutine()
        thirdCoroutine()
        log("thirdCoroutine() Done")

        //fourthCoroutine()
        val job = launch { // launch a new coroutine and keep a reference to its Job
            delay(1000L)
            log("fourthCoroutine() World!")
        }
        log("fourthCoroutine() Hello")
        job.join() // wait until child coroutine completes
        log("fourthCoroutine() Done")
    }

    fun fifthCoroutine() = runBlocking {
        val time = measureTimeMillis {
            repeat(10_000) { // launch a lot of coroutines
                launch {
                    delay(5000L)
                    log("fifthCoroutine() ")
                }
            }
        }
        log("fifthCoroutine() time to complete: $time")
    }

    fun threadingCompared(){
        val time = measureTimeMillis {
            repeat(10_000) { // launch a lot of coroutines
                thread {
                    Thread.sleep(5000L)
                    log("threadingCompared() ")
                }
            }
        }
        log("threadingCompared() time to complete: $time")
    }
    /*
        fifthCoroutine() time to complete: 278 ms
        threadingCompared() time to complete: 1409 ms
     */

    // Concurrently executes both sections
    private suspend fun thirdCoroutine() = coroutineScope { // this: CoroutineScope
        launch {
            delay(2000L)
            log("thirdCoroutine() World 2")
        }
        launch {
            delay(1000L)
            log("thirdCoroutine() World 1")
        }
        log("thirdCoroutine() Hello")
    }


    // this is your first suspending function
    private suspend fun secondCoroutine() {
        delay(1000L)
        log("secondCoroutine() World!")
    }

    /*
    secondCoroutine()
    extract the block of code inside firstCoroutine() into a separate function. When you perform "Extract function" refactoring on this code,
    you get a new function with the suspend modifier. This is a suspending function. Suspending functions can be used inside coroutines just like regular functions,
    but their additional feature is that they can, in turn, use other suspending functions (like delay in this example) to suspend execution of a coroutine.
     */

    /*
    firstCoroutine()
    OUTPUT:
               Hello
               World!
     */
    /*
        launch is a coroutine builder. It launches a new coroutine concurrently with the rest of the code, which continues to work independently.
        That's why Hello has been printed first.

        delay is a special suspending function. It suspends the coroutine for a specific time. Suspending a coroutine does not block the underlying thread,
        but allows other coroutines to run and use the underlying thread for their code.

        runBlocking is also a coroutine builder that bridges the non-coroutine world of a regular fun main() and the code with coroutines inside of runBlocking { ... } curly braces.
        This is highlighted in an IDE by this: CoroutineScope hint right after the runBlocking opening curly brace.
        The name of runBlocking means that the thread that runs it (in this case — the main thread) gets blocked for the duration of the call,
        until all the coroutines inside runBlocking { ... } complete their execution. You will often see runBlocking used like that at the very top-level of the
        application and quite rarely inside the real code, as threads are expensive resources and blocking them is inefficient and is often not desired.

        If you remove or forget runBlocking in this code, you'll get an error on the launch call, since launch is declared only on the CoroutineScope:

        Coroutines follow a principle of structured concurrency which means that new coroutines can only be launched in a specific CoroutineScope which delimits the lifetime of
        the coroutine. Structured concurrency ensures that they are not lost and do not leak. An outer scope cannot complete until all its children coroutines complete.
        Structured concurrency also ensures that any errors in the code are properly reported and are never lost.

             */
}




















