package com.brewhog.android.retrofit_crud.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.brewhog.android.retrofit_crud.model.Book

@Database(entities = [Book::class], version = 1)
abstract class BookDatabase : RoomDatabase() {

    abstract fun getBookDao(): BookDao

//    companion object {
//        var database: BookDatabase? = null
//
//        fun getDatabase(application: Application): BookDatabase? {
//            if (database == null) {
//                database = Room.databaseBuilder(
//                    application.applicationContext,
//                    BookDatabase::class.java,
//                    "book_database"
//                ).build()
//            }
//            return database
//        }
//    }
}