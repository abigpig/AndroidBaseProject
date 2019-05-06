package com.renye.demo

import android.support.multidex.MultiDexApplication

class RenYeApplication:MultiDexApplication() {

    //MultiDexApplication  :: 方法数越界的解决方案 https://www.cnblogs.com/chenxibobo/p/6076459.html
    override fun onCreate() {
        super.onCreate()
    }
}