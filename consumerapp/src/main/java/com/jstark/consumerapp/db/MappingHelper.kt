package com.jstark.consumerapp.db

import android.database.Cursor
import com.jstark.consumerapp.model.UserData

object MappingHelper {

    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<UserData> {
        val userList = ArrayList<UserData>()
        userCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns._ID))
                val username =
                    getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.USERNAME))
                val photo = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.PHOTO))
                userList.add(UserData(id, username, photo))
            }
        }
        return userList
    }

    fun mapCursorToObject(userCursor: Cursor?): UserData {
        var user = UserData()
        userCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns._ID))
            val username = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.USERNAME))
            val photo = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.PHOTO))
            user = UserData(id, username, photo)
        }
        return user
    }
}