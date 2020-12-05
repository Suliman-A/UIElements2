package com.example.uielements2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uielements2.R
import com.example.uielements2.SongsTableHandler
import com.example.uielements2.model.AlbumSong
import com.example.uielements2.model.Song

class AddSongToAlbumAct : AppCompatActivity() {
    lateinit var albumSongTitle: EditText
    lateinit var addAlbumSong: Button
    lateinit var albumSong: AlbumSong
    lateinit var albumTitleEditText : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_song_to_album)

        albumTitleEditText = findViewById(R.id.titleOftheAlbum)

        var albumTitle = intent.getStringExtra("albumTitle")
        albumSongTitle = findViewById(R.id.albumSongTitleEditText)
        addAlbumSong = findViewById(R.id.addSongToAlbumBtn)

        val databaseHandler = SongsTableHandler(this)

        albumTitleEditText.setText(albumTitle)

        addAlbumSong.setOnClickListener{
            val title_string = albumSongTitle.text.toString()
            val album_title = albumTitleEditText.text.toString()
            val albumSong = AlbumSong(albumSong = title_string, albumTitle = album_title)
            if (databaseHandler.createAlbumSongs(albumSong)){
                Toast.makeText(this, "Song added to the Album.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Oooops!", Toast.LENGTH_SHORT).show()
            }
            albumSongsArrayAdapter.notifyDataSetChanged()
            clear()
        }

    }


    fun clear(){

        albumSongTitle.text.clear()
    }
}