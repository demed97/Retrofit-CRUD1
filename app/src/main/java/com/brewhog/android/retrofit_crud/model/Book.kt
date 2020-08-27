package com.brewhog.android.retrofit_crud.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    var title : String,
    var author : String,
    var description : String,
    var published : Int,
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
) {
}