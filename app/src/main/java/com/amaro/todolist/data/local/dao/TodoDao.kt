package com.amaro.todolist.data.local.dao

import androidx.room.*
import com.amaro.todolist.data.local.entities.TodoLocalEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAllTodos(): Flowable<List<TodoLocalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(todoLocalEntity: TodoLocalEntity): Single<Long>

    @Update
    fun updateTodo(todoLocalEntity: TodoLocalEntity): Single<Int>

    @Query("SELECT COUNT(id) FROM todo WHERE done = 0")
    fun countTodos(): Flowable<Int>
}