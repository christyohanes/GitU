package com.jstark.gitu.model

data class GitUserDetail(
    var username: String? = "",
    var profilePhoto: String? = "",
    var name: String? = "",
    var following: String? = "",
    var followers: String? = "",
    var repository: String? = ""
)
