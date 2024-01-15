package com.sample.moviegallery

import android.app.Application

class MovieGalleryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MovieRepository.initialize(this)
    }
}
