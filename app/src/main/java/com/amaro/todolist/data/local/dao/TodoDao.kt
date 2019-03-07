package com.amaro.todolist.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.amaro.todolist.data.local.entities.TodoLocalEntity
import io.reactivex.Single

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAllTodos() : Single<List<TodoLocalEntity>>
}