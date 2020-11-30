package com.example.uielements2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uielements2.model.Song



class CreateSongActivity : AppCompatActivity() {

    lateinit var addSongButton: Button
    lateinit var titleEt: EditText
    lateinit var artistEt: EditText
    lateinit var albumEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_song)

        val dataBaseHandler = SongsTableHandler(this)
        titleEt = findViewById(R.id.updateTitleEt)
        artistEt = findViewById(R.id.updateArtistEt)
        albumEt = findViewById(R.id.updateAlbumEt)

        addSongButton = findViewById(R.id.updateSongButton)
        addSongButton.setOnClickListener {
            //get fields from the form
            val title = titleEt.text.toString()
            val artist = artistEt.text.toString()
            val album = albumEt.text.toString()
            //assign it to the song model
            val song = Song(title = title, artist =  artist, album =  album)
            //save it to the database
            if (dataBaseHandler.create(song)) {
                Toast.makeText(applicationContext, "Song was added", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(applicationContext, "Oops something went wrong", android.widget.Toast.LENGTH_SHORT).show()
                }
            clearField()
            }
        }

    fun clearField(){
        titleEt.text.clear()
        artistEt.text.clear()
        albumEt.text.clear()
    }
override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    val inflater = menuInflater
    inflater.inflate(R.menu.main_menu, menu)
    return super.onCreateOptionsMenu(menu)
}

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
        R.id.go_to_songs -> {
            startActivity(Intent(this, MainActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
    }
}





