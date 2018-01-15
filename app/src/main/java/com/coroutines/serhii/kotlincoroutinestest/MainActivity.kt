package com.coroutines.serhii.kotlincoroutinestest

import android.arch.lifecycle.Lifecycle
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.coroutines.serhii.coroutinesexequtor.asyncUI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.experimental.delay

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            asyncUI {
                cancelEvent = Lifecycle.Event.ON_STOP
                val a = asyncNow {
                    delay(5000)
                    100
                }
                helloWorldText.text = "result is $a"
                startActivity(Intent(this@MainActivity, Main2Activity::class.java))
                Log.d("result", "result is $a")
            }
        }
    }
}
