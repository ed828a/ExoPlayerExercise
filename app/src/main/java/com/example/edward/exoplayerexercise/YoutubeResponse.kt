package com.example.edward.exoplayerexercise

/**
 *   Created by $USER_NAME on 8/3/2018.
 */
class YoutubeResponse(val prevPageToken: String,
                      val nextPageToken: String,
                      val pageInfo: PageInfo,
                      val items: List<Item>) {
    data class PageInfo(val totalResults: String,
                        val resultsPerPage: String)

    data class Item(val id: ID,
                    val snippet: Snippet) {
        data class ID(val kind: String,
                      val videoId: String)

        data class Snippet(val publishedAt: String,
                           val title: String,
                           val thumbnails: Thumbnails) {
            data class Thumbnails(val high: High) {
                class High(val url: String)
            }
        }
    }
}