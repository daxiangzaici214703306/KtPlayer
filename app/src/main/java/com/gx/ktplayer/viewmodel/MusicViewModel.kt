package com.gx.ktplayer.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gx.ktplayer.model.MusicModel

class MusicViewModel : BaseViewModel, ViewModel() {
    val musicLiveData: MutableLiveData<MutableList<String>> = MutableLiveData()
    val musicPosition: MutableLiveData<Int> = MutableLiveData()
    val musicModel = MusicModel()



    fun getMusicData(): MutableLiveData<MutableList<String>> {
        return musicLiveData
    }

    override fun play(position: Int) {
        musicModel.play(position)
    }

    override fun pause() {
        musicModel.pause()
    }

    override fun stop() {
        musicModel.stop()
    }

    override fun prev() {
        musicModel.prev()
    }

    override fun next() {
        musicModel.next()
    }

    override fun scanFile() {
        musicModel.scanFile {
            Log.d("dx", "musicdata $it")
            musicModel.findPosition(0, it)
            musicLiveData.postValue(it)
        }
    }

    /**
     * 监听位置变化
     */
    fun listenPositionChange(){
        musicModel.setOnPostionCallback {
            musicPosition.postValue(it)
        }
    }


}