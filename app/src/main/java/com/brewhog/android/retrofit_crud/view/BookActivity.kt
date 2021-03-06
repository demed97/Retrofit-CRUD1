package com.brewhog.android.retrofit_crud.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.brewhog.android.retrofit_crud.R
import com.brewhog.android.retrofit_crud.databinding.ActivityBookBinding
import com.brewhog.android.retrofit_crud.repository.BookRepository
import com.brewhog.android.retrofit_crud.viewmodel.BookViewModel
import com.brewhog.android.retrofit_crud.viewmodel.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class BookActivity : DaggerAppCompatActivity() {

    //    @Inject
    private lateinit var viewModel: BookViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bookId = intent.extras?.getInt("bookID")
//        val factory = ViewModelFactory(application, BookRepository.getRepository(application))
        viewModel = ViewModelProvider(this, factory).get(BookViewModel::class.java)
        val bookBinding =
            DataBindingUtil.setContentView<ActivityBookBinding>(this, R.layout.activity_book)
        bookBinding.lifecycleOwner = this

        if (bookId != null) {
            viewModel.showBookInfo(bookId)
        }

        bookBinding.viewModel = viewModel

        viewModel.closeActivityLiveData.observe(this, Observer {
            finish()
        })
    }
}