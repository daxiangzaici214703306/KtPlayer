package com.gx.ktplayer.viewmodel

interface BaseViewModel {
    /**
     * 播放
     */
    fun play(position:Int)

    /**
     * 暂停
     */
    fun pause()

    /**
     * 停止播放
     */
    fun stop()

    /**
     * 上一曲
     */
    fun prev()

    /**
     * 下一曲
     */
    fun next()

    /**
     * 扫描本地文件夹获取音乐文件地址
     */
    fun scanFile();
}