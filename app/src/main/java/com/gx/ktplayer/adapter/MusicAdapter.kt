package com.gx.ktplayer.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gx.ktplayer.R

/**
 * 音乐文件列表适配器
 */
class MusicAdapter(var data: MutableList<String>) :
    RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    //    var musicListBinding:MusicListBinding?=null
    private lateinit var listMusicClickCallback: (url: String, position: Int) -> Any
    var currentPosition: Int = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
//        Log.d("dx","onCreateViewHolder")
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.music_list, parent, false)
//        musicListBinding = DataBindingUtil.bind(view)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
//        Log.d("dx","onBindViewHolder")
        holder.musicName.text = data.get(position)
        holder.index = position
        if(position==currentPosition){
            holder.musicName.setBackgroundColor(Color.BLUE)
        }
    }

    override fun getItemCount(): Int {
//        Log.d("dx","getItemCount")
//        Log.d("dx","adater data=$data")
        return data.size
    }

    inner class MusicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var index: Int = 0
        var musicName: TextView = view.findViewById(R.id.music_name)

        init {
            view.setOnClickListener {
                listMusicClickCallback(musicName.text.toString(), index)
            }
        }

    }

    /**
     * 子项点击回调事件
     */
    fun listenMusicClick(listMusicClickCallback: (url: String, position: Int) -> Unit) {
        this.listMusicClickCallback = listMusicClickCallback
    }

    /**
     * 刷新音乐数据
     */
    fun refreshData(result: MutableList<String>, currentPosition: Int) {
        this.currentPosition = currentPosition
        data.clear()
        data.addAll(result)
        notifyDataSetChanged()
    }

}