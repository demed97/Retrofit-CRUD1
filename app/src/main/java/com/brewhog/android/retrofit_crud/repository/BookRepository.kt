package com.brewhog.android.retrofit_crud.repository

import android.app.Application
import com.brewhog.android.retrofit_crud.api.RetrofitClient
import com.brewhog.android.retrofit_crud.database.BookDao
import com.brewhog.android.retrofit_crud.database.BookDatabase
import com.brewhog.android.retrofit_crud.model.Book
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
class BookRepository(application: Application) {

    private var dao: BookDao

    private val retrofitInterface = RetrofitClient().getBookInterface()

    private var allBookList = mutableListOf<Book>()

    private val allBookStateFlow = MutableStateFlow<List<Book>>(listOf())

    init {
        val database = BookDatabase.getDatabase(application)!!
        dao = database.getBookDao()
    }

    suspend fun getAllBooksFromDB(): StateFlow<List<Book>> {
        allBookStateFlow.value = dao.getAllBooks()
        return allBookStateFlow
    }

    suspend fun getAllBooks(): StateFlow<List<Book>> {
        dao.deleteAllBook()
        dao.addAllBook(retrofitInterface.getAllBooks())
        allBookStateFlow.value = dao.getAllBooks()
        return allBookStateFlow
    }

    suspend fun getBookByID(id: Int): Book {
        return retrofitInterface.getBookByID(id) ?: dao.getBookByID(id)
    }

    suspend fun addNewBook(book: Book) {
        retrofitInterface.addNewBook(book)
        dao.addNewBook(book)
//        allBookStateFlow.value = dao.getAllBooks()
    }

    suspend fun deleteBook(id: Int, book: Book) {
        retrofitInterface.deleteBook(id)
        dao.deleteBook(book)
//        allBookStateFlow.value = dao.getAllBooks()
    }

    suspend fun updateBook(id: Int, book: Book) {
        retrofitInterface.updateBook(id, book)
        dao.updateBook(book)
//        allBookStateFlow.value = dao.getAllBooks()
    }

    companion object {

        fun getRepository(application: Application) = BookRepository(application)
    }
}