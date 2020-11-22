package com.example.uielements2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

class AlbumDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        var albumItems: AlbumItem = intent.getSerializableExtra("data") as AlbumItem
        var viewImage = findViewById<ImageView>(R.id.icon_details)
        var viewText = findViewById<TextView>(R.id.icon_name)

        if(albumItems.icons == R.drawable.bob) {
            viewImage.setImageResource(albumItems.icons!!)
            viewText.text = "Rastaman Album"

            val songsQueueArray = arrayOf(
                "Bob marley - a la la la la long",
                "Bob Marley - Bend Down Low",
                "Bob marley - Rastaman live up!",
                "BOB marley - get up stand up",
                "BOB marley - Africa Uniite")
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsQueueArray)
            val albumSongs = findViewById<ListView>(R.id.album_songs)
            albumSongs.adapter = adapter
        }
        else if(albumItems.icons ==  R.drawable.cent){
            viewImage.setImageResource(albumItems.icons!!)
            viewText.text = "G-Unit Album"

            val songsQueueArray = arrayOf(
                "50 Cent - So Serious",
                "50 cent- G Unit - Stunt 101",
                "50CENT Outta Control Remix Feat Mobb Deep",
                "50CENT Candy shop",
                "50CENT many men")
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsQueueArray)
            val albumSongs = findViewById<ListView>(R.id.album_songs)
            albumSongs.adapter = adapter
        }
        else if(albumItems.icons ==  R.drawable.pac){
            viewImage.setImageResource(albumItems.icons!!)
            viewText.text = "Thug Life Album"

            val songsQueueArray = arrayOf(
                "2pac - Dream mama",
                "2pac - only God Can judge me",
                "2Pac - Thugs Get Lonely Too",
                "2Pac - All eyes on me",
                "2pac - Changes")
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsQueueArray)
            val albumSongs = findViewById<ListView>(R.id.album_songs)
            albumSongs.adapter = adapter
        }
    }
}