package com.example.livewallpaperautochanger

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import com.example.livewallpaperautochanger.databinding.ActivityMainBinding
import com.example.livewallpaperautochanger.Storage.notNull


class Images(private val context: Context, private val categories: List<Category>) {
    lateinit var binding: ActivityMainBinding
    lateinit var courseList: List<GridViewModal>




    fun load() {
        categories.map { category ->
            courseList = listOf<GridViewModal>()
            Storage.getVal(context, category.name).notNull {
                courseList = it.fold(courseList) { acc, uri ->
                    acc + (GridViewModal(uri, View.INVISIBLE))
                }
            }

            var l = "";
            courseList.forEach {
                l += it.uri
            }
            System.out.println("this is it")
            System.out.println(l)
            System.out.println(category.gridView.id.toString())

            val courseAdapter =
                GridRVAdapter(courseList, this.context, { uri -> Storage.removeVal(context, category.name, uri); load() })
            category.gridView.adapter = courseAdapter
        }
    }

    fun setVal(name: String, value: Set<String>) {
        Storage.setVal(context, name, value)
        load()
    }
}