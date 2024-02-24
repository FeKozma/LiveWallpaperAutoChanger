package com.example.livewallpaperautochanger

import android.widget.GridView

data class Category (
    val name: String,
    var gridView : GridView,
    val function: (() -> Boolean)
)