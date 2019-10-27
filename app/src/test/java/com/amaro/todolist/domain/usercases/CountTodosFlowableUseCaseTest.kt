package com.amaro.todolist.domain.usercases

import com.amaro.todolist.domain.entities.ErrorEntity
import com.amaro.todolist.domain.entities.Result
import com.amaro.todolist.domain.error.ErrorHandler
import com.amaro.todolist.domain.log.Logger
import com.amaro.todolist.domain.repositories.TodoRepository
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
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
class CountTodosFlowableUseCaseTest {

    private val testSubscriber = TestSubscriber<Result<Int>>()

    @Mock
    private lateinit var todoRepository: TodoRepository
    @Mock
    private lateinit var logger: Logger
    @Mock
    private lateinit var errorHandler: ErrorHandler

    @InjectMocks
    private lateinit var useCase: CountTodosFlowableUseCase

    private val COUNT_RESULT = 5

    @Before
    fun setup() {
        Mockito.`when`(todoRepository.countTodos()).thenReturn(Flowable.just(COUNT_RESULT))
    }

    @Test
    fun `when execute CountTodosFlowableUseCase then call (countTodos) and log`() {
        useCase.execute(Unit).subscribe(testSubscriber)

        Mockito.verify(todoRepository, Mockito.times(1)).countTodos()
        Mockito.verify(logger, Mockito.times(1)).d(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())

        testSubscriber.assertNoErrors()
    }

    @Test
    fun `when execute CountTodosFlowableUseCase then return a ResultSuccess`() {
        useCase.execute(Unit).subscribe(testSubscriber)

        testSubscriber.assertResult(Result.Success(COUNT_RESULT))

        testSubscriber.assertNoErrors()
    }

    @Test
    fun `when countTodos() throw an exception then CountTodosFlowableUseCase return error`() {
        val exception = IOException()
        val result  = Result.Error<Int>(ErrorEntity.NetWork(exception))

        Mockito.`when`(todoRepository.countTodos()).thenReturn(Flowable.error(exception))
        Mockito.`when`(errorHandler.getError(exception)).thenReturn(ErrorEntity.NetWork(exception))

        useCase.execute(Unit)
            .subscribeWith(testSubscriber)

        Mockito.verify(todoRepository, Mockito.times(1)).countTodos()
        Mockito.verify(logger, Mockito.times(1)).d(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())
        Mockito.verify(errorHandler, Mockito.times(1)).getError(exception)

        testSubscriber.assertResult(result)
        testSubscriber.assertNoErrors()
    }
}