package com.brewhog.android.retrofit_crud.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.brewhog.android.retrofit_crud.api.RetrofitClient
import com.brewhog.android.retrofit_crud.model.Book
import com.brewhog.android.retrofit_crud.repository.BookRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@ExperimentalCoroutinesApi
class BookViewModel(application: Application, private val bookRepository: BookRepository) :
    ViewModel() {

    private var _bookLiveData = MutableLiveData<List<Book>>()
    val bookLiveData: LiveData<List<Book>> = _bookLiveData


    //    var liveData: MutableLiveData<List<Book>> = MutableLiveData()
    private var _book = MutableLiveData<Book>()
    var book: LiveData<Book> = _book

    //    var book: LiveData<Book> = _book
    var bookId = 0

    //var bookLiveData : MutableLiveData<Book> = MutableLiveData()
    private var context = application.applicationContext


    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("TAG", "CoroutineExceptionHandler got", exception)
    }

    private val scope = viewModelScope + handler + Dispatchers.Default


    private suspend fun loadBookListFromServer() {
        bookRepository.getAllBooks().collect {
            _bookLiveData.postValue(it)
        }
//            .enqueue(object : Callback<List<Book>> {
//            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
//                Toast.makeText(context, "Books loading was failed", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
//                if (response.isSuccessful) {
//                    liveData.postValue(response.body())
//                } else {
//                    Toast.makeText(context, "Books loading is unsuccessful", Toast.LENGTH_LONG)
//                        .show()
//                }
//            }
//        })
    }

    private suspend fun loadBookByID(id: Int) {
        _book.postValue(bookRepository.getBookByID(id))
//        _book.value =
//            .enqueue(object : Callback<Book> {
//            override fun onFailure(call: Call<Book>, t: Throwable) {
//                Toast.makeText(context, "Book loading was failed", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(call: Call<Book>, response: Response<Book>) {
//                if (response.isSuccessful) {
//                    book.set(response.body())
//                    //bookLiveData.postValue(response.body())
//                    Toast.makeText(context, "Book loading is successful", Toast.LENGTH_LONG).show()
//
//                } else {
//                    Toast.makeText(context, "Book loading is unsuccessful", Toast.LENGTH_LONG)
//                        .show()
//                }
//            }
//        })
    }

    private suspend fun addBook(book: Book) {
        bookRepository.addNewBook(book)
//            .enqueue(object : Callback<Book> {
//            override fun onFailure(call: Call<Book>, t: Throwable) {
//                Toast.makeText(context, "Book wasn't add!", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(call: Call<Book>, response: Response<Book>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(context, "Book was add successful!", Toast.LENGTH_LONG).show()
//                } else {
//                    Toast.makeText(context, "Unsuccessful!", Toast.LENGTH_LONG).show()
//                }
//            }
//        })
    }

    private suspend fun updateBook(id: Int, book: Book) {
        bookRepository.updateBook(id, book)
//            .enqueue(object : Callback<Book> {
//            override fun onFailure(call: Call<Book>, t: Throwable) {
//                Toast.makeText(context, "Book wasn't update!", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(call: Call<Book>, response: Response<Book>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(context, "Book was updated successful!", Toast.LENGTH_LONG)
//                        .show()
//                } else {
//                    Toast.makeText(context, "Unsuccessful!", Toast.LENGTH_LONG).show()
//                }
//            }
//
//        })
    }

    private suspend fun deleteBook(id: Int, book: Book) {
        bookRepository.deleteBook(id, book)
//            .enqueue(object : Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Toast.makeText(context, "Book wasn't delete!", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(context, response.body()?.string(), Toast.LENGTH_LONG).show()
//                } else {
//                    Toast.makeText(context, "Unsuccessful!", Toast.LENGTH_LONG).show()
//                }
//            }
//
//        })
    }

    fun showBookList() {
        scope.launch {
            loadBookListFromServer()
        }
    }

    fun showBookInfo(id: Int) {
        scope.launch {
            loadBookByID(id)
            bookId = id
        }
    }

    fun deleteBook() {
        scope.launch {
            deleteBook(bookId, _book.value!!)
        }
    }

    fun addOrUpdateBook() {
        val targetBook = _book.value!!
        val targetId = targetBook?.id ?: 0
        scope.launch {
            if (targetId != 0) {
                updateBook(targetId, targetBook!!)
            } else {
                addBook(targetBook!!)
            }
        }
    }
}