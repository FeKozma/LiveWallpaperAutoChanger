package com.example.livewallpaperautochanger

import android.content.Context
import android.content.SharedPreferences

object Storage {

    fun getVal(context: Context, name: String): Set<String>? {
        return getSharedPreferences(context).getStringSet(name, emptySet())
    }
    fun setVal(context: Context, name: String, value: Set<String>) {
        System.out.println("setval")
        System.out.println(name)
        var l = "";
        value.forEach { l += it + "\n" }
        System.out.println("recive urls: " + l)

        getVal(context, name).notNull {
            setSharedPrefrences(context, name, value + it)
        }

    }
    fun removeVal(context: Context, name: String, value: String) {
        getVal(context, name).notNull {
                images ->
            setSharedPrefrences(context, name, images.filter { it != value }.toSet())
        }

    }

    private fun setSharedPrefrences(context: Context, name: String, value: Set<String>) {
        with (this.getSharedPreferences(context).edit()) {
            putStringSet(name, value)
            //binding.textView.text = value.size.toString()
            apply()
        }
    }

    private fun getSharedPreferences(context: Context) :SharedPreferences {
        return context.getApplicationContext().getSharedPreferences("WALLPAPER_CHANGER_PREFRENCES",
            Context.MODE_PRIVATE)
    }

    fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
        if (this != null) f(this)
    }
}