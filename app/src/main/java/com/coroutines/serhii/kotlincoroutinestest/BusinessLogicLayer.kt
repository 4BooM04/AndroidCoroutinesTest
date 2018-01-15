package com.coroutines.serhii.kotlincoroutinestest

import kotlinx.coroutines.experimental.delay

/**
 * Created by Serhii Chaban sc@madappgang.com on 15.01.18.
 */
class BusinessLogicLayer {
    suspend fun doSomeHeavyWorkWithResult(): Int {
        delay(5000)
        return 100
    }

    suspend fun doSomeHeavyWorkWithResult1(): Int {
        delay(100)
        return 1
    }

    suspend fun doSomeHeavyWorkWithResult2(): Int {
        delay(130)
        return 2
    }

    suspend fun doSomeHeavyWorkWithResult3(): Int {
        delay(140)
        return 3
    }

    suspend fun doSomeHeavyWorkWithResult4(): Int {
        delay(150)
        return 4
    }
}