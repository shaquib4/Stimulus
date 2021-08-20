package com.example.stimulus

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stimulus.Adapters.AdapterLocalSongs
import com.example.stimulus.Models.LocalSongs
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class SongsList : AppCompatActivity(), PermissionListener {
    private lateinit var listSongs:List<LocalSongs>
    private lateinit var localSongAdapter: AdapterLocalSongs
    private lateinit var recycler:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_list)
        listSongs= ArrayList<LocalSongs>()
        recycler=findViewById(R.id.recycler_view)
        recycler.layoutManager = LinearLayoutManager(this)
        runtimePermissions()

    }

    private fun runtimePermissions() {
        Dexter.withContext(this).withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(this).check()
    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        fetchSongs()
        localSongAdapter= AdapterLocalSongs(this@SongsList,listSongs)
        recycler.adapter=localSongAdapter
        print(listSongs)


    }



    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
        fetchSongs()
        localSongAdapter= AdapterLocalSongs(this@SongsList,listSongs)
        recycler.adapter=localSongAdapter
    }

     @SuppressLint("Recycle")
     fun  fetchSongs (){

         var uri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
         var selection=MediaStore.Audio.Media.IS_MUSIC+"!=0"
         var cursor=contentResolver.query(uri,null,selection,null,null)
         if (cursor!=null){
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
             }
         }


     }
}