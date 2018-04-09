package com.mg.axechen.wanandroid.javabean

import android.os.Parcel
import android.os.Parcelable

import java.util.ArrayList

/**
 * Created by AxeChen on 2018/3/31.
 */

class TreeBean() : Parcelable {

    /**
     * children : []
     * courseId : 13
     * id : 60
     * name : Android Studio相关
     * order : 1000
     * parentChapterId : 150
     * visible : 1
     */

    var courseId: Int = 0
    var id: Int = 0
    var name: String? = null
    var order: Int = 0
    var parentChapterId: Int = 0
    var visible: Int = 0
    var children: List<TreeBean>? = null

    constructor(parcel: Parcel) : this() {
        courseId = parcel.readInt()
        id = parcel.readInt()
        name = parcel.readString()
        order = parcel.readInt()
        parentChapterId = parcel.readInt()
        visible = parcel.readInt()
        children = parcel.createTypedArrayList(CREATOR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(courseId)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(order)
        parcel.writeInt(parentChapterId)
        parcel.writeInt(visible)
        parcel.writeTypedList(children)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TreeBean> {
        override fun createFromParcel(parcel: Parcel): TreeBean {
            return TreeBean(parcel)
        }

        override fun newArray(size: Int): Array<TreeBean?> {
            return arrayOfNulls(size)
        }
    }
}
