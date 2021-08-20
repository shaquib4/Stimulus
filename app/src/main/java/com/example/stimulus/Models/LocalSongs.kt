package com.example.stimulus.Models

class LocalSongs(
        val songFile: String,
        val songName:String,
        val songArtist:String
) {
    constructor() : this("","","")
}