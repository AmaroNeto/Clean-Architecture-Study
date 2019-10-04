package com.amaro.todolist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amaro.todolist.data.local.entities.TodoLocalEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAllTodos(): Flowable<List<TodoLocalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(todoLocalEntity: TodoLocalEntity): Single<Long>
}