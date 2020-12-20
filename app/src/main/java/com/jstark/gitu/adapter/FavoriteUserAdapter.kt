package com.jstark.gitu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jstark.gitu.R
import com.jstark.gitu.listener.FavUserItemListener
import com.jstark.gitu.listener.UserRemoveListener
import com.jstark.gitu.model.UserData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_favorite_user.view.*

class FavoriteUserAdapter(
    private val list: MutableList<UserData>,
    private val listenerRemove: UserRemoveListener,
    private val listenerDetail: FavUserItemListener
) : RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_user, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listenerRemove, listenerDetail)
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            gitUser: UserData?,
            listenerRemove: UserRemoveListener,
            listenerDetail: FavUserItemListener
        ) {
            with(itemView) {
                val url = gitUser?.profilePhoto
                Picasso.with(context).load(url)
                    .placeholder(R.drawable.default_photo)
                    .into(cv_user_item_fav)
                tv_username_item_fav.text = gitUser?.username
                btn_remove.setOnClickListener {
                    listenerRemove.onUserRemoveClicked(gitUser, adapterPosition)
                }
                setOnClickListener {
                    listenerDetail.onUserClicked(gitUser, adapterPosition)
                }
            }
        }
    }
}