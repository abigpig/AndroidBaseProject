package com.renye.demo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder

open class BaseActivity : AppCompatActivity() {

    lateinit var butterKnifeBinder: Unbinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        butterKnifeBinder = ButterKnife.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        butterKnifeBinder.unbind()
    }
}