package com.amaro.todolist.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
class TodoLocalEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @ColumnInfo(name = "title")
    var title: String = ""
    @ColumnInfo(name = "done")
    var done: Boolean = false
    @ColumnInfo(name = "description")
    var description = ""
}