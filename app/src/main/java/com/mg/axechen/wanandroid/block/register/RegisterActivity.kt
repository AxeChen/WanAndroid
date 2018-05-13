package com.mg.axechen.wanandroid.block.register

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseActivity
import com.mg.axechen.wanandroid.block.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*
import network.schedules.SchedulerProvider

/**
 * Created by AxeChen on 2018/3/20.
 * 注册
 */
class RegisterActivity : BaseActivity(), RegisterContract.View {

    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }

    override fun userNameIsEmpty() {
        ttUserName.error = getString(R.string.register_username_is_empty)
        progressDialog.dismiss()
    }

    override fun passwordIsEmpty() {
        ttPassword.error = getString(R.string.register_pwd_is_empty)
        progressDialog.dismiss()
    }

    override fun rePasswordFault() {
        ttRePassword.error = getString(R.string.register_re_pwd_is_empty)
        progressDialog.dismiss()
    }

    override fun registerSuccess() {
        progressDialog.dismiss()
        Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
        LoginActivity.lunch(this)
    }

    override fun registerFail(errorMsg: String) {
        ttUserName.error = errorMsg
        progressDialog.dismiss()
    }

    private val presenter: RegisterContract.Presenter by lazy {
        RegisterPresenter(this, SchedulerProvider.getInstatnce()!!)
    }

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
        checkEditTextInput()
        btnRegister.setOnClickListener({
            progressDialog.setMessage("正在注册")
            presenter.register(
                    etName.text.toString(),
                    etPassword.text.toString(),
                    etRePassword.text.toString()
            )
        })
    }

    private fun checkEditTextInput() {

        etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkUserName(s.toString())
            }

        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkPassword(s.toString())
            }

        })

        etRePassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkRePassword(s.toString())
            }

        })
    }

    private fun checkRePassword(text: String) {
        if (text.isEmpty()) {
            ttRePassword.error = "确认密码为空"
        } else {
            ttRePassword.error = null
        }
    }


    private fun checkPassword(text: String) {
        if (text.isEmpty()) {
            ttPassword.error = "密码为空"
        } else {
            ttPassword.error = null
        }
    }

    private fun checkUserName(text: String) {
        if (text.length < 6) {
            ttUserName.error = "用户名少于6位"
        } else {
            ttUserName.error = null
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}