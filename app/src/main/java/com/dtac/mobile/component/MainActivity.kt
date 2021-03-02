package com.dtac.mobile.component

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import th.co.dtac.library.textfield.DtacTextField

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<DtacTextField>(R.id.edtTest).addTextConditionsChangeListener {
            Log.d("Prew", "Text Change: $it")
        }
    }
}
