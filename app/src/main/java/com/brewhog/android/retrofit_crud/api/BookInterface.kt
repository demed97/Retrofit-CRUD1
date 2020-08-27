package com.brewhog.android.retrofit_crud.api

import com.brewhog.android.retrofit_crud.model.Book
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface BookInterface {
    @GET("api/books/")
    suspend fun getAllBooks () : List<Book>

    //or add @Path and {id}
    @GET("api/books/{id}")
    suspend fun getBookByID(@Path("id")id : Int) : Book?

    @POST("api/books/create")
    suspend fun addNewBook(@Body book: Book) : Book

    @DELETE("api/books/{id}")
    suspend fun deleteBook (@Path("id") id : Int) : ResponseBody

    @PUT("api/books/{id}")
    suspend fun updateBook(@Path("id") id : Int, @Body book : Book) : Book

}