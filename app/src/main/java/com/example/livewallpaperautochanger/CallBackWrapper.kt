package com.example.livewallpaperautochanger
import androidx.activity.result.ActivityResultCallback

class CallBackWrapper<T> : ActivityResultCallback<T> {
    var callback: ActivityResultCallback<T>? = null

    /**
     * Called when the result is available
     */
    override fun onActivityResult(result: T?) {
        callback?.onActivityResult(result);
    }
}