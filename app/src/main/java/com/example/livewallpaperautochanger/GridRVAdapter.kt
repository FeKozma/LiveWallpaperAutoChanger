package com.example.livewallpaperautochanger

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView


class GridRVAdapter(   private var courseList: List<GridViewModal>,
                                 private val context: Context,
                       remove: (uri: String) -> Unit): BaseAdapter() {

    private val remove = remove
    // in base adapter class we are creating variables
    // for layout inflater, course image view and course text view.
    private var layoutInflater: LayoutInflater? = null
//    private lateinit var image: ImageView
//    private lateinit var removeButtn: Button

    // below method is use to return the count of course list
    override fun getCount(): Int {
        return courseList.size
    }

    // below function is use to return the item of grid view.
    override fun getItem(position: Int): GridViewModal {
        return courseList.get(position)
    }

    // below function is use to return item id of grid view.
    override fun getItemId(position: Int): Long {
        return 0
    }

    // in below function we are getting individual item of grid view.
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        lateinit var image: ImageView
        lateinit var removeButtn: Button
        // on blow line we are checking if layout inflater
        // is null, if it is null we are initializing it.
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        // on the below line we are checking if convert view is null.
        // If it is null we are initializing it.
        if (convertView == null) {
            // on below line we are passing the layout file
            // which we have to inflate for each item of grid view.
            convertView = layoutInflater!!.inflate(R.layout.gridview_item, null)
        }
        // on below line we are initializing our course image view
        // and course text view with their ids.
        image = convertView!!.findViewById(R.id.idIVCourse)
        removeButtn = convertView!!.findViewById(R.id.removeButton)
        // on below line we are setting image for our course image view.
        image.setImageURI(
            Uri.parse(courseList.get(position).uri))

        removeButtn.visibility = courseList.get(position).remove
        // at last we are returning our convert view.
        image.setOnClickListener(
            View.OnClickListener { view: View ->
                removeButtn.visibility = (if (removeButtn.visibility == View.INVISIBLE) View.VISIBLE else View.INVISIBLE)
                courseList.get(position).remove = removeButtn.visibility
            }
        )

        removeButtn.setOnClickListener { view: View ->
            image.visibility = View.INVISIBLE
            this.remove(courseList.get(position).uri)
        }

        //convertView.setOnClickListener()
        return convertView
    }

    fun allowURI(uri: Uri): String {
        val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
        context.contentResolver.takePersistableUriPermission(uri, flag)
        return uri.toString()
    }

}