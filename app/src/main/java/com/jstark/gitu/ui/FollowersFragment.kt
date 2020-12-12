package com.jstark.gitu.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jstark.gitu.R
import com.jstark.gitu.adapter.FollowUserAdapter
import com.jstark.gitu.model.FollowUser
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_followers.*
import org.json.JSONArray

class FollowersFragment : Fragment() {

    companion object {
        var username: String? = null
    }

    private var list = mutableListOf<FollowUser>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData(username)
    }

    private fun getData(usernameUser: String?) {
        list.clear()
        pb_followers.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 4319594b9b599962e4cd2b3ec6b6bdb36acefbf9")
        client.addHeader("User-Agent", "request")
        val urlUser = "https://api.github.com/users/$usernameUser/followers"
        client.get(urlUser, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = String(responseBody!!)
                try {
                    val items = JSONArray(result)

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("login")
                        val photo = item.getString("avatar_url")
                        val user = FollowUser()
                        user.username = username
                        user.profilePhoto = photo
                        list.add(user)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                pb_followers.visibility = View.GONE
                showFollowers()
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                pb_followers.visibility = View.GONE
            }
        })
    }

    private fun showFollowers() {
        if (list.size != 0) {
            rv_followers.layoutManager = LinearLayoutManager(activity)
            val followUserAdapter = FollowUserAdapter(list)
            rv_followers.adapter = followUserAdapter
            rv_followers.visibility = View.VISIBLE
            gp_no_followers.visibility = View.GONE
        } else {
            rv_followers.visibility = View.GONE
            tv_no_followers.text = getString(R.string.no_followers)
            gp_no_followers.visibility = View.VISIBLE
        }
    }
}


