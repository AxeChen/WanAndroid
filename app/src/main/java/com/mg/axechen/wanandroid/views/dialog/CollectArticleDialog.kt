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
import kotlinx.android.synthetic.main.dialog_collect_article.*

/**
 * Created by AxeChen on 2018/5/27.
 */
class CollectArticleDialog : DialogFragment(), View.OnClickListener {

    public var clickListener: OnPositiveClickListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.dialog_collect_article, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initClickListener()
        initColor()
    }


    private fun initClickListener() {
        ttCancel.setOnClickListener(this)
        ttConfirm.setOnClickListener(this)
    }

    interface OnPositiveClickListener {
        fun onPositiveClick(title: String, author: String, edLink: String)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.ttConfirm) {
            collectArticle()
        } else if (v?.id == R.id.ttCancel) {
            edLink.setText("")
            edTitle.setText("")
            edAuthor.setText("")
            dismiss()
        }
    }

    fun collectArticle() {
        var title = edTitle.text.toString()
        var author = edAuthor.text.toString()
        var link = edLink.text.toString()
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(activity, getString(R.string.title_is_empty), Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(author)) {
            Toast.makeText(activity, getString(R.string.author_is_empty), Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(link)) {
            Toast.makeText(activity, getString(R.string.link_is_empty), Toast.LENGTH_SHORT).show()
            return
        }
        clickListener?.onPositiveClick(title, author, link)
        dismiss()
    }

    private fun initColor() {
        var color: Int = WanAndroidApplication.instance!!.getThemeColor(activity, WanAndroidApplication.instance!!.getTheme(activity)!!)
        ttConfirm.setTextColor(activity.resources.getColor(color))
        ttCancel.setTextColor(activity.resources.getColor(color))
    }
}