package com.brewhog.android.retrofit_crud.util

import android.view.View
import com.brewhog.android.retrofit_crud.model.Book

interface OnBookClickListener {
//    fun onItemClick(book: Book, position: Int)
    fun onItemClick(view: View)
}