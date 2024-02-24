package com.example.livewallpaperautochanger

import android.app.ActivityManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.GridView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.livewallpaperautochanger.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var currFunction: Int = -1;
    private fun sharedPreference(): SharedPreferences {return getSharedPreferences("WALLPAPER_CHANGER_PREFRENCES",Context.MODE_PRIVATE)}
    lateinit var courseGRV: GridView
    lateinit var courseList: List<GridViewModal>
    private lateinit var images: Images

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener { loadVid(R.string.sunnyWeather) }
        images = Images(this@MainActivity, listOf(
            Category("sunny", findViewById(R.id.idGRV)) { this.sunny() }
        ))


        images.load()
        //loadImages(R.array.sunnyWeather);

        isLocationPermissionGranted()
        startJobService()


    }

    fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                123
            )
            false
        } else {
            true
        }
    }

    private fun startJobService() {
        if (!isMyServiceRunning(ImageJobService::class.java)) {
            val jobScheduler = ContextCompat.getSystemService(
                this,
                JobScheduler::class.java
            ) as JobScheduler


            val jobInfo = JobInfo.Builder(123, ComponentName(this, ImageJobService::class.java))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresCharging(false)
                .setMinimumLatency(TimeUnit.MINUTES.toMillis(1))
                //.setPeriodic(60*1000L)
                .build()

            val result = jobScheduler.schedule(jobInfo)
            if (result == JobScheduler.RESULT_SUCCESS) {
                Toast.makeText(
                    this, "JobScheduler.RESULT_SUCCESS",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this, "JobScheduler.RESULT_FAILED",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                this, "No service need to start",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    fun sunny(): Boolean {
        return true
    }

    fun allowURI(uri: Uri): String {
        val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
        contentResolver.takePersistableUriPermission(uri, flag)
        return uri.toString()
    }

    val singleWrapper: CallBackWrapper<List<Uri>> = CallBackWrapper()

    val pickMultipleMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5), singleWrapper)

    fun loadVid(resID: Int) {
        // Registers a photo picker activity launcher in multi-select mode.
        // In this example, the app lets the user select up to 5 media files.


        // For this example, launch the photo picker and let the user choose images
        // and videos. If you want the user to select a specific type of media file,
        // use the overloaded versions of launch(), as shown in the section about how
        // to select a single media item.
        singleWrapper.callback = ActivityResultCallback{
            // Callback is invoked after the user selects media items or closes the
            // photo picker.
            if (it.isNotEmpty()) {

                images.setVal("sunny", it.map { uri -> allowURI(uri) }.toSet())
                Toast.makeText(
                    this, "Images loaded",
                    Toast.LENGTH_LONG
                ).show()

            } else {
                Toast.makeText(
                    this, "You haven't picked Image",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))

//        val intent = Intent()
//            .setType("image/*")
//            .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//            .setAction(Intent.ACTION_GET_CONTENT)
//        currFunction = resID;
//        startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == 111 && resultCode == RESULT_OK) {
//            val inputMediaItem = MediaItem.Builder()
//                .setUri(data?.data)
//                .setClippingConfiguration(
//                    MediaItem.ClippingConfiguration.Builder()
//                        .setStartPositionMs(0)
//                        .setEndPositionMs(10_000)
//                        .build())
//                .build()
//
//            val contentResolver = applicationContext.contentResolver
//
//            val displayMetrics by lazy { Resources.getSystem().displayMetrics }
//             val deviceWidth by lazy { (displayMetrics.widthPixels).toFloat() }
//             val deviceHeight by lazy { (displayMetrics.heightPixels).toFloat() }
//            val rect = Rect(0,0,deviceWidth.toInt(),deviceHeight.toInt())
//
//            setVal(R.string.sunnyWeater, data?.data.toString())
//
//
//            //ContentResolver().loadThumbnail()
//            //val selectedFile =  // The URI with the location of the file
//        }
//    }
//    var imageEncoded: String? = null
//    var imagesEncodedList: List<String>? = null
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        try {
//            // When an Image is picked
//            if (requestCode == 111 && resultCode == RESULT_OK && null != data) {
//                // Get the Image from data
//                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
//                imagesEncodedList = ArrayList<String>()
//                if (data.data != null) {
//                    val mImageUri = data.data
//                    System.out.println("one file")
//                    setVal(currFunction, mImageUri.toString())
//
//
//                } else {
//                    if (data.clipData != null) {
//                        val mClipData = data.clipData
//                        val mArrayUri = ArrayList<String>()
//                        for (i in 0 until mClipData!!.itemCount) {
//                            val item = mClipData.getItemAt(i)
//                            val uri = item.uri
//                            mArrayUri.add(uri.toString())
//
//                        }
//                        System.out.println("testtest")
//                        setVal(currFunction, mArrayUri.toSet())
//
//
//                        System.out.println("Selected Images " + mArrayUri.size)
//                    }
//                }
//            } else {
//                Toast.makeText(
//                    this, "You haven't picked Image",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        } catch (e: Exception) {
//            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
//                .show()
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }

    fun setVal(resID: Int, value: String?) {
        value.notNull {
           setVal(resID, setOf(it))
        }
    }

    fun setVal(resID: Int, value: Set<String>) {
        sharedPreference().getStringSet(resID.toString(), null).notNull {
            with (sharedPreference().edit()) {
                putStringSet(resID.toString(), value + it)
                binding.textView.text = value.size.toString()
                //loadImages(resID);
                toast("images loaded!")
                apply()
            }
        }
    }

//    fun loadImages(resID: Int) {
//        courseGRV = findViewById(R.id.idGRV)
//        courseList = listOf<GridViewModal>()
//        sharedPreference().getStringSet(resID.toString(), null).notNull {
//            courseList = it.fold(courseList) {acc, uri ->
//                acc + (GridViewModal(uri, View.INVISIBLE))
//            }
//        }
//
//        val courseAdapter = GridRVAdapter( courseGRV,this@MainActivity)
//        courseGRV.adapter = courseAdapter
//
//
//    }

    fun toast(msg: String) {
        Toast.makeText(
        this, msg,
        Toast.LENGTH_LONG
        ).show()
    }

    fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
        if (this != null) f(this)
    }
}