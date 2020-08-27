package com.brewhog.android.retrofit_crud.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brewhog.android.retrofit_crud.repository.BookRepository

class ViewModelFactory(
    private val application: Application,
    private val bookRepository: BookRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(application, bookRepository) as T
        } else {
            throw IllegalArgumentException("no one correct class")
        }
    }
}