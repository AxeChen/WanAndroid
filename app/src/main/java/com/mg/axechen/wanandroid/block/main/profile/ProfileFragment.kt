package com.mg.axechen.wanandroid.block.main.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseFragment
import com.mg.axechen.wanandroid.block.collect.CollectActivity
import com.mg.axechen.wanandroid.block.login.LoginActivity
import com.mg.axechen.wanandroid.block.main.AboutUsActivity
import com.mg.axechen.wanandroid.theme.ChangeThemeActivity
import com.mg.axechen.wanandroid.utils.ApkVersionUtils
import com.mg.axechen.wanandroid.utils.SharePreferencesContants
import com.mg.axechen.wanandroid.utils.SharedPreferencesUtils
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Created by AxeChen on 2018/4/2.
 * 个人详情页
 */
class ProfileFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater!!.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerLoginStatusReceiver()
        initClickListener()
        initData()
    }

    private fun initClickListener() {
        rlTheme.setOnClickListener { view ->
            ChangeThemeActivity.launch(activity)
        }

        rlCollect.setOnClickListener { view ->
            if (SharedPreferencesUtils.getInt(SharePreferencesContants.USER_ID) == 0) {
                Toast.makeText(activity, getString(R.string.pls_login), Toast.LENGTH_SHORT).show()
            } else {
                CollectActivity.lunch(activity)
            }
        }

        rlAbout.setOnClickListener { v: View? ->
            AboutUsActivity.lunch(activity)
        }

    }

    private fun initData() {
        var userId = SharedPreferencesUtils.getInt(SharePreferencesContants.USER_ID)
        if (userId == 0) {
            tvName.text = "点击登录"
            llProfile.setOnClickListener({
                LoginActivity.lunch(activity)
            })
        } else {
            tvName.text = SharedPreferencesUtils.getString(SharePreferencesContants.USER_NAME)
            llProfile.setOnClickListener({})
        }
        tvVersionName.text = "v" + ApkVersionUtils.getVerName(activity)
    }

    override fun loginSuccess() {
        super.loginSuccess()
        initData()
    }
}