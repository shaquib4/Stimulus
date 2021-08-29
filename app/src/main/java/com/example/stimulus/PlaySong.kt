package com.example.stimulus

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import com.example.stimulus.Models.LocalSongs
import com.example.stimulus.Models.MusicPlayerN
import com.example.stimulus.Models.Songs_list
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception

class PlaySong : AppCompatActivity() {
    private lateinit var playPause:CircleImageView
    private lateinit var nextSong:CircleImageView
    private lateinit var prevsong:CircleImageView
    private lateinit var seekbar:SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_song)
       val songFile=intent.getSerializableExtra("songList")
       playPause=findViewById(R.id.playPause)
        nextSong=findViewById(R.id.nextSong)
        prevsong= findViewById(R.id.previousSong)
        seekbar=findViewById(R.id.seekBar)
        startService(Intent(baseContext,
                ClearService::class.java))
        seekbar.max=MusicPlayerN.musicPlayer!!.duration/1000
        seekbar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
               if (MusicPlayerN.musicPlayer!=null&&fromUser){
                   MusicPlayerN.musicPlayer!!.seekTo(progress*1000)
               }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        this.runOnUiThread(object :Runnable{
            override fun run() {
                if(MusicPlayerN.musicPlayer!=null){
                    val currentPosition=MusicPlayerN.musicPlayer!!.currentPosition/1000
                    seekbar.progress = currentPosition



                }
            }

        })



        if (  MusicPlayerN.musicPlayer!!.isPlaying){
            playPause.setBackgroundResource(R.drawable.ic_baseline_pause_24)
        }else{
            playPause.setBackgroundResource(R.drawable.ic_play)
        }
        prevsong.setOnClickListener {

            if ( MusicPlayerN.musicPlayer!=null){

                try {
                    MusicPlayerN.musicPlayer!!.stop()
                    MusicPlayerN.musicPlayer!!.release()

                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
            playPause.setBackgroundResource(R.drawable.ic_baseline_pause_24)
            try {

                if (MusicPlayerN.position>0){
                    MusicPlayerN.musicPlayer= MediaPlayer()
                    MusicPlayerN.musicPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    MusicPlayerN.musicPlayer!!.setDataSource(Songs_list.listOfSongs!![MusicPlayerN.position-1].songFile)
                    MusicPlayerN.musicPlayer!!.prepare()
                    MusicPlayerN.musicPlayer!!.start()
                    MusicPlayerN.position-=1}else{
                    Toast.makeText(this,"No previous song available",Toast.LENGTH_SHORT).show()
                    MusicPlayerN.musicPlayer= MediaPlayer()
                    MusicPlayerN.musicPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    MusicPlayerN.musicPlayer!!.setDataSource(Songs_list.listOfSongs!![0].songFile)
                    MusicPlayerN.musicPlayer!!.prepare()
                    MusicPlayerN.musicPlayer!!.start()
                    MusicPlayerN.position=0
                }


            }catch (e: Exception){
                e.printStackTrace()
            }

        }
        nextSong.setOnClickListener {
            if ( MusicPlayerN.musicPlayer!=null){
                try {
                    MusicPlayerN.musicPlayer!!.stop()
                    MusicPlayerN.musicPlayer!!.release()

                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
            playPause.setBackgroundResource(R.drawable.ic_baseline_pause_24)
            try {

                if (MusicPlayerN.position<Songs_list.listOfSongs!!.size-1){
                    MusicPlayerN.musicPlayer= MediaPlayer()
                    MusicPlayerN.musicPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    MusicPlayerN.musicPlayer!!.setDataSource(Songs_list.listOfSongs!![MusicPlayerN.position+1].songFile)
                    MusicPlayerN.musicPlayer!!.prepare()
                    MusicPlayerN.musicPlayer!!.start()
                    MusicPlayerN.position+=1
                }else{
                    MusicPlayerN.musicPlayer= MediaPlayer()
                    MusicPlayerN.musicPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    MusicPlayerN.musicPlayer!!.setDataSource(Songs_list.listOfSongs!![0].songFile)
                    MusicPlayerN.musicPlayer!!.prepare()
                    MusicPlayerN.musicPlayer!!.start()
                    MusicPlayerN.position=0
                }


            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        playPause.setOnClickListener {
            if (  MusicPlayerN.musicPlayer!!.isPlaying){
                MusicPlayerN.musicPlayer!!.pause()
                playPause.setBackgroundResource(R.drawable.ic_play)
            }else{
                playPause.setBackgroundResource(R.drawable.ic_baseline_pause_24)
               MusicPlayerN.musicPlayer!!.start()
            }
        }



    }

}