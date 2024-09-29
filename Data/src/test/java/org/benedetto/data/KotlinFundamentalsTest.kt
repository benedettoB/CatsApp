package org.benedetto.data

import org.junit.Assert.*
import org.junit.Test
import java.lang.Integer.sum
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class KotlinFundamentalsTest {



    @Test
    fun varTests(){
        //var is a mutable reference (read-write) that can be updated after initialization: var is the normal(non-final) java variable
        var fruitVar: String = "orange"
        fruitVar = "banana"
        assertEquals("banana", fruitVar)

        var list = listOf(1,2,3)
        //list.remove(1) //error: no modify object state
        list = listOf(4,5,6)// but reference can change
        assertFalse(list.contains(1))
        assertTrue(list.contains(5))

        var mutableList = mutableListOf("a","b","c")
        mutableList.remove("a")
        assertFalse(mutableList.contains("a"))
        //mutableList = list //error: incompatible type
        mutableList = mutableListOf("d","e","f")

        assertTrue(mutableList.contains("d"))
        assertFalse(mutableList.contains("b"))

    }


    val random: Int
        get() = Random.nextInt()
    @Test
    fun valTests(){
        //val is a read-only reference. Cannot be reassigned after initialization
        val fruitVal: String = "orange"
        //fruitVal = "banana" //error

        //val keyword can contain a custom getter, so technically it can return different objects on each access
        //thus, we can't guarantee that the reference to the underlying object is immutable
        val number = random
        assertFalse(number == random)
        assertTrue(number != random)

    //when using val we can't reassign variable values, but we still may be able to modify properties of referenced objects
        //no modify reference & no modify object state
        val list = listOf(1,2,3)
        //list = listOf(4,5,6) error: no modify reference
        //list.remove(1) error: no modify object state
        assertTrue(list.contains(1))

        //no modify reference & yes modify object state
        val mutableList = mutableListOf("a", "b", "c")
        //mutableList = mutableListOf("d","e") error: no modify reference
        mutableList.remove("a")//yes modify object state

        assertFalse(mutableList.contains("a"))
        assertTrue(mutableList.contains("b"))

    }

    @Test
    fun typeInference(){
        //kotlin compiler will try to determine the best type for the variable from the current context. Current context = value assigned to the variable
        var title = "kotlin"
        assertTrue(title is String)
        //title = 12 // error: inferred type was String and we are trying to assign Int

        // if we want to assign Int(12) to the title variable then we need to specify title type to one that is a String and Int common type
        var newTitle: Any = "Kotlin"
        newTitle = 12

        //The type Any is an equivalent of the Java object type. It is the root of the Kotlin type hierarchy. All classes in Kotlin explicitly inherit from type Any,
        //even primitive types such as String or Int
        //Any defines three methods: equals, toString and hashCode

        //type inference is not limited to primitive values. Kotlin can infer types directly from functions
        var total = sum(10, 20)
        assertTrue(total is Int)

        var pair = "Everest" to 8848 //create pair using infix function 'to'
        assertTrue( pair is Pair<String, Int>)
        var pair2 = Pair("Everest", 8848)//create pair using constructor

        //type inference works also for more complex scenarios such as inferring type from an inferred type
        var map = mapOf("Mount Everest" to 8848, "K2" to 4017)
        assertTrue(map is Map<String,Int>)

        var map2 = mapOf("Mount Everest" to 8848, "K2" to "4017")
        assertTrue(map is Map<String, Any>)

    }

    @Test
    fun strictNullSafety(){
        //null types are not allowed unless they explicitly permitted
        val name: String? = null
        //val age: Int = null //error:
        assertNull(name)

        //we are not allowed to call a method on a potentially nullable object, unless a nullity check is performed before a call
        //name.toUpperCase()//error, this reference may be null

        /*Every non-nullable type in Kotlin has its nullable type equivalent: Int has Int?, String has String? and so on
        The same rule applies for all classes in the Android framework (View has View?), third party libraries
        (OkHttpClient has OkHttpClient?). This means that every non-generic type is also a subtype of its nullable equivalent.
        For example Vehicle, as well as being a subtype of Vehicle? is also a subtype of Any (Any is a subtype of Any?)
         */

        //When defining generic types, there are multiple possibilities for nullability
        var list1: ArrayList<Int> // list1 can't be null nor can it's elements contain null
        var list2: ArrayList<Int>?// list2 can be null but it's elements can't be null
        var list3: ArrayList<Int?>// list3 can't be null but it's elements can be null
        var list4: ArrayList<Int?>?// list4 and it's elements can be null

        val name2: String? = null
            //name2.toUpperCase(): error, this reference may be null



    }

}







