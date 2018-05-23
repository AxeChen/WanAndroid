package com.mg.axechen.wanandroid.javabean

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

import java.util.ArrayList

/**
 * Created by AxeChen on 2018/3/31.
 */

class TreeBean : Serializable {

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


}
