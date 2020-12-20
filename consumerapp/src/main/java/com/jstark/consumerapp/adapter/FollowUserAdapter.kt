package com.jstark.consumerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jstark.consumerapp.R
import com.jstark.consumerapp.model.UserData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_github_user.view.*

class FollowUserAdapter(
    private val list: MutableList<UserData>,
) : RecyclerView.Adapter<FollowUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_github_user, parent, false)
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(followUser: UserData?) {
            with(itemView) {
                val url = followUser?.profilePhoto
                Picasso.with(context).load(url)
                    .placeholder(R.drawable.default_photo)
                    .into(cv_user_item)
                tv_username_item.text = followUser?.username
            }
        }
    }

}
