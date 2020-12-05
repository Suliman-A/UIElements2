package com.example.uielements2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uielements2.model.Song

class EditSongActivity : AppCompatActivity() {

    lateinit var updateSongButton: Button
    lateinit var title : EditText
    lateinit var artist : EditText
    lateinit var album : EditText
    lateinit var song: Song


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_song)

        val song_id = intent.getIntExtra("song_id", 0)
        //GET RECORD FROM DATABASE
        val dataBaseHandler = SongsTableHandler(this)
        val song = dataBaseHandler.readOne(song_id)

        title = findViewById(R.id.updateTitleEt)
        artist = findViewById(R.id.updateArtistEt)
        album = findViewById(R.id.updateAlbumEt)
        updateSongButton = findViewById(R.id.updateSongButton)

        title.setText(song.title)
        album.setText(song.album)
        artist.setText(song.artist)

        updateSongButton.setOnClickListener {
            val title_string = title.text.toString()
            val artist_string = artist.text.toString()
            val album_string = album.text.toString()
            val updateSong = Song(id = song.id, title = title_string, artist = artist_string, album =  album_string)
            //save it to the database
            if (dataBaseHandler.update(updateSong)) {
                Toast.makeText(this, "Song updated!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Oooops!", Toast.LENGTH_SHORT).show()
            }
            songsAdapter.notifyDataSetChanged()

        }

    }
}