package com.sample.todolistapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.moviegallery.api.MovieItem
import com.sample.moviegallery.database.MovieDao

@Database(entities = [ MovieItem::class ], version=1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

}