package com.example.livewallpaperautochanger
import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherAsyncTask : AsyncTask<Double, Void, String>() {

    override fun doInBackground(vararg params: Double?): String {
        val apiKey = "YOUR_API_KEY"
        val latitude = params[0]
        val longitude = params[1]

        val apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=$latitude&lon=$longitude&appid=$apiKey"

        val url = URL(apiUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.connectTimeout = 10000 // Set your preferred timeout
        connection.readTimeout = 10000 // Set your preferred timeout

        val responseCode = connection.responseCode

        if (responseCode == HttpURLConnection.HTTP_OK) {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }
            reader.close()
            return response.toString()
        }

        return ""
    }

    override fun onPostExecute(result: String) {
        // Handle the weather data (result) here
    }
}