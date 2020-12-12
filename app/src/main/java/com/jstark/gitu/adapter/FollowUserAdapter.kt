package com.jstark.gitu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jstark.gitu.R
import com.jstark.gitu.model.FollowUser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_follow.view.*

class FollowUserAdapter(
    private val list: MutableList<FollowUser>,
) : RecyclerView.Adapter<FollowUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_follow, parent, false)
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(followUser: FollowUser?) {
            with(itemView) {
                val url = followUser?.profilePhoto
                Picasso.with(context).load(url)
                    .placeholder(R.drawable.default_photo)
                    .into(cv_user_item_follow)
                tv_username_item_follow.text = followUser?.username
            }
        }
    }

}
