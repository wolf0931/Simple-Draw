package com.simplemobiletools.draw.pro.views

import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.view.Display
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bytexero.tools.flashlight.view.click
import com.simplemobiletools.commons.extensions.launchActivityIntent
import com.simplemobiletools.commons.helpers.ensureBackgroundThread
import com.simplemobiletools.draw.pro.R
import com.simplemobiletools.draw.pro.activities.ClauseActivity
import com.simplemobiletools.draw.pro.activities.PrivateActivity
import kotlinx.android.synthetic.main.layout_dialog_service.view.*


/**

 *
 *@Author: 残梦
 *@UpdateDate: 2021/10/9 11:11
 *@Description:
 */
class ServiceDialog {
    private var activity: Activity
    private lateinit var dialog: Dialog
    private var display: Display

    constructor(activity: Activity) {
        this.activity = activity
        val m = activity.window.windowManager
        display = m.defaultDisplay
    }


    fun builder(): ServiceDialog {

        // 获取Dialog布局
        val view =
            LayoutInflater.from(activity).inflate(R.layout.layout_dialog_service, null)
        // 设置Dialog最小宽度为屏幕宽度
        view.minimumWidth = display.width * 6 / 10

        // 定义Dialog布局和参数
        dialog = Dialog(activity, R.style.add_dialog)
        view.user.setOnClickListener {
            activity.startActivity(Intent(activity, ClauseActivity::class.java))
        }
        view.privacy.setOnClickListener {
            activity.startActivity(Intent(activity, PrivateActivity::class.java))
//            this.activity.launchViewIntent(link)
        }
        view.tvCancel.click {
            mOnCancelClickListener!!.onCancelClick()
            dialog.dismiss()
        }

        view.tvConfirm.click {
            mOnClickItemListener!!.onItemClick()
            dialog.dismiss()
        }
        dialog.setContentView(view)
        val dialogWindow = dialog.getWindow()
        dialogWindow!!.setGravity(Gravity.CENTER)
        dialogWindow.setLayout(
            ActionBar.LayoutParams.WRAP_CONTENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )
        val lp = dialogWindow.attributes
        lp.x = 0
        lp.y = 0
        dialogWindow.attributes = lp
        dialog.window!!.setLayout(
            dip2px(325),
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        return this
    }

    private fun Activity.launchViewIntent(s: String) {
        ensureBackgroundThread {
            Intent(Intent.ACTION_VIEW, Uri.parse(s)).apply {
                launchActivityIntent(this)
            }
        }
    }

    private fun dip2px(dpValue: Int): Int {
        val scale: Float = activity.resources.displayMetrics.density
        return (dpValue.toFloat() * scale + 0.5f).toInt()
    }


    fun show() {
        dialog.show()
    }

    fun setCancelable(cancel: Boolean): ServiceDialog {
        dialog.setCancelable(cancel)
        return this
    }

    fun setCanceledOnTouchOutside(cancel: Boolean): ServiceDialog {
        dialog.setCanceledOnTouchOutside(cancel)
        return this
    }

    public interface OnClickItemListener {
        fun onItemClick()
    }

    public interface OnCancelClickListener {
        fun onCancelClick()
    }

    public var mOnCancelClickListener: OnCancelClickListener? = null
    public fun setOnCancelListener(mOnCancelClickListener: OnCancelClickListener): ServiceDialog {
        this.mOnCancelClickListener = mOnCancelClickListener
        return this
    }

    public var mOnClickItemListener: OnClickItemListener? = null
    public fun setOnClickItemListener(mOnClickItemListener: OnClickItemListener): ServiceDialog {
        this.mOnClickItemListener = mOnClickItemListener
        return this
    }

}
