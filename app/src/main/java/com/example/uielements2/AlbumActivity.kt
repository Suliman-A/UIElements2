package com.example.uielements2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.uielements2.R
import com.example.uielements2.SongsTableHandler
import com.example.uielements2.model.Album

var albumSongs = ArrayList<String>()
var albumURI = String
lateinit var albumAdapter: ArrayAdapter<Album>
class AlbumActivity : AppCompatActivity() {
    lateinit var title: String
    lateinit var listView : ListView
    lateinit var databaseHandler: SongsTableHandler
    lateinit var albums: MutableList<Album>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        listView = findViewById(R.id.albumslistView)
        databaseHandler = SongsTableHandler(this)
        albums = databaseHandler.readAlbum()
        albumAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, albums)
        listView.adapter = albumAdapter

        registerForContextMenu(listView)

        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, NewAlbumDetailsAct::class.java)
            val album_id = albums[position].id
            intent.putExtra("album_id", album_id)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.album_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addAlbum -> {
                val intent = Intent(this, AddAlbumAct::class.java)
                startActivity(intent)
                true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val inflater = menuInflater
        inflater.inflate(R.menu.edit_album_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        when (item.itemId) {
            R.id.editAlbum -> {
                val intent = Intent(this, EditAlbumActivity::class.java)
                val album_id = albums[info.position].id
                intent.putExtra("album_id", album_id)
                startActivity(intent)
                true
            }

        }
        return super.onOptionsItemSelected(item)
    }




}






