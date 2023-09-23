package com.example.stimulus

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stimulus.Adapters.AdapterLocalSongs
import com.example.stimulus.Models.LocalSongs
import com.example.stimulus.Models.MusicPlayerN
import com.example.stimulus.Models.Songs_list
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


class SongsList : AppCompatActivity(), PermissionListener {
    private lateinit var listSongs:List<LocalSongs>
    private lateinit var localSongAdapter: AdapterLocalSongs
    private  lateinit var songInfo:RelativeLayout
    private lateinit var recycler:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(baseContext,
                ClearService::class.java))
        setContentView(R.layout.activity_songs_list)
        listSongs= ArrayList<LocalSongs>()
        recycler=findViewById(R.id.recycler_view)
        songInfo=findViewById(R.id.songInfo)
        recycler.layoutManager = LinearLayoutManager(this)
        runtimePermissions()

       songInfo.setOnClickListener {
           if (MusicPlayerN.musicPlayer!=null){
               val intent= Intent(this, PlaySong::class.java)
               startActivity(intent)

           }else{
               Toast.makeText(this,"Np Song Playing Playing",Toast.LENGTH_LONG).show()
           }
       }


    }

    private fun runtimePermissions() {
        Dexter.withContext(this).withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(this).check()
    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        fetchSongs()
        localSongAdapter= AdapterLocalSongs(this@SongsList, listSongs)
        recycler.adapter=localSongAdapter
        print(listSongs)


    }



    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
        fetchSongs()
        localSongAdapter= AdapterLocalSongs(this@SongsList, listSongs)
        recycler.adapter=localSongAdapter
    }

     @SuppressLint("Recycle")
     fun  fetchSongs (){


         var uri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
         var selection=MediaStore.Audio.Media.IS_MUSIC+"!=0"
         var cursor=contentResolver.query(uri, null, selection, null, null)
         if (cursor!=null){
             Songs_list.listOfSongs!!.clear()
             (listSongs as ArrayList<LocalSongs>).clear()
             while(cursor.moveToNext()){
                 val uriN=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                 val name=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                 val artist=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                 val obj = LocalSongs(
                         uriN,
                         name,
                         artist
                 )
                 (listSongs as ArrayList<LocalSongs>).add(obj)
                 Songs_list.listOfSongs!!.add(obj)
             }
         }


     }
}