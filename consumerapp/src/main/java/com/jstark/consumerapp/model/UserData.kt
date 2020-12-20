package com.jstark.consumerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserData(
    var id: Int? = 0,
    var username: String? = "",
    var profilePhoto: String? = ""
) : Parcelable
