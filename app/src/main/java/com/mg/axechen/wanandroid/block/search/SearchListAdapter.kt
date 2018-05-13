package com.mg.axechen.wanandroid.block.search

import android.content.Context
import android.support.v7.widget.ActionMenuView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mg.axechen.wanandroid.FlowLayout
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.javabean.HomeData
import com.mg.axechen.wanandroid.javabean.SearchTag
import com.mg.axechen.wanandroid.javabean.SearchViewType

/**
 * Created by AxeChen on 2018/4/10.
 * 搜索列表适配器
 */
class SearchListAdapter : BaseMultiItemQuickAdapter<SearchViewType, BaseViewHolder> {

    var layoutInflater: LayoutInflater? = null

    var recommendClickListener: RecommendClickListener? = null


    constructor(data: MutableList<SearchViewType>?, context: Context) : super(data) {
        // 添加selection
        addItemType(SearchViewType.VIEW_TYPE_SELECTION, R.layout.item_home_selection)
        addItemType(SearchViewType.VIEW_TYPE_RECOMMEND, R.layout.item_search_recommend)
        addItemType(SearchViewType.VIEW_TYPE_HISTORY, R.layout.item_search_history)
        addItemType(SearchViewType.VIEW_TYPE_RESULT, R.layout.item_collect_article)
        addItemType(SearchViewType.VIEW_TYPE_HISTORY_SELECTION, R.layout.item_seach_history_selection)

        layoutInflater = LayoutInflater.from(context)
    }

    override fun convert(helper: BaseViewHolder?, item: SearchViewType?) {
        when {
            item?.itemType == SearchViewType.VIEW_TYPE_SELECTION -> helper!!.setText(R.id.tvSelection, item.item as String)
            item?.itemType == SearchViewType.VIEW_TYPE_RECOMMEND -> {
                var flowLayout: FlowLayout = helper!!.getView(R.id.flowLayout)
                var tags: MutableList<SearchTag> = item!!.item as MutableList<SearchTag>

                var views = mutableListOf<View>()
                for (tag in tags) {

                    val textView = layoutInflater!!.inflate(R.layout.item_flowlayout, null) as TextView
                    textView.text = tag.name
                    textView.id = tag.id
                    val margin = ViewGroup.MarginLayoutParams(ActionMenuView.LayoutParams.WRAP_CONTENT, ActionMenuView.LayoutParams.WRAP_CONTENT)
                    margin.rightMargin = 10
                    margin.topMargin = 10
                    margin.leftMargin = 10
                    margin.bottomMargin = 10
                    textView.layoutParams = margin
                    views.add(textView)

                    textView.setOnClickListener({
                        recommendClickListener?.recommendClick(tag)
                    })
                }
                flowLayout.addItems(views)

            }
            item?.itemType == SearchViewType.VIEW_TYPE_HISTORY -> {
                helper!!.setText(R.id.tvName, item!!.item as String)
            }
            item?.itemType == SearchViewType.VIEW_TYPE_HISTORY_SELECTION -> {
                helper?.addOnClickListener(R.id.tvClear)
            }
            else -> {
                var homeData = item!!.item as HomeData
                helper?.setText(R.id.ttTvName, homeData.author)
                helper?.setText(R.id.tvContent, Html.fromHtml(homeData.title))
                helper?.setText(R.id.tvTime, homeData.niceDate)
                var like: ImageView = helper!!.getView(R.id.ivLike)
                like.visibility = View.GONE
            }
        }
    }

    interface RecommendClickListener {
        fun recommendClick(tag: SearchTag)
    }

}