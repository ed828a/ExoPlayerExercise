package com.example.edward.exoplayerexercise

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    // bandwidth meter to measure and estimate bandwidth
    private var player: SimpleExoPlayer? = null
    private var playbackPosition: Long = 0
    private var currentWindow: Int = 0
    private var playWhenReady = true
    private var videoUrl: String = "http://rdmedia.bbc.co.uk/dash/ondemand/bbb/2/client_manifest-separate_init.mpd"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = getVideoList()
        val adapter = RecyclerListAdapter(list){
            Toast.makeText(this, "you clicked this item", Toast.LENGTH_SHORT).show()
        }

        recyclerList.adapter = adapter

        val adapter2 = MainListAdapter(list){
            Toast.makeText(this, "you clicked this item", Toast.LENGTH_SHORT).show()
        }

        mainListView.adapter = adapter2
//        try {
//            val tem = findViewById<RecyclerView>(R.id.action_bar_title)
//            Log.d("MainActivity", "tem = $tem")
//        } catch (t: Throwable){
//            Log.d("MainActivity", "throw.message = ${t.message}")
//        }

        slidingUpPanel.isEnabled = resources.configuration.orientation != android.content.res.Configuration.ORIENTATION_PORTRAIT
//        slidingUpPanel.isOverlayed = false

    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer(this, videoUrl)
        }
    }

    public override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer(this, videoUrl)
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            playbackPosition = player?.currentPosition ?: 0
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }


    private fun initializePlayer(context: Context, videoUrl: String) {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                    DefaultRenderersFactory(context),
                    DefaultTrackSelector(),
                    DefaultLoadControl())

            videoView.player = player
            player!!.playWhenReady = playWhenReady
            player!!.seekTo(currentWindow, playbackPosition)

        }
        val uri = Uri.parse(videoUrl)
//        val mediaSource =
//                ExtractorMediaSource.Factory(
//                        DefaultHttpDataSourceFactory("exoPlayer"))
//                        .createMediaSource(uri)
        val mediaSource = buildMediaSource(uri)
        player!!.prepare(mediaSource, false, false)
    }

    private fun releasePlayer() {
        if (player != null) {
            currentWindow = player!!.currentWindowIndex
            playWhenReady = player!!.playWhenReady
            player!!.release()
            player = null
        }
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultHttpDataSourceFactory("ua", DefaultBandwidthMeter())
        val dashChunkSourceFactory = DefaultDashChunkSource.Factory(dataSourceFactory)
        return DashMediaSource(uri, dataSourceFactory, dashChunkSourceFactory, null, null)
    }

    private fun getVideoList(filename: String = "json_1.txt"): List<VideoModel> {
        val gson = Gson()
        val fileResourceId = resources.getIdentifier(filename, "raw", packageName)
        val fileInputStream = resources.openRawResource(R.raw.json_1txt)
//        val inputStr = File(fileUrl.path).inputStream()
        val inputStreamReader = InputStreamReader(fileInputStream)
        val response = gson.fromJson(inputStreamReader, YoutubeResponse::class.java)

        val videoList = response.items.map {
            VideoModel(it.snippet.title, it.snippet.publishedAt.extractDate(), it.snippet.thumbnails.high.url, it.id.videoId)
        }

        Log.d("MainActivity", "videoList = $videoList")
        return videoList
    }
}

fun String.extractDate(): String {
    val stringArray = this.split('T')

    return stringArray[0]
}
