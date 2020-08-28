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

    private var _addBookLiveData = MutableLiveData<Unit>()
    val addBookLiveData: LiveData<Unit> = _addBookLiveData

    private var _closeActivityLiveData = MutableLiveData<Unit>()
    val closeActivityLiveData: LiveData<Unit> = _closeActivityLiveData
//    var mutableId = MutableLiveData<String>()
//    var title = MutableLiveData<String>()
//    var author = MutableLiveData<String>()
//    var description = MutableLiveData<String>()
//    var published = MutableLiveData<String>()

    var book = MutableLiveData<Book>(Book(null, null, null, null))

    var bookId = 0

    //var bookLiveData : MutableLiveData<Book> = MutableLiveData()
    private var context = application.applicationContext


    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("TAG", "CoroutineExceptionHandler got", exception)
    }

    private val scope = viewModelScope + handler + Dispatchers.Default

    fun loadBookListFromDatabase() {

        scope.launch {
            bookRepository.getAllBooksFromDB().collect {
                _bookLiveData.postValue(it)
            }
        }
    }

    fun loadBookListFromServer() {

        scope.launch {
            bookRepository.getAllBooks().collect {
                _bookLiveData.postValue(it)
            }
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
        book.postValue(bookRepository.getBookByID(id))

//        scope.launch {
//        val book1 = bookRepository.getBookByID(id)
//        mutableId.postValue(book1.id.toString())
//        title.postValue(book1.title)
//        author.postValue(book1.author)
//        description.postValue(book1.description)
//        published.postValue(book1.published.toString())
//        }
//        _book.postValue(bookRepository.getBookByID(id))
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

    private fun addBook(book: Book) {
        scope.launch {
            bookRepository.addNewBook(book)
            closeActivity()
        }
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

    private fun updateBook(id: Int, book: Book) {
        scope.launch {
            bookRepository.updateBook(id, book)
            closeActivity()
        }
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

    private fun deleteBook(id: Int, book: Book) {
        scope.launch {
            bookRepository.deleteBook(id, book)
            closeActivity()
        }
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


    private fun closeActivity() {
        _closeActivityLiveData.postValue(Unit)
    }

    fun addNewBook() {
        _addBookLiveData.postValue(Unit)
    }

    fun showBookInfo(id: Int) {
        scope.launch {
            loadBookByID(id)
            bookId = id
        }
    }

    fun deleteBook() {
            deleteBook(bookId, book.value!!)
    }

    fun addOrUpdateBook() {
        val targetBook = book.value
        val targetId = targetBook?.id ?: 0
        if (targetId != 0) {
            updateBook(targetId, targetBook!!)
        } else {
//            val boook = Book(
//                book.value?.title,
//                book.value?.author,
//                book.value?.description,
//                book.value?.published
//            )
            addBook(targetBook!!)
        }
    }
}