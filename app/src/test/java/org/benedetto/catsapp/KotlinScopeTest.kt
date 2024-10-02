package org.benedetto.catsapp

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class KotlinScopeTest {


    /* Use let when you need to operate on the result of an expression, especially when nullability is involved.
    * Function   Object reference    Return value         Is extension function
        let         it               Lambda result        Yes
    *  */
    @Test
    fun letTest() {
        Person("Benedetto", 40, "Phoenix").let {
            assertTrue(it.age == 40)
            it.moveTo("London")
            it.incrementAge()

            assertFalse(it.age == 40)
            assertFalse(it.city == "Phoenix")
            assertTrue(it.age == 41)
            assertTrue(it.city == "London")
        }

        var person: Person? = Person("Kathleen", 27, "Phoenix")
        person?.let {

            assertTrue(it.name == "Kathleen")
            assertTrue(it.age == 27)
        }
    }

    /*Use run scope when you need to perform a series of actions with a block and return a result.
    * Function   Object reference    Return value         Is extension function
    * run         this               Lambda result        Yes
    * */
    @Test
    fun runTest() {
        val result = run {
            val x = 5
            val y = 10
            x + y
        }
        assertTrue(result == 15)

        val str = "Hello"
        str.run {
            assertTrue(this.length == 5)
        }

    }

    /* Use with when you have an object and want to perform multiple operations on it without needing to return the object itself.
        Function   Object reference    Return value         Is extension function
         with        this              Lambda result        No: takes the context object as an argument.
     */
    @Test
    fun withTest() {
        val person = Person("Joe", 45, "NYC")
        with(person) {
            assertTrue(this.age == 45)
        }
    }

    /* Use apply when you are setting properties on an object and want to return the object itself
     Function   Object reference    Return value         Is extension function
     apply       this                Context object      Yes
     */
    @Test
    fun applyTest() {
        val me = Person("Benedetto").apply {
            this.age = 40
            this.city = "Phoenix"
        }
        assertTrue(me.age == 40)
        assertFalse(me.city == "Scottsdale")
        assertTrue(me.name == "Benedetto")
    }

    /* Use also when you want to perform some additional operations (e.g, logging or validation) on an object before returning
        Function   Object reference    Return value         Is extension function
        also        it                  Context object      Yes
     */
    @Test
    fun alsoTest() {

        val person = Person("Person").also {
            assertTrue(it.name != "NoName")
        }
        assertTrue(person is Any)
        assertTrue(person is Person)

        var insideRef: Int = -1
        fun getRandomInt(): Int {
            return Random.nextInt(100).also {
                insideRef = it
            }
        }

        val i = getRandomInt()
        assertTrue(i == insideRef)

    }
}

/*
Scope function

The Kotlin standard library contains several functions whose sole purpose is to execute a block of code within the context of an object. When you call such a function on an object
with a lambda expression provided, it forms a temporary scope. In this scope, you can access the object without its name. Such functions are called scope functions.
There are five of them: let, run, with, apply, and also.

Basically, these functions all perform the same action: execute a block of code on an object.
What's different is how this object becomes available inside the block and what the result of the whole expression is

Function select

To help you choose the right scope function for your purpose, we provide this table that summarizes the key differences between them.

Function   Object reference    Return value         Is extension function
let         it                 Lambda result        Yes

run         this               Lambda result        Yes

run         -                  Lambda result        No: called without the context object

with        this               Lambda result        No: takes the context object as an argument.

apply       this                Context object      Yes

also        it                  Context object      Yes



Here is a short guide for choosing scope functions depending on the intended purpose:

    Executing a lambda on non-nullable objects: let

    Introducing an expression as a variable in local scope: let

    Object configuration: apply

    Object configuration and computing the result: run

    Running statements where an expression is required: non-extension run

    Additional effects: also

    Grouping function calls on an object: with

The use cases of different scope functions overlap, so you can choose which functions to use based on the specific conventions used in your project or team.

Although scope functions can make your code more concise, avoid overusing them: it can make your code hard to read and lead to errors. We also recommend that you avoid nesting scope functions and be careful when chaining them because it's easy to get confused about the current context object and value of this or it
 */