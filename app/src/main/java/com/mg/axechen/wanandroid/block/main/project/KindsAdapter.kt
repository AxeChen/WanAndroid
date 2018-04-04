package com.mg.axechen.wanandroid.block.main.project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.javabean.TreeBean

/**
 * Created by AxeChen on 2018/4/2.
 *
 * 项目分类适配器
 */
class KindsAdapter(context: Context, data: List<TreeBean>) : BaseAdapter() {

    var context: Context? = null
    var data: List<TreeBean>? = null
    var inflater: LayoutInflater? = null

    init {
        this.context = context
        this.data = data
        inflater = LayoutInflater.from(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = null
        if (convertView == null) {
            view = inflater!!.inflate(R.layout.item_spinner_kinds, null)
        } else {
            view = convertView
        }
        val text: TextView = view!!.findViewById(R.id.tvText)
        text.text = data?.get(position)?.name
        return view!!
    }

    override fun getItem(position: Int): TreeBean? {
        return data?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data?.size!!
    }

    class SpinnerViewHolder {
        public var text: TextView? = null
    }

}