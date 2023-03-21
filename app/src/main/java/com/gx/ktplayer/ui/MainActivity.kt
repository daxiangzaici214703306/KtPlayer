package com.gx.ktplayer.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gx.ktplayer.R
import com.gx.ktplayer.adapter.MusicAdapter
import com.gx.ktplayer.databinding.MainBinding
import com.gx.ktplayer.utils.PermissionUtils
import com.gx.ktplayer.viewmodel.MusicViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : BaseActivity() {
    lateinit var musicViewModel: MusicViewModel
    lateinit var musicAdapter: MusicAdapter
    var musicData: MutableList<String> = ArrayList()
    lateinit var mainBinding: MainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.main)
        initAdapter()
        listenMusicData()
        listenMusicPosition()
        listenAdapterClick()
        super.onCreate(savedInstanceState)
    }

    private fun listenMusicPosition() {
        musicViewModel.listenPositionChange()
        musicViewModel.musicPosition.observe(this, Observer {
            //获取正在播放音乐的位置，刷新ui
            musicAdapter.refreshData(musicData,it)

        })
    }

    override fun hasFilePermission(hasPerssion: Boolean) {
        when (hasPerssion) {
            true -> musicViewModel.scanFile()
            false -> "no file permission"
        }
    }


    /**
     * 监听recyclerview的点击事件
     */
    private fun listenAdapterClick() {
        musicAdapter.listenMusicClick { url: String, position: Int ->
            musicViewModel.play(position)
//            playPosition = position
        }
    }

    /**
     * 初始化recyclerview的adapter
     */
    private fun initAdapter() {
        musicViewModel = ViewModelProvider(this).get(MusicViewModel::class.java)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mainBinding.musicRecycleview.layoutManager = linearLayoutManager
        musicAdapter = MusicAdapter(musicData)
        mainBinding.musicRecycleview.adapter = musicAdapter
    }

    /**
     * 监听音乐数据
     */
    fun listenMusicData() {
        musicViewModel.getMusicData().observe(this, Observer {
            Log.d("dx", "mainactivity musicdata $it")
            refreshData(it)
        })
    }

    /**
     * 刷新数据列表
     */
    private fun refreshData(result: MutableList<String>) {
        musicData=result
        musicAdapter.refreshData(result,-1)
    }

    /**
     * 点击播放的操作
     */
    fun onClickPlay(view: View) {
        musicViewModel.play(-1)
    }

    /**
     * 点击暂停的操作
     */
    fun onClickPause(view: View) {
        musicViewModel.pause()
    }


    /**
     * 点击上一曲的操作
     */
    fun onClickPrev(view: View) {
        musicViewModel.prev()
    }


    /**
     * 点击下一曲的操作
     */
    fun onClickNext(view: View) {
        musicViewModel.next()
    }


    /**
     * 点击下一曲的操作
     */
    fun onClickSongList(view: View) {
        musicViewModel.scanFile()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}