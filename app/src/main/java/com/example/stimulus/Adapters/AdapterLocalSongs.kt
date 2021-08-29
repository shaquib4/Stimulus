package com.example.stimulus.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stimulus.Models.LocalSongs
import com.example.stimulus.Models.MusicPlayerN
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

            if ( MusicPlayerN.musicPlayer!=null){
                try {
                    MusicPlayerN.musicPlayer!!.stop()
                    MusicPlayerN.musicPlayer!!.release()

                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
            try {
                MusicPlayerN.position=position
                MusicPlayerN.musicPlayer= MediaPlayer()
                MusicPlayerN.musicPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC);
                MusicPlayerN.musicPlayer!!.setDataSource(songs.songFile)
                MusicPlayerN.musicPlayer!!.prepare()
                MusicPlayerN.musicPlayer!!.start()


            }catch (e: Exception){
                e.printStackTrace()
            }
/*
            mp.stop()
            try {
                    mp=android.media.MediaPlayer()
                    mp.setDataSource(songs.songFile)
                    mp.prepare()
                    mp.start()


            }catch (e:Exception){
                e.printStackTrace()
            }*/
           /* val intent= Intent(context, PlaySong::class.java)
            intent.putExtra("songFile",songs.songFile)
            context.startActivity(intent)*/
            context as Activity
            context.findViewById<TextView>(R.id.song_artist).text=songs.songArtist
            context.findViewById<TextView>(R.id.song_name).text=songs.songName
        }

    }

    override fun getItemCount(): Int {
        return  songList.size
    }
}