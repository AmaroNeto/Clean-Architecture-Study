package com.amaro.todolist.domain.log

interface Logger {
    fun d(tag : String, message : String )
    fun w(tag : String, message : String )
    fun e(tag : String, message : String )
    fun v(tag : String, message : String )
    fun i(tag : String, message : String )
}