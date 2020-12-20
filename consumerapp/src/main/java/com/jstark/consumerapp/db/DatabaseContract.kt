package com.jstark.consumerapp.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.jstark.gitu"
    const val SCHEME = "content"

    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "GitUser"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val PHOTO = "photo"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}