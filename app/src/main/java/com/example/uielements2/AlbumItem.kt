package com.example.uielements2

import java.io.Serializable

class AlbumItem : Serializable{
    var icons:Int ? = 0

    constructor(icons: Int?) {
        this.icons = icons
    }
}