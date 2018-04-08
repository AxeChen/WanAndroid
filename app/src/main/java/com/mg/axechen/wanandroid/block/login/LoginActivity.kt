package com.mg.axechen.wanandroid.block.login

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseActivity
import com.mg.axechen.wanandroid.block.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/3/20.
 *
 * 登陆注册
 */
class LoginActivity() : BaseActivity(), LoginContract.View, Parcelable {

    private val presenter: LoginPresenter by lazy {
        LoginPresenter(LoginModel(), this, SchedulerProvider.getInstatnce()!!)
    }

    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }

    constructor(parcel: Parcel) : this() {
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
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginActivity> {
        override fun createFromParcel(parcel: Parcel): LoginActivity {
            return LoginActivity(parcel)
        }

        override fun newArray(size: Int): Array<LoginActivity?> {
            return arrayOfNulls(size)
        }
    }


}