package com.gx.ktplayer.model

import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log

class MusicModel : BaseModel {
    val musicPlayer: MediaPlayer = MediaPlayer()
    lateinit var data: MutableList<String>
    var playPosition: Int = -1
    var isPrepared: Boolean = false
    var currentPosition: Int = -1
    lateinit var onPositionCallback: (index: Int) -> Unit

    override fun play(position: Int) {
//        musicPlayer.release()
        //判断是否点击播放按钮
        if (position == -1) {
            if (!musicPlayer.isPlaying) {
                if (isPrepared) {
                    musicPlayer.start()
                } else {
                    play(0)
                }
            }
            return
        }

        var resultUrl = data[position]
        Log.d("dx", "resultUrl=$resultUrl")
        if (resultUrl.isEmpty()) {
            return
        }
        if (musicPlayer.isPlaying && currentPosition == position) {
            return
        } else {
            musicPlayer.reset()
        }
        musicPlayer.setDataSource(resultUrl)
        musicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        musicPlayer.prepareAsync()
        musicPlayer.setOnPreparedListener(MediaPlayer.OnPreparedListener {
            isPrepared = true
            musicPlayer.start()
        })
        musicPlayer.setOnCompletionListener {
            next()
        }
        currentPosition = position
        onPositionCallback(position)

    }

    /**
     * 设置位置监听回调
     */
    fun setOnPostionCallback(onPositionCallback: (index: Int) -> Unit) {
        this.onPositionCallback = onPositionCallback
    }

    override fun pause() {
        musicPlayer.pause()
    }

    override fun stop() {
        musicPlayer.stop()
    }

    override fun prev() {
        if (isPrepared) {
            playPosition--
            if (playPosition >= 1) {
                Log.d("dx", "play prev position=$playPosition")
                play(playPosition)
            } else {
                play(0)
            }
        }
    }

    override fun next() {
        if (isPrepared) {
            playPosition++
            if (playPosition <= data.size - 1) {
                Log.d("dx", "play next position=$playPosition")
                play(playPosition)
            } else {
                playPosition = 0
                play(0)
            }
        }
    }

    override fun scanFile(onMusicListCallback: (MutableList<String>) -> Unit) {
        MusicFileTask.getMusicFileTask()?.findMusicFile(onMusicListCallback)
    }

    /**
     * 传递播放位置
     */
    fun findPositonAction(position: Int) {
        findPosition(position, data)
    }

    /**
     * 传递播放的位置
     */
    fun findPosition(position: Int, data: MutableList<String>) {
        playPosition = position
        this.data = data
    }
}