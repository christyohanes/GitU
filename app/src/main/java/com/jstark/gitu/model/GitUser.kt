package com.jstark.gitu.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitUser(
    var username: String? = "",
    var profilePhoto: String? = "",
    var url: String? = ""
) : Parcelable