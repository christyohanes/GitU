package com.jstark.gitu.listener

import com.jstark.gitu.model.UserData

interface FavUserItemListener {
    fun onUserClicked(item: UserData?, position: Int)
}