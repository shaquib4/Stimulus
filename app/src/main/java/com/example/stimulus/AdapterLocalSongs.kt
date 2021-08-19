package com.example.stimulus

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class AdapterLocalSongs(val context: Context,val songList: List<String>):RecyclerView.Adapter<AdapterLocalSongs.HolderLocalSongs>() {
   class HolderLocalSongs(view:View) :RecyclerView.ViewHolder(view){
      var songName:TextView= view.findViewById(R.id.songName)

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderLocalSongs {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.single_songs,parent,false)
        return HolderLocalSongs(view)
    }

    override fun onBindViewHolder(holder: HolderLocalSongs, position: Int) {
      val songs=songList[position]
        holder.songName.text=songs
    }

    override fun getItemCount(): Int {
        return  songList.size
    }
}