package com.brewhog.android.retrofit_crud.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.brewhog.android.retrofit_crud.R
import com.brewhog.android.retrofit_crud.databinding.BookItemBinding
import com.brewhog.android.retrofit_crud.model.Book

class BookAdapter(private var bookList : List<Book>) : RecyclerView.Adapter<BookHolder>() {
    var callback : AdapterCallback? = null

    interface AdapterCallback{
        fun callActivityFromHolder(holder: BookHolder, id : Int)
    }

    fun updateBookList(newBookList: List<Book>){
        val diffUtilCallback = DiffUtilCallback(this.bookList,newBookList)
        val result = DiffUtil.calculateDiff(diffUtilCallback)
        this.bookList = newBookList
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bookItemBinder : BookItemBinding = DataBindingUtil.inflate(inflater, R.layout.book_item,parent,false)
        return BookHolder(bookItemBinder)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.bookItemBinding.book = bookList[position]
        callback?.callActivityFromHolder(holder, bookList[position].id)
    }
}