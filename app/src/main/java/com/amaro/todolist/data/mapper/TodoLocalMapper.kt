package com.amaro.todolist.data.mapper

import com.amaro.todolist.data.local.entities.TodoLocalEntity
import com.amaro.todolist.domain.entities.TodoDomain

class TodoLocalMapper : Mapper<TodoDomain, TodoLocalEntity> {
    override fun mapFromDomain(domainType: TodoDomain): TodoLocalEntity {
        var todoLocalEntity = TodoLocalEntity()
        todoLocalEntity.id = domainType.id
        todoLocalEntity.title = domainType.title
        todoLocalEntity.description = domainType.description
        todoLocalEntity.done = domainType.done
        return todoLocalEntity
    }

    override fun mapToDomain(dataType: TodoLocalEntity): TodoDomain {
        var todoDomain = TodoDomain(dataType.title, dataType.done)
        todoDomain.id = dataType.id
        todoDomain.description = dataType.description
        return todoDomain
    }
}