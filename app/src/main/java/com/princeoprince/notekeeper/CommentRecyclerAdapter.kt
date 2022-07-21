package com.princeoprince.notekeeper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommentRecyclerAdapter(private val context: Context, private val note: NoteInfo) :
    RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val sender = itemView.findViewById<TextView>(R.id.sender)
        internal val comment = itemView.findViewById<TextView>(R.id.comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_comment, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noteComment = note.comments[position]

        holder.sender.text = noteComment.sender?.name ?: "You"
        holder.comment.text = noteComment.comment
    }

    override fun getItemCount(): Int = note.comments.size

}
