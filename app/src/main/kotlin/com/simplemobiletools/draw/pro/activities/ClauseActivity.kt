package com.simplemobiletools.draw.pro.activities

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import androidx.core.content.ContextCompat
import com.bytexero.tools.flashlight.view.click
import com.simplemobiletools.draw.pro.R
import kotlinx.android.synthetic.main.activity_clause.*
import kotlinx.android.synthetic.main.toolbar_layout.*

/**
 * 功能描述：
 * 创建作者：尉浩楠
 * 创建时间：2021/9/9.
 */
class ClauseActivity : SimpleActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clause)
        initView();
        start();
    }


    private fun initView() {
        toolbar_left_image_back?.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.black_back2)
        )
        toolbar?.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        toolbar_title?.text = "用户协议"
        toolbar_title?.setTextColor(ContextCompat.getColor(this, R.color.black))
        toolbar_left_image_back?.visibility = View.VISIBLE
        toolbar_left_image_back.click { onBackPressed() }

        val webSettings = webview.settings;

        webSettings.domStorageEnabled = true;//开启本地DOM存储

        webSettings.useWideViewPort = true; //将图片调整到适合webview的大小

        webSettings.loadWithOverviewMode = true; // 缩放至屏幕的大小

        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。

        webSettings.builtInZoomControls = true; //设置内置的缩放控件。若为false，则该WebView不可缩放

        webSettings.displayZoomControls = false; //隐藏原生的缩放控件

        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK; //关闭webview中缓存

        webSettings.allowFileAccess = true; //设置可以访问文件

        webSettings.javaScriptCanOpenWindowsAutomatically = true; //支持通过JS打开新窗口

        webSettings.loadsImagesAutomatically = true; //支持自动加载图片

        webSettings.defaultTextEncodingName = "utf-8";//设置编码格式

        webSettings.javaScriptCanOpenWindowsAutomatically =
            true;//设置js可以直接打开窗口，如window.open()，默认为false
        webSettings.javaScriptEnabled = true;//是否允许JavaScript脚本运行，默认为false。设置true时，会提醒可能造成XSS漏洞
        webSettings.setSupportZoom(true);//是否可以缩放，默认true
        webSettings.builtInZoomControls = true;//是否显示缩放按钮，默认false
        webSettings.useWideViewPort = true;//设置此属性，可任意比例缩放。大视图模式
        webSettings.loadWithOverviewMode = true;//和setUseWideViewPort(true)一起解决网页自适应问题
//        webSettings.setAppCacheEnabled(true);//是否使用缓存
        webSettings.domStorageEnabled = true;//开启本地DOM存储
        webSettings.loadsImagesAutomatically = true; // 加载图片
        webSettings.mediaPlaybackRequiresUserGesture = false;//播放音频，多媒体需要用户手动？设置为false为可自动播放

//        webview!!.loadUrl("https://dahui.beiyuqiye.com/baskstage/protocol_privacy.html")
//        webview!!.loadUrl("https://www.fzman.cn/private_send.html")
    }

    private fun start() {
        webview!!.loadUrl("http://www.bytexero.com/static/draw/xiaoxiong/user.html")
    }
}
