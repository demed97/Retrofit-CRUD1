package com.brewhog.android.retrofit_crud.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.brewhog.android.retrofit_crud.R
import com.brewhog.android.retrofit_crud.databinding.ActivityMainBinding
import com.brewhog.android.retrofit_crud.model.Book
import com.brewhog.android.retrofit_crud.repository.BookRepository
import com.brewhog.android.retrofit_crud.util.BookAdapter
import com.brewhog.android.retrofit_crud.util.BookHolder
import com.brewhog.android.retrofit_crud.viewmodel.BookViewModel
import com.brewhog.android.retrofit_crud.viewmodel.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = BookAdapter(listOf())
        val factory = ViewModelFactory(application, BookRepository.getRepository(application))
        viewModel = ViewModelProvider(this, factory).get(BookViewModel::class.java)

        checkConnection(viewModel)
        observeToLiveData(viewModel, adapter)


        adapter.callback = object : BookAdapter.AdapterCallback {
            override fun callActivityFromHolder(holder: BookHolder, id: Int) {
                holder.itemView.setOnClickListener(View.OnClickListener {
                    val intent = Intent(baseContext, BookActivity::class.java)
                    intent.putExtra("bookID", id)
                    this@MainActivity.startActivityForResult(intent, 0)
                })
            }
        }

        val mainBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        mainBinding.adapter = adapter
        mainBinding.viewModel = viewModel
    }

    private fun observeToLiveData(viewModel: BookViewModel, adapter: BookAdapter) {

        viewModel.addBookLiveData.observe(this, Observer {
            val intent = Intent(baseContext, BookActivity::class.java)
            this@MainActivity.startActivityForResult(intent, 0)
        })

        viewModel.bookLiveData.observe(this, Observer {
            adapter.updateBookList(it)
        })
    }


    private fun checkConnection(viewModel: BookViewModel) {

        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
            .build()
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                viewModel.loadBookListFromServer()
                Toast.makeText(baseContext, "Network is available", Toast.LENGTH_LONG).show()
            }

            override fun onLost(network: Network) {
                viewModel.loadBookListFromDatabase()
                Toast.makeText(baseContext, "Network was lost", Toast.LENGTH_LONG).show()
            }
        }

        try {
            manager.unregisterNetworkCallback(callback)
        } catch (e: Exception) {
            Log.w(
                "MainActivity",
                "NetworkCallback for Wi-fi was not registered or already unregistered"
            )
        }
        manager.registerNetworkCallback(networkRequest, callback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> viewModel.loadBookListFromServer()
        }
    }
}
