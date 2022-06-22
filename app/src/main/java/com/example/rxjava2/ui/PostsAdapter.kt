package com.example.rxjava2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjava2.R
import com.example.rxjava2.data.entities.Post

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.MyViewHolder>() {

    var lists: ArrayList<Post> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rvitems, null, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(lists.get(position))
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title = itemView.findViewById<TextView>(R.id.titles)
        val numComments = itemView.findViewById<TextView>(R.id.num_comments)
        val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)

        fun bind(post: Post) {
            title.setText(post.title)
            if (post.comment == null) {
                showProgressBar(true)
                numComments.setText("")
            } else {
                showProgressBar(false)
                numComments.setText(post.comment.size.toString())
            }
        }

        fun showProgressBar(showProgressBar: Boolean) {
            if (showProgressBar) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    fun setPosts(post: ArrayList<Post>) {
        lists = post
        notifyDataSetChanged()
    }

    fun getPosts() : ArrayList<Post> {
        return lists
    }

    fun update(t: Post) {
        lists.set(lists.indexOf(t),t)
        notifyItemChanged(lists.indexOf(t))
    }
}