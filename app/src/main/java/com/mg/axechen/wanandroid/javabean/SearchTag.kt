package com.mg.axechen.wanandroid.javabean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by AxeChen on 2018/4/10.
 */

class SearchTag() : Parcelable {

    /**
     * id : 6
     * link :
     * name : 面试
     * order : 1
     * visible : 1
     */

    var id: Int = 0
    var link: String? = null
    var name: String? = null
    var order: Int = 0
    var visible: Int = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        link = parcel.readString()
        name = parcel.readString()
        order = parcel.readInt()
        visible = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(link)
        parcel.writeString(name)
        parcel.writeInt(order)
        parcel.writeInt(visible)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchTag> {
        override fun createFromParcel(parcel: Parcel): SearchTag {
            return SearchTag(parcel)
        }

        override fun newArray(size: Int): Array<SearchTag?> {
            return arrayOfNulls(size)
        }
    }
}
