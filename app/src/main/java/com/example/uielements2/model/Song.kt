package com.example.uielements2.model

class Song(var id: Int = 0, var title: String, var artist: String, var album: String) {
    override fun toString(): String {
        return "Title: $title, Artist: $artist, Album: $album"
    }
}