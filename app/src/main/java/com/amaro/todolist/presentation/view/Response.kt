package com.amaro.todolist.presentation.view

import androidx.annotation.NonNull

class Response(val status: Status, val data : Any? = Any(), val error : Throwable = Throwable()) {

    companion object {
        fun loading() : Response {
            return Response(status = Status.LOADING);
        }

        fun success(data : Any?) : Response {
            return Response(Status.SUCCESS, data)
        }

        fun error(@NonNull error : Throwable) : Response {
            return Response(Status.ERROR, error = error)
        }
    }
}