package com.mg.axechen.wanandroid.javabean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by AxeChen on 2018/4/19.
 */

class LoginBean() : Parcelable {

    /**
     * collectIds : [2680,2700,1367,1352]
     * email : 1137366723@qq.com
     * icon :
     * id : 2145
     * password : 123321
     * type : 0
     * username : axeChen
     */

    var email: String? = null
    var icon: String? = null
    var id: Int = 0
    var password: String? = null
    var type: Int = 0
    var username: String? = null
    var collectIds: MutableList<Int>? = null

    constructor(parcel: Parcel) : this() {
        email = parcel.readString()
        icon = parcel.readString()
        id = parcel.readInt()
        password = parcel.readString()
        type = parcel.readInt()
        username = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(icon)
        parcel.writeInt(id)
        parcel.writeString(password)
        parcel.writeInt(type)
        parcel.writeString(username)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginBean> {
        override fun createFromParcel(parcel: Parcel): LoginBean {
            return LoginBean(parcel)
        }

        override fun newArray(size: Int): Array<LoginBean?> {
            return arrayOfNulls(size)
        }
    }
}
