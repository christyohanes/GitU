package com.jstark.gitu.ui

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jstark.gitu.R
import com.jstark.gitu.adapter.SectionsPagerAdapter
import com.jstark.gitu.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.jstark.gitu.db.DatabaseContract.UserColumns.Companion.PHOTO
import com.jstark.gitu.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.jstark.gitu.model.GitUser
import com.jstark.gitu.model.GitUserDetail
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.squareup.picasso.Picasso
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detail_git_user.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class DetailGitUserActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var username: String

    private lateinit var profilePhoto: String

    private lateinit var urlWithUName: Uri

    private val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_git_user)

        initiateData()
        btn_favorite.setOnClickListener(this)
    }

    private fun initiateUI(user: GitUserDetail) {
        tv_name_detail_git.text = user.name
        tv_username_detail_git.text = user.username
        Picasso.with(this).load(user.profilePhoto)
            .placeholder(R.drawable.default_photo)
            .into(cv_profile_detail_git)
        tv_repo_count_detail_git.text = user.repository
        tv_follower_count_detail_git.text = user.followers
        tv_following_count_detail_git.text = user.following

        FollowingFragment.username = user.username
        FollowersFragment.username = user.username
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }

    private fun initiateData() {
        val user = intent.getParcelableExtra<GitUser>(EXTRA_USER) as GitUser
        getUser(user.url.toString())
    }

    private fun getUser(urlData: String?) {
        pb_detail.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 4319594b9b599962e4cd2b3ec6b6bdb36acefbf9")
        client.addHeader("User-Agent", "request")
        client.get(urlData, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = String(responseBody!!)
                try {
                    val responseObject = JSONObject(result)
                    val name = responseObject.getString("name")
                    val usernameUser = responseObject.getString("login")
                    val photo = responseObject.getString("avatar_url")
                    val following = responseObject.getString("following")
                    val followers = responseObject.getString("followers")
                    val repo = responseObject.getString("public_repos")
                    pb_detail.visibility = View.GONE

                    val user = GitUserDetail()
                    user.name = name
                    user.username = usernameUser
                    user.profilePhoto = photo
                    user.following = following
                    user.followers = followers
                    user.repository = repo

                    initiateUI(user)

                    username = usernameUser
                    profilePhoto = photo

                    urlWithUName = Uri.parse("$CONTENT_URI/$username")
                    checkIsFavorite()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                pb_detail.visibility = View.GONE
                Toast.makeText(this@DetailGitUserActivity, "Gagal load data", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    override fun onClick(v: View?) {
        insertFavUser()
    }

    private fun insertFavUser() {
        btn_favorite.visibility = View.GONE
        Toast.makeText(this, "$username telah masuk ke daftar favorit!", Toast.LENGTH_LONG).show()
        val values = ContentValues()
        values.put(USERNAME, username)
        values.put(PHOTO, profilePhoto)
        contentResolver.insert(CONTENT_URI, values)
    }

    private fun checkIsFavorite() {
        val cursor = contentResolver.query(urlWithUName, null, null, null, null)
        if (cursor != null) {
            if (cursor.count > 0) btn_favorite.visibility = View.GONE
        }
    }
}