package com.brewhog.android.retrofit_crud.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter

@BindingAdapter("android:text")
fun setText(view : TextView, value : Int){
    view.text = value.toString()
}

@InverseBindingAdapter(attribute = "android:text",event = "android:textAttrChanged")
fun getText(view : TextView): Int{
    return if (view.text.toString() != ""){
        Integer.parseInt(view.text.toString())
    }else{
        0
    }
}

