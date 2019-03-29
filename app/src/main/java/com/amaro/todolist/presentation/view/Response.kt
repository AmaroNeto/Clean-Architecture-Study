package com.amaro.todolist.presentation.view

import androidx.annotation.NonNull

class Response {

    private constructor(status: Status, data : Any = Any(), error : Throwable = Throwable()) {}

    companion object {
        fun loading() : Response {
            return Response(status = Status.LOADING);
        }

        fun success(@NonNull data : Any) : Response {
            return Response(Status.SUCCESS, data)
        }

        fun error(@NonNull error : Throwable) : Response {
            return Response(Status.SUCCESS, error = error)
        }
    }
}