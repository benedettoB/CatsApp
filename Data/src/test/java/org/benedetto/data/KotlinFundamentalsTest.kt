package org.benedetto.data

import org.junit.Test

import org.junit.Assert.*
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class KotlinFundamentalsTest {


    val random: Int
        get() = Random.nextInt()

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun variableTests(){
        //var is a mutable reference (read-write) that can be updated after initialization: var is the normal(non-final) java variable
        var fruitVar: String = "orange"
        fruitVar = "banana"
        assertEquals("banana", fruitVar)

        //val is a read-only reference. Cannot be reassigned after initialization
        val fruitVal: String = "orange"
            //fruitVal = "banana" //error
        //val keyword can contain a custom getter, so technically it can return different objects on each access;
            //In other words, we can't guarantee that the reference to the underlying object is immutable
    }



}