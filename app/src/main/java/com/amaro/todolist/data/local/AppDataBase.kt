package com.amaro.todolist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amaro.todolist.data.local.entities.TodoLocalEntity
import com.amaro.todolist.data.local.dao.TodoDao

@Database(entities = arrayOf(TodoLocalEntity::class), version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}