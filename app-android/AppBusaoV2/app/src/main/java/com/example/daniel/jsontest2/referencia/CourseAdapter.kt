package com.example.daniel.jsontest2.referencia

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daniel.jsontest2.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_list.view.*


class CourseAdapter(val homeFeed: HomeFeed) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        //como ciramos uma view
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.video_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
//        return videoTitles.size
        return homeFeed.videos.count()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
//        val videoTitle = videoTitles.get(position)
        val video = homeFeed.videos.get(position)
        holder.itemView.textView_video_title.text = video.name
        holder.itemView.textView_channel_name.text = video.channel.name

        val channelProfileImageView = holder.itemView.iImageView_channelprofile
        Picasso.get().load(video.channel.profileImageUrl).into(channelProfileImageView)

        val thumbnailImageView = holder.itemView.imageView_videothumbnail
        Picasso.get().load(video.imageUrl).into(thumbnailImageView)
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);

        holder.video = video

    }
}

class CustomViewHolder(val view: View, var video: Video? = null) : RecyclerView.ViewHolder(view) {
    //acessa as propriedades da classe
    companion object {
        val VIDEO_TITLE_KEY = "VIDEO_TITLE"
        val VIDEO_ID_KEY = "VIDEO_ID"
    }

    init {
        view.setOnClickListener() {
            //println("test")
            val intent = Intent(view.context, CourseDetailActivity::class.java)
            intent.putExtra(VIDEO_TITLE_KEY, video?.name)
            intent.putExtra(VIDEO_ID_KEY, video?.id)
            view.context.startActivity(intent)
        }

    }
}