package com.jstark.consumerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jstark.consumerapp.adapter.FollowUserAdapter
import com.jstark.consumerapp.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.jstark.consumerapp.db.MappingHelper
import com.jstark.consumerapp.model.UserData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var listUser = mutableListOf<UserData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initiateData()
    }

    private fun initiateUI() {
        rv_git_user_main.isFocusable = false
        rv_git_user_main.setHasFixedSize(true)
        showRecyclerList()
    }

    fun initiateData() {
        val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
        listUser = MappingHelper.mapCursorToArrayList(cursor)
        initiateUI()
    }

    private fun showRecyclerList() {
        rv_git_user_main.layoutManager = LinearLayoutManager(this)
        val gitUserAdapter = FollowUserAdapter(listUser)
        rv_git_user_main.adapter = gitUserAdapter
    }
}