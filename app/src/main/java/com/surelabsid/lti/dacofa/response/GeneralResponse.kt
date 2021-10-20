package com.surelabsid.lti.dacofa.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeneralResponse(

    @field:SerializedName("message")
    var message: String? = null,
    @field:SerializedName("code")
    var code: Int? = null,
    @field:SerializedName("type")
    var type: String? = null,
    @field:SerializedName("res")
    var res: Int? = null

) : Parcelable