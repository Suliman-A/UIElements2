package com.example.uielements2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.uielements2.model.Song

class SongsTableHandler (var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private var DATABASE_VERSION = 1
        private var DATABASE_NAME = "songs_database"
        private var TABLE_NAME ="songs"
        private var COL_ID = "id"
        private var COL_TITLE = "title"
        private var COL_ARTIST = "artist"
        private var COL_ALBUM = "album"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("Not yet implemented")
        // DEFINE THE QUERY
        var query = "CREATE TABLE "+TABLE_NAME+" ("+COL_ID+" INTEGER PRIMARY KEY, "+COL_TITLE+" TEXT, "+COL_ARTIST+" TEXT, "+COL_ALBUM+" TEXT)"
        // EXECUTE THE QUERY
        if (db != null) {
            db.execSQL(query)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //TODO("Not yet implemented")
        db!!.execSQL("DROP TABLE IF EXIST " + TABLE_NAME)
        onCreate(db)
    }

    fun create(song: Song): Boolean {
        //get the database
        val database = this.writableDatabase
        //set content value
        val contentValues = ContentValues()
        contentValues.put(COL_TITLE, song.title)
        contentValues.put(COL_ARTIST, song.artist)
        contentValues.put(COL_ALBUM, song.album)
        // insert
        val result = database.insert(TABLE_NAME, null, contentValues)
        // check the result
        if (result == (0).toLong()) {
            return false
        }
        return true
    }

    fun read(): MutableList<Song>{
        val songList: MutableList<Song> = ArrayList<Song>()
        val query = "SELECT * FROM"+ TABLE_NAME
        val database = this.readableDatabase
        var cursor: Cursor?  = null
        try {
            cursor = database.rawQuery(query, null)
        }catch (e: SQLiteException){
            return songList
        }
        var id: Int
        var title: String
        var artist: String
        var album: String
        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                title = cursor.getString(cursor.getColumnIndex(COL_TITLE))
                artist = cursor.getString(cursor.getColumnIndex(COL_ARTIST))
                album = cursor.getString(cursor.getColumnIndex(COL_ALBUM))
                val song = Song(id, title, artist, album)
                songList.add(song)
            }while (cursor.moveToNext())
        }
        return songList
    }

    fun readOne(song_id: Int): Song {
        var song: Song = Song(0, "" , "" , "")
        val query = "SELECT * FROM $TABLE_NAME WHERE id = $song_id"
        val database = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = database.rawQuery(query, null)
        } catch (e: SQLiteException){
            return song
        }
        var id: Int
        var title: String
        var author : String
        var album : String
        if (cursor.moveToFirst()) {
            id=cursor.getInt(cursor.getColumnIndex(COL_ID))
            title= cursor.getString(cursor.getColumnIndex(COL_TITLE))
            author = cursor.getString(cursor.getColumnIndex(COL_ARTIST))
            album= cursor.getString(cursor.getColumnIndex(COL_ALBUM))
            song = Song(id, title, author, album)
        }
        return song
    }

    fun update(song: Song): Boolean {
        val database= this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(COL_TITLE, song.title)
        contentValue.put(COL_ARTIST, song.artist)
        contentValue.put(COL_ALBUM, song.album)
        val result = database.update(TABLE_NAME, contentValue, "id="+song.id, null)
        if (result==0) {
            return  false
        }
        return true
    }


    fun delete(book : Song): Boolean {
        val database = this.writableDatabase
        val result = database.delete(TABLE_NAME, "id=${book.id}", null)

        if (result == 0 ){
            false
        }
        return true


    }


}