package com.brewhog.android.retrofit_crud.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.brewhog.android.retrofit_crud.api.BookInterface
import com.brewhog.android.retrofit_crud.api.RetrofitClient
import com.brewhog.android.retrofit_crud.database.BookDao
import com.brewhog.android.retrofit_crud.database.BookDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun getApplication(application: Application): Context = application

    @Singleton
    @Provides
    fun getDatabase(application: Application): BookDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            BookDatabase::class.java,
            "book_database"
        ).build()
    }

    @Singleton
    @Provides
    fun getDatabaseDao(bookDatabase: BookDatabase): BookDao = bookDatabase.getBookDao()

    @Singleton
    @Provides
    fun getRetrofitClient(): RetrofitClient = RetrofitClient()

    @Singleton
    @Provides
    fun getBookInterface(retrofitClient: RetrofitClient): BookInterface = retrofitClient.getBookInterface()
}