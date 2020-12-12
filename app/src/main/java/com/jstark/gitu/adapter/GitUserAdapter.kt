package com.jstark.gitu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jstark.gitu.R
import com.jstark.gitu.listener.GitUserItemListener
import com.jstark.gitu.model.GitUser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_github_user.view.*

class GitUserAdapter(
    private val list: MutableList<GitUser>,
    private val listenerGitUser: GitUserItemListener
) : RecyclerView.Adapter<GitUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_github_user, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listenerGitUser)
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(gitUser: GitUser?, listenerGitUser: GitUserItemListener) {
            with(itemView) {
                val url = gitUser?.profilePhoto
                Picasso.with(context).load(url)
                    .placeholder(R.drawable.default_photo)
                    .into(cv_user_item)
                tv_username_item.text = gitUser?.username
                setOnClickListener {
                    listenerGitUser.onGitUserClicked(gitUser, adapterPosition)
                }
            }
        }
    }
}
