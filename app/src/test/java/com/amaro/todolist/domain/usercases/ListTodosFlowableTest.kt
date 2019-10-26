package com.amaro.todolist.domain.usercases

import com.amaro.todolist.domain.entities.TodoDomain
import com.amaro.todolist.domain.error.ErrorHandler
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.domain.repositories.TodoRepository
import com.amaro.todolist.domain.entities.Result
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(
    MockitoJUnitRunner::class)
class ListTodosFlowableTest {

    private val testSubscriber = TestSubscriber<Result<List<TodoDomain>>>()

    @Mock
    private lateinit var todoRepository: TodoRepository
    @Mock
    private lateinit var logger: Logger
    @Mock
    private lateinit var errorHandler: ErrorHandler

    @InjectMocks
    private lateinit var useCase: ListTodosFlowableUseCase

    @Before
    fun setup() {
        `when`(todoRepository.getAllTodos()).thenReturn(Flowable.just(getAllTodos()))
    }

    @Test
    fun `when execute ListTodosFlowableUseCase then call getAllTodos() and log`() {
        useCase.execute(Unit).subscribe(testSubscriber)

        verify(todoRepository, times(1)).getAllTodos()
        verify(logger, times(1)).d(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())

        testSubscriber.assertNoErrors()
    }

    @Test
    fun `when getAllTodos() throw an exception then ListTodosFlowableUseCase return error`() {
        val exception = IOException()
        `when`(todoRepository.getAllTodos()).thenThrow(exception)

        useCase.execute(Unit)
            .subscribeWith(testSubscriber)

        verify(todoRepository, times(1)).getAllTodos()
        verify(logger, times(1)).d(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())
        verify(errorHandler, times(1)).getError(exception)

        testSubscriber.assertError(exception)
    }

    private fun getAllTodos(): List<TodoDomain> {
        val result = mutableListOf<TodoDomain>()
        for (i in 1..10) {
            result.add(TodoDomain("Title $i", false))
        }
        return result.toList()
    }
}