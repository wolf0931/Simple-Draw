package com.simplemobiletools.draw.pro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.msdk.api.AdError;
import com.bytedance.msdk.api.v2.ad.splash.GMSplashAdListener;
import com.bytedance.msdk.api.v2.ad.splash.GMSplashAdLoadCallback;
import com.simplemobiletools.draw.pro.R;
import com.simplemobiletools.draw.pro.config.AdSplashManager;
import com.simplemobiletools.draw.pro.constant.Constant;

/**
 * 开屏广告Activity示例
 */

public class SplashAdActivity extends AppCompatActivity {
    public static final String EXTRA_FORCE_LOAD_BOTTOM = "extra_force_load_bottom";

    private static final String TAG = SplashActivity.class.getSimpleName();

    private FrameLayout mSplashContainer;
    private AdSplashManager mAdSplashManager;
    private boolean mForceLoadBottom;
    private GMSplashAdListener mSplashAdListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_splash);
        mSplashContainer = findViewById(R.id.splash_container);
        mForceLoadBottom = getIntent().getBooleanExtra(EXTRA_FORCE_LOAD_BOTTOM, false);
        initListener();
        initAdLoader();
        //加载开屏广告
        if (mAdSplashManager != null) {
            mAdSplashManager.loadSplashAd(Constant.cjs_kaiping);
        }
    }

    public void initListener() {
        mSplashAdListener = new GMSplashAdListener() {
            @Override
            public void onAdClicked() {
                Log.d(TAG, "onAdClicked");
            }

            @Override
            public void onAdShow() {
                Log.d(TAG, "onAdShow");
            }

            /**
             * show失败回调。如果show时发现无可用广告（比如广告过期），会触发该回调。
             * 开发者应该结合自己的广告加载、展示流程，在该回调里进行重新加载。
             * @param adError showFail的具体原因
             */
            @Override
            public void onAdShowFail(AdError adError) {
                Log.d(TAG, "onAdShowFail");
                // 开发者应该结合自己的广告加载、展示流程，在该回调里进行重新加载
                if (mAdSplashManager != null) {
                    mAdSplashManager.loadSplashAd(Constant.cjs_tt_xinchaping);
                }
            }

            @Override
            public void onAdSkip() {
                Log.d(TAG, "onAdSkip");
                goToMainActivity();
            }

            @Override
            public void onAdDismiss() {
                Log.d(TAG, "onAdDismiss");
                goToMainActivity();
            }
        };
    }


    public void initAdLoader() {
        mAdSplashManager = new AdSplashManager(this, mForceLoadBottom, new GMSplashAdLoadCallback() {
            @Override
            public void onSplashAdLoadFail(AdError adError) {
                Log.d(TAG, adError.message);
                Log.e(TAG, "load splash ad error : " + adError.code + ", " + adError.message);
                // 获取本次waterfall加载中，加载失败的adn错误信息。
                if (mAdSplashManager.getSplashAd() != null)
                    Log.d(TAG, "ad load infos: " + mAdSplashManager.getSplashAd().getAdLoadInfoList());
                goToMainActivity();
            }

            @Override
            public void onSplashAdLoadSuccess() {
                Log.e(TAG, "load splash ad success ");
                mAdSplashManager.printInfo();
                // 根据需要选择调用isReady()
//                if (mAdSplashManager.getSplashAd().isReady()) {
//                    mAdSplashManager.getSplashAd().showAd(mSplashContainer);
//                }
                mAdSplashManager.getSplashAd().showAd(mSplashContainer);

            }

            // 注意：***** 开屏广告加载超时回调已废弃，统一走onSplashAdLoadFail，GroMore作为聚合不存在SplashTimeout情况。*****
            @Override
            public void onAdLoadTimeout() {
            }
        }, mSplashAdListener);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdSplashManager != null) {
            mAdSplashManager.destroy();
        }
    }

    /**
     * 跳转到主页面
     */
    private void goToMainActivity() {
        Intent intent = new Intent(SplashAdActivity.this, MainActivity.class);
        startActivity(intent);
        mSplashContainer.removeAllViews();
        this.finish();
    }

}
