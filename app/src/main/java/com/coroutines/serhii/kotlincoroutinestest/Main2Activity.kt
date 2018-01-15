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
            asyncUI { // this task will autoCancel on On_DESTROY event
                val measureTimeMillis = measureTimeMillis {
                    val number = asyncNow {
                        delay(100)
                        100
                    }
                    val someTask = promice {
                        delay(120)
                        200
                    }
                    val someTask1 = promice {
                        delay(130)
                        150
                    }
                    val someTask2 = promice {
                        delay(50)
                        140
                    }
                    println("result is $number ${someTask1.await()},${someTask.await()},${someTask2.await()}")
                }
                println("with total time of : $measureTimeMillis")
            }
        }
    }

}
