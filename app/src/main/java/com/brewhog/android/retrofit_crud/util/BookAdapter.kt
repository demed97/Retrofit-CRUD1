package com.brewhog.android.retrofit_crud.util

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.brewhog.android.retrofit_crud.R
import com.brewhog.android.retrofit_crud.databinding.BookItemBinding
import com.brewhog.android.retrofit_crud.model.Book
import com.brewhog.android.retrofit_crud.view.MainActivity
import com.brewhog.android.retrofit_crud.view.TextActivity
import javax.inject.Inject

class BookAdapter @Inject constructor(val context: Context) : RecyclerView.Adapter<BookHolder>() {
    var callback: AdapterCallback? = null
    private var bookList: List<Book> = arrayListOf()

    interface AdapterCallback {
        fun callActivityFromHolder(holder: BookHolder, id: Int)
    }

    fun updateBookList(newBookList: List<Book>) {
        val diffUtilCallback = DiffUtilCallback(this.bookList, newBookList)
        val result = DiffUtil.calculateDiff(diffUtilCallback)
        this.bookList = newBookList
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bookItemBinder: BookItemBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.book_item,
            parent,
            false
        )
        return BookHolder(bookItemBinder)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.bookItemBinding.book = bookList[position]
        callback?.callActivityFromHolder(holder, bookList[position].id)

//        holder.bookItemBinding.click = object : OnBookClickListener {
//            override fun onItemClick(view: View) {
//                val bundle = Bundle()
//                bundle.putInt("bookID", bookList[position].id)
//                NavHostFragment.findNavController().navigate(R.id.bookFragment, bundle)
////                val intent = Intent("android.intent.action.SHOW_BOOK")
////                intent.type = "text/plain"
////                val book = holder.bookItemBinding.book as Book
////                val value = book.author
//////                intent.putExtra("str", book)
////                intent.putExtra("str", value)
////                context.startActivity(intent)
//            }
//        }
    }
}