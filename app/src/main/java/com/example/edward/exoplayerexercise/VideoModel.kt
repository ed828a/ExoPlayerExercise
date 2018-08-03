package com.example.edward.exoplayerexercise

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *   Created by $USER_NAME on 8/3/2018.
 */
@Parcelize
data class VideoModel(var title: String = "",
                      var date: String = "",
                      var thumbnail: String = "",
                      var videoId: String = "",
                      var relatedToVideoId: String = "",
        // indexResponse: to be consistent with changing backend order
                      var indexResponse: Int = -1) : Parcelable