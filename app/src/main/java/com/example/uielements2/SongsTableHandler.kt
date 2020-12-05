package com.example.uielements2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.uielements2.model.Album
import com.example.uielements2.model.AlbumSong
import com.example.uielements2.model.Song

class SongsTableHandler (var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private var DATABASE_VERSION = 2
        private var DATABASE_NAME = "songs_database"
        private var TABLE_NAME ="songs"
        private var COL_ID = "id"
        private var COL_TITLE = "title"
        private var COL_ARTIST = "artist"
        private var COL_ALBUM = "album_title"

        private val ALBUMS_TABLE = "albums"
        private val ALBUM_TITLE = "title"
        private val ALBUM_RELEASE_DATE = "release_date"

        //Album Songs
        private val ALBUM_SONGS = "album_songs"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("Not yet implemented")
        // DEFINE THE QUERY
        val query = "CREATE TABLE "+ TABLE_NAME + " ( "+ COL_ID+ " INTEGER PRIMARY KEY, " + COL_TITLE + " TEXT, "+ COL_ARTIST+ " TEXT, " + COL_ALBUM + " TEXT)"
        val secondQuery = "CREATE TABLE "+ ALBUMS_TABLE + " ( "+ COL_ID + " INTEGER PRIMARY KEY, " + ALBUM_TITLE + " TEXT, "+ ALBUM_RELEASE_DATE+ " TEXT)"
        val thirdQuery = "CREATE TABLE "+ ALBUM_SONGS + " ( "+ COL_ID+ " INTEGER PRIMARY KEY, " + COL_TITLE + " TEXT, " + COL_ALBUM + " TEXT)"
        // EXECUTE THE QUERY
        if (db != null) {
            db.execSQL(query)
            db.execSQL(secondQuery)
            db.execSQL(thirdQuery)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //TODO("Not yet implemented")
        db!!.execSQL("DROP TABLE IF EXIST " + TABLE_NAME)
        db!!.execSQL("DROP TABLE IF EXISTS " + ALBUMS_TABLE)
        db!!.execSQL("DROP TABLE IF EXISTS " + ALBUM_SONGS)
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
        val query = "SELECT * FROM " + TABLE_NAME
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


    fun delete(book: Song): Boolean {
        val database = this.writableDatabase
        val result = database.delete(TABLE_NAME, "id=${book.id}", null)

        if (result == 0 ){
            false
        }
        return true
    }

    fun createAlbum(album: Album): Boolean {
        val database= this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(ALBUM_TITLE, album.albumTitle)
        contentValue.put(ALBUM_RELEASE_DATE, album.releaseDate)
        val result = database.insert(ALBUMS_TABLE, null, contentValue)

        if (result==(0).toLong()) {
            return  false
        }
        return true
    }

    fun readAlbum(): MutableList<Album>{
        val albumsList: MutableList<Album> = ArrayList<Album>()
        val query = "SELECT * FROM "+  ALBUMS_TABLE
        val database = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = database.rawQuery(query, null)
        } catch (e: SQLiteException){
            return albumsList
        }

        var id: Int
        var albumTitle: String
        var releaseDate : String
        if (cursor.moveToFirst()) {
            do {
                id=cursor.getInt(cursor.getColumnIndex(COL_ID))
                albumTitle= cursor.getString(cursor.getColumnIndex(ALBUM_TITLE))
                releaseDate = cursor.getString(cursor.getColumnIndex(ALBUM_RELEASE_DATE))
                val album = Album(id, albumTitle, releaseDate)
                albumsList.add(album)
            } while (cursor.moveToNext())
        }
        return albumsList
    }

    fun readSongsUsingAlbumTitle(album_title: String): MutableList<Song>{
        val songsList: MutableList<Song> = ArrayList<Song>()
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_TITLE = $album_title"
        val database = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = database.rawQuery(query, null)
        } catch (e: SQLiteException){
            return songsList
        }

        var id: Int
        var title: String
        var artist : String
        var album : String
        if (cursor.moveToFirst()) {
            do {
                id=cursor.getInt(cursor.getColumnIndex(COL_ID))
                title= cursor.getString(cursor.getColumnIndex(COL_TITLE))
                artist = cursor.getString(cursor.getColumnIndex(COL_ARTIST))
                album= cursor.getString(cursor.getColumnIndex(COL_ALBUM))
                val book = Song(id, title, artist, album)
                songsList.add(book)
            } while (cursor.moveToNext())
        }
        return songsList

    }

    fun readOneAlbum(album_id : Int): Album {
        var album: Album = Album(0, "", "")
        val query = "SELECT * FROM $ALBUMS_TABLE WHERE id = $album_id"
        val database = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = database.rawQuery(query, null)
        } catch (e: SQLiteException){
            return album
        }

        var id: Int
        var title: String
        var releaseDate : String
        if (cursor.moveToFirst()) {
            do {
                id=cursor.getInt(cursor.getColumnIndex(COL_ID))
                title= cursor.getString(cursor.getColumnIndex(ALBUM_TITLE))
                releaseDate= cursor.getString(cursor.getColumnIndex(ALBUM_RELEASE_DATE))
                album = Album(id, title, releaseDate)
            } while (cursor.moveToNext())
        }
        return album

    }

    fun updateAlbum(album: Album): Boolean {
        val database= this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(ALBUM_TITLE, album.albumTitle)
        contentValue.put(ALBUM_RELEASE_DATE, album.releaseDate)
        val result = database.update(ALBUMS_TABLE, contentValue, "id="+album.id, null)
        if (result==0) {
            return  false
        }
        return true
    }

    fun deleteAlbum(album: Album): Boolean {
        val database = this.writableDatabase
        val result = database.delete(TABLE_NAME, "id=${album.id}", null)

        if (result == 0 ){
            false
        }
        return true
    }
    fun createAlbumSongs(song: AlbumSong): Boolean {

        val database= this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(COL_TITLE, song.albumSong)
        contentValue.put(COL_ALBUM, song.albumTitle)
        val result = database.insert(ALBUM_SONGS, null, contentValue)
        if (result==(0).toLong()) {
            return  false
        }
        return true
    }

    fun readAlbumSongs(title: String): MutableList<AlbumSong>{
        val songsList: MutableList<AlbumSong> = ArrayList<AlbumSong>()
        val query = "SELECT * FROM $ALBUM_SONGS WHERE $COL_ALBUM = '$title' "
        val database = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = database.rawQuery(query, null)
        } catch (e: SQLiteException){
            return songsList
        }

        var id: Int
        var title: String
        var album_title : String
        if (cursor.moveToFirst()) {
            do {
                id=cursor.getInt(cursor.getColumnIndex(COL_ID))
                title= cursor.getString(cursor.getColumnIndex(COL_TITLE))
                album_title = cursor.getString(cursor.getColumnIndex(COL_ALBUM))
                val albumSong = AlbumSong(id, title, album_title)
                songsList.add(albumSong)
            } while (cursor.moveToNext())
        }
        return songsList


    }

    fun deleteAlbumSongs(albumSong: AlbumSong): Boolean {
        val database = this.writableDatabase
        val result = database.delete(ALBUM_SONGS, "id=${albumSong.id}", null)
        if (result == 0 ){
            false
        }
        return true

    }





}