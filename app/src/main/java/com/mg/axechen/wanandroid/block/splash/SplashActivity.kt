package com.mg.axechen.wanandroid.block.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseActivity
import com.mg.axechen.wanandroid.block.login.LoginActivity
import com.mg.axechen.wanandroid.block.main.MainActivity
import com.mg.axechen.wanandroid.block.register.RegisterActivity
import com.mg.axechen.wanandroid.utils.SharePreferencesContants
import com.mg.axechen.wanandroid.utils.SharedPreferencesUtils
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by AxeChen on 2018/4/18.
 */
class SplashActivity : BaseActivity() {
    override fun setLayoutId(): Int {
        return R.layout.activity_splash
    }

    companion object {
        fun lunch(context: Context) {
            context.startActivity(Intent(context, SplashActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initClickListener()
        initData()
    }

    private fun initData() {
        var userId = SharedPreferencesUtils.getInt(SharePreferencesContants.USER_ID)
        if (userId == 0) {
            if (SharedPreferencesUtils.getBoolean(SharePreferencesContants.IS_SECOND_USE)) {
                MainActivity.launch(this)
                finish()
            } else {
                tvNextTime.visibility = View.VISIBLE
                btnLogin.visibility = View.VISIBLE
                btnRegister.visibility = View.VISIBLE
                SharedPreferencesUtils.putBoolean(SharePreferencesContants.IS_SECOND_USE, true)
            }

        } else {
            // 三秒之后跳转
            tvNextTime.visibility = View.GONE
            btnLogin.visibility = View.GONE
            btnRegister.visibility = View.GONE
            Handler().postDelayed({
                MainActivity.launch(this)
                finish()
            }, 2000)
        }
    }

    private fun initClickListener() {
        tvNextTime.setOnClickListener({
            MainActivity.launch(this)
            finish()
        })

        btnLogin.setOnClickListener({
            LoginActivity.lunch(this)
            finish()
        })

        btnRegister.setOnClickListener({
            RegisterActivity.lunach(this)
            finish()
        })
    }


}