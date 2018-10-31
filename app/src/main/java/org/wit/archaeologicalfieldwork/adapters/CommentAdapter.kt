package org.wit.archaeologicalfieldwork.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_comment.view.*
import org.jetbrains.anko.AnkoLogger
import org.wit.archaeologicalfieldwork.R
import org.wit.archaeologicalfieldwork.models.comment.CommentsModel

class CommentAdapter constructor(private var comments: List<CommentsModel>) : RecyclerView.Adapter<CommentAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_comment, parent, false))
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val comment = comments[holder.adapterPosition]
    holder.bind(comment)
  }

  override fun getItemCount(): Int = comments.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView), AnkoLogger{

    fun bind(comment: CommentsModel){
      itemView.user.text = comment.user
      itemView.comment.text = comment.comment
      itemView.date.text = comment.date
    }
  }
}