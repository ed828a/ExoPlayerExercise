package com.example.edward.exoplayerexercise

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_second_list.view.*

/**
 *   Created by $USER_NAME on 8/3/2018.
 */
class RecyclerListAdapter(
        val list: List<VideoModel>,
        val listener: (VideoModel) -> Unit
) : RecyclerView.Adapter<RecyclerListAdapter.RecyclerListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_second_list, parent, false)

        return RecyclerListViewHolder(view)
    }

    override fun getItemCount(): Int = list.count()

    override fun onBindViewHolder(holder: RecyclerListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class RecyclerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTitle = itemView.textTitle
        private val textDate = itemView.textDate
        private val imageThumbnail = itemView.imageThumbnail

        fun bind(videoModel: VideoModel) {
            textTitle.text = videoModel.title
            textDate.text = videoModel.date
            Glide.with(itemView.context).load(videoModel.thumbnail).into(imageThumbnail)
            itemView.setOnClickListener {
                listener(videoModel)
            }
        }
    }
}