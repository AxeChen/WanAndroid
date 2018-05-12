package com.mg.axechen.wanandroid.views.viewpager

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import com.bumptech.glide.Glide


import com.mg.axechen.wanandroid.javabean.BannerBean

/**
 * Created by chen on 2017/4/4.
 */
class PhotoPagerAdapter(private val mContext: Context, private val mData: MutableList<BannerBean>, private val mIsMatch: Boolean) : PagerAdapter() {

    private var imageClickListener: ImageClickListener? = null

    fun setImageClickListener(imageClickListener: ImageClickListener) {
        this.imageClickListener = imageClickListener
    }

    override fun getCount(): Int {
        return mData.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val photoView: ImageView
        if (mIsMatch) {
            photoView = ImageView(mContext)
            photoView.scaleType = ScaleType.FIT_XY
        } else {
            photoView = ImageView(mContext)
        }
        Glide.with(mContext).load(mData[position].imagePath).into(photoView)
        container.addView(photoView, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT)
        photoView.setOnClickListener({
            imageClickListener?.onImageClickListener(mData[position])
        })
        return photoView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    interface ImageClickListener {
        fun onImageClickListener(bean: BannerBean)
    }
}
