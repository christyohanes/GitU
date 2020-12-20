package com.jstark.gitu.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jstark.gitu.R
import com.jstark.gitu.adapter.FavoriteUserAdapter
import com.jstark.gitu.db.MappingHelper
import com.jstark.gitu.db.UserHelper
import com.jstark.gitu.listener.FavUserItemListener
import com.jstark.gitu.listener.UserRemoveListener
import com.jstark.gitu.model.GitUser
import com.jstark.gitu.model.UserData
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity(), View.OnClickListener, UserRemoveListener,
    FavUserItemListener {
    private var listUser = mutableListOf<UserData>()
    private lateinit var userHelper: UserHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        initiateData()
    }

    fun initiatateUI() {
        rv_git_user_fav.isFocusable = false
        rv_git_user_fav.setHasFixedSize(true)
        btn_back_fav.setOnClickListener(this)
        showRecyclerList()
    }

    fun initiateData() {
        userHelper = UserHelper.getInstance(this)
        userHelper.open()
        val cursor = userHelper.queryAll()
        listUser = MappingHelper.mapCursorToArrayList(cursor)
        initiatateUI()
    }

    private fun showRecyclerList() {
        rv_git_user_fav.layoutManager = LinearLayoutManager(this)
        val favoriteUserAdapter = FavoriteUserAdapter(listUser, this, this)
        rv_git_user_fav.adapter = favoriteUserAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        userHelper.close()
    }

    override fun onClick(v: View?) {
        onBackPressed()
    }

    override fun onUserRemoveClicked(item: UserData?, position: Int) {
        pb_fav.visibility = View.VISIBLE
        val user = listUser[position]
        userHelper.deleteByUsername(user.username.toString())
        Toast.makeText(
            this,
            (getString(R.string.user_removed, user.username.toString())),
            Toast.LENGTH_SHORT
        ).show()
        initiateData()
        pb_fav.visibility = View.GONE
    }

    override fun onUserClicked(item: UserData?, position: Int) {
        val user =
            GitUser(
                item?.username,
                item?.profilePhoto,
                "https://api.github.com/users/${item?.username}"
            )
        val moveWithData = Intent(this, DetailGitUserActivity::class.java)
        moveWithData.putExtra(DetailGitUserActivity.EXTRA_USER, user)
        startActivity(moveWithData)
    }


}