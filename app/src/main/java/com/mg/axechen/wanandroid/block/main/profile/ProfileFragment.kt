package com.mg.axechen.wanandroid.block.main.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.theme.ChangeThemeActivity
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Created by AxeChen on 2018/4/2.
 * 个人详情页
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater!!.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initClickListener()
    }

    private fun initClickListener() {
        rlTheme.setOnClickListener { view ->
            ChangeThemeActivity.launch(activity)
        }
    }

}