package com.dtac.mobile.component

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import th.co.dtac.library.DtacProgressDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DtacProgressDialog.showProgress(this, true)
    }
}
