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
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.brewhog.android.retrofit_crud.R
import com.brewhog.android.retrofit_crud.database.BookDao
import com.brewhog.android.retrofit_crud.database.BookDatabase
import com.brewhog.android.retrofit_crud.model.Book
import com.brewhog.android.retrofit_crud.repository.BookRepository
import com.brewhog.android.retrofit_crud.util.BookAdapter
import com.brewhog.android.retrofit_crud.util.BookHolder
import com.brewhog.android.retrofit_crud.viewmodel.BookViewModel
import com.brewhog.android.retrofit_crud.viewmodel.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainActivity : DaggerAppCompatActivity() {

//    @Inject
    private lateinit var viewModel: BookViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findNavController(this, R.id.nav_host_frag)
//        val adapter = BookAdapter(listOf())
//        val factory = ViewModelFactory(application, BookRepository.getRepository(application))
        viewModel = ViewModelProvider(this, factory).get(BookViewModel::class.java)
        checkConnection(viewModel)
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
}
