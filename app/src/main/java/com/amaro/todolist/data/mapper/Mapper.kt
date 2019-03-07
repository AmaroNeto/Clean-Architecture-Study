package com.amaro.todolist.data.mapper

interface Mapper<A, B> {
    fun mapFromDomain(domainType: A): B
    fun mapToDomain(dataType: B): A
}