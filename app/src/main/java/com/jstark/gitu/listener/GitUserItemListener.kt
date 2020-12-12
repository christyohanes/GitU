package com.jstark.gitu.listener

import com.jstark.gitu.model.GitUser

interface GitUserItemListener {
    fun onGitUserClicked(item: GitUser?, position: Int)
}