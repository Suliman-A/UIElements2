package com.example.uielements2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

val albumSongs = ArrayList<String>()
val albumURI = String

class AlbumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)


        val gridView = findViewById<GridView>(R.id.grid_view)
        gridView.adapter = ImageAdapter(applicationContext)

        gridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, AlbumDetailsActivity::class.java)
            val uri: String
            when (position) {
                0 -> {
                    uri = "@drawable/pac"
                    albumSongs.clear()
                    albumSongs.addAll(resources.getStringArray(R.array.pac))
                }
                1 -> {
                    uri = "@drawable/bob"
                    albumSongs.clear()
                    albumSongs.addAll(resources.getStringArray(R.array.bob))
                }
                else -> {
                    uri = "@drawable/cent"
                    albumSongs.clear()
                    albumSongs.addAll(resources.getStringArray(R.array.cent))
                }
            }
            intent.putExtra("imageUri",  uri)
            startActivity(intent)
        }


    }

}