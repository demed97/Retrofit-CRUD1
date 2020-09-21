package com.brewhog.android.retrofit_crud.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.brewhog.android.retrofit_crud.R
import com.brewhog.android.retrofit_crud.databinding.FragmentBookBinding
import com.brewhog.android.retrofit_crud.util.observer
import com.brewhog.android.retrofit_crud.viewmodel.BookViewModel
import com.brewhog.android.retrofit_crud.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class BookFragment : DaggerFragment() {

    private lateinit var viewModel: BookViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val bookId = intent.extras?.getInt("bookID")
        val bookId = arguments?.getInt("bookID")
        viewModel = ViewModelProvider(this, factory).get(BookViewModel::class.java)
        val bookBinding =
            DataBindingUtil.setContentView<FragmentBookBinding>(requireActivity(), R.layout.fragment_book)
        bookBinding.lifecycleOwner = this

        if (bookId != null) {
            viewModel.showBookInfo(bookId)
        }

        bookBinding.viewModel = viewModel
        viewModel.closeActivityEvent.observer(this){
            Navigation.findNavController(view).popBackStack()
        }
    }
}
