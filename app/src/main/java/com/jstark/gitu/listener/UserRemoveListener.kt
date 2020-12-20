package com.jstark.gitu.listener

import com.jstark.gitu.model.UserData

interface UserRemoveListener {
    fun onUserRemoveClicked(item: UserData?, position: Int)
}