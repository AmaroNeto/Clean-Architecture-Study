package com.amaro.todolist.presentation.mapper

interface Mapper<A, B> {
    fun mapFromDomain(domainType: A): B
    fun mapToDomain(dataType: B): A
}