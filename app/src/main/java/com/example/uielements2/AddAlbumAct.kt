package com.example.uielements2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import com.example.uielements2.R
import com.example.uielements2.SongsTableHandler
import com.example.uielements2.model.Album

class AddAlbumAct : AppCompatActivity() {
    lateinit var albumTitle: EditText
    lateinit var addAlbumBtn : Button
    lateinit var datePicker : DatePicker
    var releaseDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_album)

        albumTitle = findViewById(R.id.editAlbumET)
        addAlbumBtn = findViewById(R.id.updateAlbumBtn)
        val databaseHandler = SongsTableHandler(this)


        //Date picker
        datePicker = findViewById<DatePicker>(R.id.datePickerEdit) as DatePicker
        datePicker.init(2020, 11, 1, object: DatePicker.OnDateChangedListener{
            override fun onDateChanged(
                view: DatePicker?,
                year: Int,
                monthOfYear: Int,
                dayOfMonth: Int
            ) {
                releaseDate = "${datePicker.month + 1}/${datePicker.dayOfMonth}/${datePicker.year}"
            }
        })


        addAlbumBtn.setOnClickListener {
            val albumTitle_string = albumTitle.text.toString()
            val date_string = releaseDate
            val album = Album(albumTitle = albumTitle_string, releaseDate = date_string)
            if (databaseHandler.createAlbum(album)){
                Toast.makeText(this, "Album was added.", Toast.LENGTH_SHORT).show()
                albumAdapter.notifyDataSetChanged()

            } else {
                Toast.makeText(this, "Oooops!", Toast.LENGTH_SHORT).show()
            }

            clear()

        }

    }

    fun clear () {
        albumTitle.text.clear()
    }



}