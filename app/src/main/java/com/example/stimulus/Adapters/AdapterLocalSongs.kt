package com.example.stimulus.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stimulus.Models.LocalSongs
import com.example.stimulus.PlaySong
import com.example.stimulus.R
import java.lang.Exception


class AdapterLocalSongs(val context: Context, val songList: List<LocalSongs>):RecyclerView.Adapter<AdapterLocalSongs.HolderLocalSongs>() {
    var mp=android.media.MediaPlayer()
   class HolderLocalSongs(view:View) :RecyclerView.ViewHolder(view){
      var songName:TextView= view.findViewById(R.id.songName)
      var songArtist : TextView=view.findViewById(R.id.songArtist)
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderLocalSongs {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.single_songs,parent,false)
        return HolderLocalSongs(view)
    }

    override fun onBindViewHolder(holder: HolderLocalSongs, position: Int) {
      val songs=songList[position]

        holder.songName.text=songs.songName
        holder.songArtist.text=songs.songArtist
        holder.itemView.setOnClickListener {

            mp.stop()
            try {
                    mp=android.media.MediaPlayer()
                    mp.setDataSource(songs.songFile)
                    mp.prepare()
                    mp.start()


            }catch (e:Exception){
                e.printStackTrace()
            }
        }

    }

    override fun getItemCount(): Int {
        return  songList.size
    }
}