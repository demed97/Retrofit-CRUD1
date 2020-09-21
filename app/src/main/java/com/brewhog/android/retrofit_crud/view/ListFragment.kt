package com.brewhog.android.retrofit_crud.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.brewhog.android.retrofit_crud.R
import com.brewhog.android.retrofit_crud.databinding.FragmentListBinding
import com.brewhog.android.retrofit_crud.util.BookAdapter
import com.brewhog.android.retrofit_crud.util.BookHolder
import com.brewhog.android.retrofit_crud.util.observer
import com.brewhog.android.retrofit_crud.viewmodel.BookViewModel
import com.brewhog.android.retrofit_crud.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ListFragment : DaggerFragment() {

    private lateinit var viewModel: BookViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var adapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val adapter = BookAdapter(listOf())
//        val factory = ViewModelFactory(application, BookRepository.getRepository(application))
        viewModel = ViewModelProvider(this, factory).get(BookViewModel::class.java)
        observeToLiveData(viewModel, adapter)


        adapter.callback = object : BookAdapter.AdapterCallback {
            override fun callActivityFromHolder(holder: BookHolder, id: Int) {
                holder.itemView.setOnClickListener(View.OnClickListener {
                    Log.d("click", "click")
                    val bundle = Bundle()
                    bundle.putInt("bookID", id)
                    findNavController(this@ListFragment).navigate(R.id.bookFragment, bundle)

//                    val intent = Intent(requireContext(), BookActivity::class.java)
//                    intent.putExtra("bookID", id)
//                    requireActivity().startActivityForResult(intent, 0)
                })
            }
        }

        val mainBinding =
            DataBindingUtil.setContentView<FragmentListBinding>(requireActivity(), R.layout.fragment_list)
        mainBinding.adapter = adapter
        mainBinding.viewModel = viewModel
       viewModel.loadBookListFromDatabase()
    }

    private fun observeToLiveData(viewModel: BookViewModel, adapter: BookAdapter) {

        viewModel.addBookEvent.observer(this){
            findNavController(this).navigate(R.id.bookFragment)
        }

        viewModel.bookLiveData.observer(this){
            adapter.updateBookList(it)
        }
    }
}