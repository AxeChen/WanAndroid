package com.mg.axechen.wanandroid.block.login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.WanAndroidApplication
import com.mg.axechen.wanandroid.base.BaseActivity
import com.mg.axechen.wanandroid.block.main.MainActivity
import com.mg.axechen.wanandroid.block.register.RegisterActivity
import com.mg.axechen.wanandroid.utils.Contacts
import kotlinx.android.synthetic.main.activity_login.*
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/3/20.
 *
 * 登陆注册
 */
class LoginActivity : BaseActivity(), LoginContract.View {

    companion object {
        fun lunch(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    private val presenter: LoginPresenter by lazy {
        LoginPresenter(LoginModel(), this, SchedulerProvider.getInstatnce()!!)
    }

    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initClickListener()
        checkEditTextInput()
    }

    private fun checkEditTextInput() {

        etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkInput(s.toString())
            }

        })
    }

    private fun checkInput(text: String) {
        if (text.length < 6) {
            ttUserName.error = "用户名少于6位"
        } else {
            ttUserName.error = null
        }
    }

    private fun initClickListener() {
        btLogin.setOnClickListener({ view ->
            progressDialog.setMessage("正在登录")
            progressDialog.show()
            presenter.login(etName.text.toString(), etPassword.text.toString())
        })

        tvRegister.setOnClickListener({ view ->
            RegisterActivity.lunach(this@LoginActivity)
        })
    }

    override fun loginSuccess() {
        progressDialog.dismiss()
        // 跳转逻辑
        Toast.makeText(WanAndroidApplication.instance, "登录成功", Toast.LENGTH_SHORT).show()
        finish()


        MainActivity.launch(this)
        var intent = Intent()
        intent.action = Contacts.LOGIN_SUCCESS
        sendBroadcast(intent)
    }

    override fun loginFail(errorMsg: String) {
        progressDialog.dismiss()
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
    }

    override fun userNameIsEmpty() {
        ttUserName.error = "用户名为空"
        progressDialog.dismiss()
    }

    override fun passwordIsEmpty() {
        ttPassword.error = "密码为空"
        progressDialog.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unSubscribe()
    }

}