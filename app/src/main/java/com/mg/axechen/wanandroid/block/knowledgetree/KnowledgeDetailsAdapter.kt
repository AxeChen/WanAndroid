package com.mg.axechen.wanandroid.block.knowledgetree

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by AxeChen on 2018/4/9.
 */
class KnowledgeDetailsAdapter : FragmentStatePagerAdapter {

    private var fragments: MutableList<KnowledgeListFragment>? = null

    constructor(fm: FragmentManager?, fragments: MutableList<KnowledgeListFragment>) : super(fm) {
        this.fragments = fragments
    }

    override fun getItem(position: Int): Fragment {
        return fragments!![position]
    }

    override fun getCount(): Int {
        return fragments!!.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragments?.get(position)?.treeBean?.name + ""
    }

}