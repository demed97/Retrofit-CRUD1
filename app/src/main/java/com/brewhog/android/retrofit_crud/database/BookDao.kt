package com.brewhog.android.retrofit_crud.database

import androidx.room.*
import com.brewhog.android.retrofit_crud.model.Book
import retrofit2.http.Body
import retrofit2.http.Path

@Dao
interface BookDao {

    @Query("SELECT * FROM book")
    suspend fun getAllBooks () : List<Book>

    @Query("SELECT * FROM book WHERE id = :id")
    suspend fun getBookByID(id : Int) : Book

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewBook(book: Book)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllBook(listBook: List<Book>)

    @Update
    suspend fun updateBook(book : Book)

    @Delete
    suspend fun deleteBook (book: Book)

    @Query("DELETE FROM book")
    suspend fun deleteAllBook()


}