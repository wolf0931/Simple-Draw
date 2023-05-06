package com.simplemobiletools.draw.pro.activities

import android.content.Intent
import android.util.Log
import com.bytexero.tools.flashlight.utils.SaveUtil
import com.simplemobiletools.commons.activities.BaseSplashActivity
import com.simplemobiletools.draw.pro.R
import com.simplemobiletools.draw.pro.config.GMAdManagerHolder
import com.simplemobiletools.draw.pro.config.TTAdManagerHolder
import com.simplemobiletools.draw.pro.constant.Constant
import com.simplemobiletools.draw.pro.helpers.Config.Companion.newInstance
import com.simplemobiletools.draw.pro.http.RetrofitApi
import com.simplemobiletools.draw.pro.utils.APKVersionCodeUtils
import com.simplemobiletools.draw.pro.views.ServiceDialog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseSplashActivity() {
    override fun initActivity() {
        setContentView(R.layout.activity_splash)
        intView()
    }

    private fun intView() {
        lottieView.playAnimation()
        //加载开屏广告
        if (SaveUtil.getPrivate()) {
            ServiceDialog(this).builder()
                .setOnClickItemListener(object : ServiceDialog.OnClickItemListener {
                    override fun onItemClick() {
                        SaveUtil.setPrivate(false)
                        userOpen()
                    }
                }).setOnCancelListener(
                    object : ServiceDialog.OnCancelClickListener {
                        override fun onCancelClick() {
                            finish()
                        }
                    }).show()
        } else {
            userOpen()
        }
    }

    private fun userOpen() {
        val map: MutableMap<String, Any> = HashMap()
        map["appId"] = Constant.APP_ID
        map["channelName"] = APKVersionCodeUtils.getFlavor()
        Log.d("广告是否加载", "广告是否加载 json:\$postMap")
        RetrofitApi.init().userOpen(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ userOpen ->
                if (200 == userOpen.code) {
                    if (userOpen.data.status == "1") {
                        TTAdManagerHolder.init(this)
                        GMAdManagerHolder.init(this);
                        newInstance(applicationContext).ad = true
                        goToHomeSplashActivity()
                    } else {
                        newInstance(applicationContext).ad = false
                        goToMainActivity()
                    }
                } else {
                    newInstance(applicationContext).ad = false
                    goToMainActivity()
                }
            }) {
                newInstance(applicationContext).ad = false
                goToMainActivity()
            }
    }

    /**
     * 跳转到主页面
     */
    private fun goToMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        lottieView.cancelAnimation()
        finish()
    }

    private fun goToHomeSplashActivity() {
        val intent = Intent(this@SplashActivity, SplashAdActivity::class.java)
        startActivity(intent)
        lottieView.cancelAnimation()
        finish()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
    }
}
