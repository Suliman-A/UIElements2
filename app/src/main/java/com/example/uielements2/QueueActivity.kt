package com.example.uielements2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class QueueActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queue)

        val preferences = getSharedPreferences("sharedPrefs", 0)
        val songs = preferences.getString("songname", "")
        val songs2 = preferences.getString("songname2", "")
        val songs3 = preferences.getString("songname3", "")
        val songs4 = preferences.getString("songname4", "")
        val songs5 = preferences.getString("songname5", "")
        val songs6 = preferences.getString("songname6", "")
        val songs7 = preferences.getString("songname7", "")
        val songs8 = preferences.getString("songname8", "")
        val songs9 = preferences.getString("songname9", "")
        val songs10 = preferences.getString("songname10", "")
        val songs11 = preferences.getString("songname11", "")
        val songs12 = preferences.getString("songname12", "")
        val songs13 = preferences.getString("songname13", "")
        val songs14 = preferences.getString("songname14", "")
        val songs15 = preferences.getString("songname15", "")


        val songsQueueArray = arrayOf(songs, songs2, songs3, songs4, songs5, songs6, songs7, songs8, songs9, songs10, songs11, songs12, songs13, songs14, songs15)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsQueueArray)
        val songsList = findViewById<ListView>(R.id.queue_songs)
        songsList.adapter = adapter


    }
}