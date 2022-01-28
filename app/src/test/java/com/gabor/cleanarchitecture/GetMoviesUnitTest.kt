/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gabor.cleanarchitecture

import com.gabor.cleanarchitecture.data.remote.SessionProvider
import com.gabor.cleanarchitecture.domain.Result
import com.gabor.cleanarchitecture.domain.contracts.MoviesRepository
import com.gabor.cleanarchitecture.domain.movies.usecases.GetMoviesUseCase
import com.gabor.cleanarchitecture.presentation.movies.MovieViewItem
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GetMoviesUnitTest {

    private val movieTestObject = MovieViewItem(1, "Spider Man", "Some description", "url")

    private val repository : MoviesRepository = mockk()
    private val getMoviesInteractor = spyk(GetMoviesUseCase(repository))

//    @Test
//    fun `get movies success`() = runBlocking {
//        val expectedResponse = arrayListOf<MovieViewItem>()
//        expectedResponse.add(movieTestObject)
//        coEvery { getMoviesInteractor() } returns Result.success(expectedResponse)
//
//        val actualResponse = getMoviesInteractor()
//        assertEquals(actualResponse.isRight , true)
//
//        val responseMovie = (actualResponse as Result.Right).b.first()
//
//        assertEquals(responseMovie.title , movieTestObject.title)
//        assertEquals(responseMovie.description , movieTestObject.description)
//        assertEquals(responseMovie.posterImageUrl , movieTestObject.imageUrl)
//    }
//
//    @Test
//    fun `get movies failure`() = runBlocking {
//        val expectedResponse = Exception()
//        coEvery { getMoviesInteractor() } returns Result.failure(expectedResponse)
//
//        val actualResponse = getMoviesInteractor()
//        assertEquals(actualResponse.isLeft , true)
//
//        val throwable = (actualResponse as Result.Left).a
//        assertEquals(throwable , expectedResponse)
//    }
}
