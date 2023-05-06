package com.simplemobiletools.draw.pro

import android.app.Application
import android.content.Context
import com.simplemobiletools.commons.extensions.checkUseEnglish

class App : Application() {

    companion object {
        var _context: Application? = null
        fun getContext(): Context {
            return _context!!
        }
    }
    override fun onCreate() {
        super.onCreate()
        _context = this
        checkUseEnglish()
    }
}
