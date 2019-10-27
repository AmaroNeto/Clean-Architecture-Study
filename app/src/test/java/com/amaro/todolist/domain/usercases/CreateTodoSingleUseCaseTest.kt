package com.amaro.todolist.domain.usercases

import com.amaro.todolist.domain.entities.ErrorEntity
import com.amaro.todolist.domain.entities.Result
import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.error.ErrorHandler
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.domain.repositories.TodoRepository
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(
    MockitoJUnitRunner::class)
class CreateTodoSingleUseCaseTest {

    private val testSubscriber = TestObserver<Result<Long>>()

    @Mock
    private lateinit var todoRepository: TodoRepository
    @Mock
    private lateinit var logger: Logger
    @Mock
    private lateinit var errorHandler: ErrorHandler

    @InjectMocks
    private lateinit var useCase: CreateTodoSingleUseCase

    private val INSERT_RESULT = 5L
    private val todo = TodoDomain("test", false)

    @Before
    fun setup() {
        Mockito.`when`(todoRepository.insertTodo(todo)).thenReturn(Single.just(INSERT_RESULT))
    }

    @Test
    fun `when execute CountTodosFlowableUseCase then call (countTodos) and log`() {
        useCase.execute(todo).subscribe(testSubscriber)

        Mockito.verify(todoRepository, Mockito.times(1)).insertTodo(todo)
        Mockito.verify(logger, Mockito.times(1)).d(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())

        testSubscriber.assertNoErrors()
    }

    @Test
    fun `when execute CreateTodoSingleUseCase then return a ResultSuccess`() {
        useCase.execute(todo).subscribe(testSubscriber)

        testSubscriber.assertResult(Result.Success(INSERT_RESULT))

        testSubscriber.assertNoErrors()
    }

    @Test
    fun `when insertTodo() throw an exception then CreateTodoSingleUseCase return error`() {
        val exception = IOException()
        val result  = Result.Error<Long>(ErrorEntity.NetWork(exception))

        Mockito.`when`(todoRepository.insertTodo(todo)).thenReturn(Single.error(exception))
        Mockito.`when`(errorHandler.getError(exception)).thenReturn(ErrorEntity.NetWork(exception))

        useCase.execute(todo)
            .subscribeWith(testSubscriber)

        Mockito.verify(todoRepository, Mockito.times(1)).insertTodo(todo)
        Mockito.verify(logger, Mockito.times(1)).d(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())
        Mockito.verify(errorHandler, Mockito.times(1)).getError(exception)

        testSubscriber.assertResult(result)
        testSubscriber.assertNoErrors()
    }

}