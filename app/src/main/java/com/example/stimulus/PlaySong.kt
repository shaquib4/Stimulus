package com.example.stimulus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.lang.Exception

class PlaySong : AppCompatActivity() {
      private var mp=android.media.MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_song)
       val songFile=intent.getStringExtra("songFile")
        mp.stop()
        try {
            mp=android.media.MediaPlayer()
            mp.setDataSource(songFile!!)
            mp.prepare()
            mp.start()


        }catch (e: Exception){
            e.printStackTrace()
        }
    }

}