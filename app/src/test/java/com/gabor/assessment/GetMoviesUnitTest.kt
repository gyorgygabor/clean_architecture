package com.gabor.assessment

import com.gabor.assessment.data.remote.SessionProvider
import com.gabor.assessment.domain.Result
import com.gabor.assessment.domain.contracts.MoviesRepository
import com.gabor.assessment.domain.invoke
import com.gabor.assessment.domain.movies.usecases.GetMoviesUseCase
import com.gabor.assessment.presentation.movies.MovieViewItem
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GetMoviesUnitTest {

    private val movieTestObject = MovieViewItem("Spider Man", "Some description", "url")

    private val repository : MoviesRepository = mockk()
    private val sessionProvider : SessionProvider = mockk()
    private val getMoviesInteractor = spyk(GetMoviesUseCase(repository, sessionProvider))

    @Test
    fun `get movies success`() = runBlocking {
        val expectedResponse = arrayListOf<MovieViewItem>()
        expectedResponse.add(movieTestObject)
        coEvery { getMoviesInteractor() } returns Result.success(expectedResponse)

        val actualResponse = getMoviesInteractor()
        assertEquals(actualResponse.isRight , true)

        val responseMovie = (actualResponse as Result.Right).b.first()

        assertEquals(responseMovie.title , movieTestObject.title)
        assertEquals(responseMovie.description , movieTestObject.description)
        assertEquals(responseMovie.imageUrl , movieTestObject.imageUrl)
    }

    @Test
    fun `get movies failure`() = runBlocking {
        val expectedResponse = Exception()
        coEvery { getMoviesInteractor() } returns Result.failure(expectedResponse)

        val actualResponse = getMoviesInteractor()
        assertEquals(actualResponse.isLeft , true)

        val throwable = (actualResponse as Result.Left).a
        assertEquals(throwable , expectedResponse)
    }
}