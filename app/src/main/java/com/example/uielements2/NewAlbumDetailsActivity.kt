package com.example.uielements2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import com.example.uielements2.R
import com.example.uielements2.SongsTableHandler
import com.example.uielements2.model.Album
import com.example.uielements2.model.AlbumSong

lateinit var albumSongsArrayAdapter : ArrayAdapter<AlbumSong>
class NewAlbumDetailsAct : AppCompatActivity() {

    lateinit var albumTitle: TextView
    lateinit var albumReleaseDate: TextView
    lateinit var albumSongsListView : ListView
    lateinit var songsDatabaseHandler: SongsTableHandler
    lateinit var album : Album
    lateinit var albumSongs: MutableList<AlbumSong>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_album_details)

        val album_id = intent.getIntExtra("album_id", 0)

        albumTitle = findViewById(R.id.albumTitle_new)
        albumReleaseDate = findViewById(R.id.releaseDate)
        albumSongsListView = findViewById(R.id.albumSongsListView)
        songsDatabaseHandler = SongsTableHandler(this)

        album = songsDatabaseHandler.readOneAlbum(album_id)

        albumSongs = songsDatabaseHandler.readAlbumSongs(album.albumTitle)

        albumSongsArrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, albumSongs)
        albumSongsListView.adapter = albumSongsArrayAdapter


        albumTitle.setText(album.albumTitle)
        albumReleaseDate.setText(album.releaseDate)
        registerForContextMenu(albumSongsListView)

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.delete_song_album, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when(item.itemId) {
            R.id.deleteSongOnAlbum -> {
                val albumSong = albumSongs[menuInfo.position]
                if(songsDatabaseHandler.deleteAlbumSongs(albumSong)){
                    albumSongs.removeAt(menuInfo.position)
                    albumSongsArrayAdapter.notifyDataSetChanged()
                    Toast.makeText(this, "Song deleted.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show()
                }

                true
            }
            else -> return super.onContextItemSelected(item)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_song_album, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.addSongToAlbum -> {
                val intent = Intent(this, AddSongToAlbumAct::class.java)
                val albumTitle = album.albumTitle
                intent.putExtra("albumTitle", albumTitle)
                startActivity(intent)
                true
            }

        }

        return super.onOptionsItemSelected(item)
    }


}