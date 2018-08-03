package com.example.edward.exoplayerexercise

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cell_video.view.*


/**
 *   Created by $USER_NAME on 8/3/2018.
 */
class MainListAdapter(
        val list: List<VideoModel>,
        val listener: (VideoModel) -> Unit
) : RecyclerView.Adapter<MainListAdapter.MainListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_video, parent, false)

        return MainListViewHolder(view)
    }

    override fun getItemCount(): Int = list.count()

    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class MainListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTitle = itemView.textViewTitle
        private val textDate = itemView.textViewDate
        private val imageThumbnail = itemView.imageViewThumb

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
