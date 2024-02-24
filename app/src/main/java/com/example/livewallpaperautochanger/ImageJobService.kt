package com.example.livewallpaperautochanger

import android.annotation.SuppressLint
import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ImageJobService : JobService()  {

//    private fun checkLocationPermission() {
//        if (ContextCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                123
//            )
//        }
//    }

    @SuppressLint("MissingPermission")
    override fun onStartJob(p0: JobParameters?): Boolean {
        System.out.println("hello hello hello donkey")
        Log.d("ImageJobService", "hello hello hello donkey")
//        getWeather.getUserLocation(this.applicationContext)
//        val images = Storage.getVal(this.applicationContext, "sunny")
//        images?.first {imgUri ->
//            // val i = (0..category.gridView.adapter.count -1).random()
//            //                val imgUri = (category.gridView.adapter.getItem(i) as GridViewModal).uri
//            val wallpaperManager = WallpaperManager.getInstance(this.applicationContext)
//            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.applicationContext.contentResolver, Uri.parse(imgUri)))
//            } else {
//                MediaStore.Images.Media.getBitmap(this.applicationContext.contentResolver, Uri.parse(imgUri))
//            }
//            wallpaperManager.setBitmap(bitmap)
//            return true
//        }
//        return true

        // Create a LocationRequest

        val locationRequest = com.google.android.gms.location.LocationRequest.create().apply {
            priority = com.google.android.gms.location.LocationRequest.PRIORITY_LOW_POWER
        }

        // Initialize the FusedLocationProviderClient
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        //checkLocationPermission();
        // Request location updates
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

        return true // Return true if there's more work to be done in the background
    }

    // Create a LocationCallback
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // Handle the received location updates here
            val location = locationResult.lastLocation
            // Do something with the location data
            Log.d("getWeater",location.latitude.toString() + ", " + location.longitude.toString())
            //WeatherAsyncTask().execute(latitude, longitude)
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(WeatherService::class.java)
            val call = service.getCurrentWeatherData(location.latitude.toString(), location.longitude.toString(), "3e16c89a703eb235f504e41839404c1d")

            call.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    if (response.code() == 200) {
                        val weatherResponse = response.body()!!

                        weatherResponse.main!!.temp
                        Log.d("ImgaeJobService", "donkey: " + weatherResponse.main!!.temp)
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Log.d("ImgaeJobService", "donkey: " + t.message.orEmpty())
                }
            })
        }
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        return true
    }
}