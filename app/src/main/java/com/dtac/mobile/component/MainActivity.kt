package com.dtac.mobile.component

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import th.co.dtac.library.CustomProgressDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CustomProgressDialog.showProgress(this, true)
    }
}
