package com.coroutines.serhii.kotlincoroutinestest

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    //default test case
    @Test
    fun checkLogicFlow() {
        runBlocking() {
            val doSomeHeavyWorkResult = BusinessLogicLayer().doSomeHeavyWorkWithResult()
            assertEquals(100, doSomeHeavyWorkResult)
        }
    }
    //use this test way to test suspend function with only bg available operations
    @Test
    fun checkNetworkCall() {
        runBlocking() {
            val networkCallResult = async(CommonPool) { BusinessLogicLayer().doSomeHeavyWorkWithResult() }.await()
            assertEquals(100, networkCallResult)
        }
    }
}
