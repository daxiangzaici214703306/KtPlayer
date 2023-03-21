package com.gx.ktplayer.model

import android.os.Environment
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.ArrayList

/**
 * 获取音乐文件的类
 */
class MusicFileTask {
    val musics: MutableList<String> = ArrayList()
    val mainScope = MainScope()

    /***
     * 单例模式
     */
    companion object {
        var instance: MusicFileTask? = null
        fun getMusicFileTask(): MusicFileTask? {
            if (instance == null) {
                instance = MusicFileTask()
            }
            return instance
        }
    }


    /**
     * 查找本地音乐文件
     */
    fun findMusicFile(onMusicListCallback: (MutableList<String>) -> Unit) {
        val rootUrl: String = Environment.getExternalStorageDirectory().path   //+"/gx"
        Log.d("dx", "rooturl=$rootUrl")
        val rootFile: File = File(rootUrl)
        mainScope.launch {
            onMusicListCallback(withContext(Dispatchers.IO) {
                musics.clear()
                loopFile(rootFile)
                musics
            })
        }

    }

    /**
     * 循环遍历文件夹获取文件后缀为.mp3的文件
     */
    private fun loopFile(file: File) {
        val files = file.listFiles()
        if (files != null) {
            files.forEach {
                if (it.isDirectory) {
                    Log.d("dx", "is Directory=${it.path}")
                    loopFile(it)
                } else {
                    Log.d("dx", "is not Directory=${it.path}")
                    if (it.path.contains(".mp3")) {
                        Log.d("dx", "add success")
                        musics.add(it.path)
                    }
                }
            }
        }
    }
}