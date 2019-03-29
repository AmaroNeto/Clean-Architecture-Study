package com.amaro.todolist.presentation.mapper

import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.presentation.model.TodoModel

class TodoModelMapper : Mapper<TodoDomain, TodoModel> {
    override fun mapFromDomain(domainType: TodoDomain): TodoModel {
        var todoModel = TodoModel()
        todoModel.id = domainType.id
        todoModel.title = domainType.title
        todoModel.description = domainType.description
        todoModel.done = domainType.done
        return todoModel
    }

    override fun mapToDomain(dataType: TodoModel): TodoDomain {
        var todoDomain = TodoDomain(dataType.title, dataType.done)
        todoDomain.id = dataType.id
        todoDomain.description = dataType.description
        return todoDomain
    }
}