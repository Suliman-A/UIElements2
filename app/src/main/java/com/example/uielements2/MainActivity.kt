package com.example.uielements2

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.google.android.material.snackbar.Snackbar

val queuedSongs = ArrayList<String>() //Array where all the songs queued will be stored and will be passed to the Queue activity
val songsArray = arrayListOf<String>()

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        songsArray.addAll(resources.getStringArray(R.array.pac))
        songsArray.addAll(resources.getStringArray(R.array.bob))
        songsArray.addAll(resources.getStringArray(R.array.cent))


        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsArray)
        val songsQueueListView = findViewById<ListView>(R.id.songsQueueListView)
        songsQueueListView.adapter = adapter

        registerForContextMenu(songsQueueListView)
        songsQueueListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->  }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.songs_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo //Allows us to inherit from the class Adapterview.AdapterCOntextMenuInfo to get the position
        return when (item.itemId) {
            R.id.add_to_queue -> {
                queuedSongs.add(songsArray[menuInfo.position])
                val snackbar = Snackbar.make(findViewById(R.id.songsQueueListView), "${songsArray[menuInfo.position]} is added to the Queue.", Snackbar.LENGTH_LONG)
                snackbar.setAction("Queue", View.OnClickListener { //Lamda function
                    val intent = Intent(this, QueueActivity::class.java)
                    startActivity(intent)
                })
                snackbar.show()
                true
            }
            else -> {
                return super.onContextItemSelected(item)
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.go_to_songs -> {
                true
            }
            R.id.go_to_albums -> {
                startActivity(Intent(this, AlbumActivity::class.java))
                true
            }
            R.id.go_to_queues -> {
                startActivity(Intent(this, QueueActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}