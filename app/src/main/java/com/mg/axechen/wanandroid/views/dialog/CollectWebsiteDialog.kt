package com.mg.axechen.wanandroid.views.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.WanAndroidApplication
import kotlinx.android.synthetic.main.dialog_collect_website.*

/**
 * Created by AxeChen on 2018/5/27.
 */
class CollectWebsiteDialog : DialogFragment(), View.OnClickListener {

    public var listener: CollectWebSiteListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.dialog_collect_website, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initColor()
        initClickListener()
    }

    private fun initColor() {
        var color: Int = WanAndroidApplication.instance!!.getThemeColor(activity, WanAndroidApplication.instance!!.getTheme(activity)!!)
        ttConfirm.setTextColor(activity.resources.getColor(color))
        ttCancel.setTextColor(activity.resources.getColor(color))
    }

    private fun initClickListener() {
        ttCancel.setOnClickListener(this)
        ttConfirm.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.ttConfirm) {
            collectWebSite()
        } else if (v?.id == R.id.ttCancel) {
            edTitle.setText("")
            edLink.setText("")
            dismiss()
        }
    }

    private fun collectWebSite() {
        var title = edTitle.text.toString()
        var link = edLink.text.toString()
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(activity, getString(R.string.title_is_empty), Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(link)) {
            Toast.makeText(activity, getString(R.string.link_is_empty), Toast.LENGTH_SHORT).show()
            return
        }
        listener?.collectWebsite(title, link)
        dismiss()
    }

    interface CollectWebSiteListener {
        fun collectWebsite(title: String, link: String)
    }
}