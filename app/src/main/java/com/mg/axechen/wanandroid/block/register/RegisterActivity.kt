package com.mg.axechen.wanandroid.block.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseActivity

/**
 * Created by AxeChen on 2018/3/20.
 * 注册
 */
class RegisterActivity : BaseActivity() {

    companion object {
        fun lunach(context: Context) {
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }


    override fun setLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}