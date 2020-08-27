package com.brewhog.android.retrofit_crud.util

import androidx.recyclerview.widget.DiffUtil
import com.brewhog.android.retrofit_crud.model.Book

class DiffUtilCallback(var oldList: List<Book>, var newList: List<Book>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size


    override fun getNewListSize(): Int = newList.size


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}