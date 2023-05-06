package com.bytexero.tools.flashlight.utils

import com.simplemobiletools.draw.pro.App
import com.simplemobiletools.draw.pro.constant.Constant
import com.simplemobiletools.draw.pro.utils.MySharedPreferences


/**
 * 功能描述：
 * 创建作者：尉浩楠
 * 创建时间：2021/9/2.
 */
object SaveUtil {
    private val sharedPreferencesUtil = MySharedPreferences.SharedPreferencesUtil.getInstance(
        App.getContext()
    )

    /**
     * private
     */
    fun setPrivate(private: Boolean) {
        sharedPreferencesUtil.saveData(Constant.IS_FIRST_START, false)

    }


    fun getPrivate(): Boolean {
        return sharedPreferencesUtil.getData(Constant.IS_FIRST_START, true) as Boolean
    }

}
