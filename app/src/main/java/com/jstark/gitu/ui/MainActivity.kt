package com.jstark.gitu.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.jstark.gitu.R
import com.jstark.gitu.adapter.GitUserAdapter
import com.jstark.gitu.listener.GitUserItemListener
import com.jstark.gitu.model.GitUser
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), GitUserItemListener, SearchView.OnQueryTextListener,
    View.OnClickListener {

    private var listUser = mutableListOf<GitUser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initiateUI()
    }

    private fun initiateUI() {
        rv_git_user_main.isFocusable = false
        rv_git_user_main.setHasFixedSize(true)
        sv_user.setOnQueryTextListener(this)
        btn_next_favorite.setOnClickListener(this)
        btn_next_settings.setOnClickListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        getListGitUser(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    private fun getListGitUser(username: String?) {
        pb_main.visibility = View.VISIBLE
        listUser.clear()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$username"
        client.addHeader("Authorization", "token 4319594b9b599962e4cd2b3ec6b6bdb36acefbf9")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = String(responseBody!!)
                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val usernameUser = item.getString("login")
                        val photo = item.getString("avatar_url")
                        val urlDetail = item.getString("url")
                        val user = GitUser()
                        user.username = usernameUser
                        user.profilePhoto = photo
                        user.url = urlDetail
                        listUser.add(user)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                pb_main.visibility = View.GONE
                showRecyclerList()
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                pb_main.visibility = View.GONE
                Toast.makeText(this@MainActivity, "Gagal load data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showRecyclerList() {
        if (listUser.size != 0) {
            rv_git_user_main.layoutManager = LinearLayoutManager(this)
            val gitUserAdapter = GitUserAdapter(listUser, this)
            rv_git_user_main.adapter = gitUserAdapter
        } else {
            Toast.makeText(
                this,
                "Maaf, user dengan username tersebut tidak ditemukan",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onGitUserClicked(item: GitUser?, position: Int) {
        val gitUser = listUser[position]
        val moveWithData = Intent(this@MainActivity, DetailGitUserActivity::class.java)
        moveWithData.putExtra(DetailGitUserActivity.EXTRA_USER, gitUser)
        startActivity(moveWithData)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_next_favorite -> startActivity(Intent(this, FavoriteActivity::class.java))
            R.id.btn_next_settings -> startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}