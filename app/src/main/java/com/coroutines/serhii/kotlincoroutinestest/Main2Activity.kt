package com.coroutines.serhii.kotlincoroutinestest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.coroutines.serhii.coroutinesexequtor.asyncUI
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.coroutines.experimental.delay
import kotlin.system.measureTimeMillis

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            asyncUI {
                // this task will autoCancel on On_DESTROY event
                val measureTimeMillis = measureTimeMillis {
                    //this call will block coroutine till gain current result
                    val number = asyncNow {
                        //this call will be executed in background thread context
                        BusinessLogicLayer().doSomeHeavyWorkWithResult1()
                    }
                    val someTask = promice {
                        BusinessLogicLayer().doSomeHeavyWorkWithResult2()
                    }
                    val someTask1 = promice {
                        BusinessLogicLayer().doSomeHeavyWorkWithResult3()

                    }
                    val someTask2 = promice {
                        BusinessLogicLayer().doSomeHeavyWorkWithResult4()
                    }
                    //this call will block coroutine while all of 3 other results will be available
                    //this call will be executed in UI thread context
                    println("result is $number ${someTask1.await()},${someTask.await()},${someTask2.await()}")
                }
                //total time should be near max delay time of all 3 calls
                println("with total time of : $measureTimeMillis")
            }
        }
    }

}
