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
package com.gabor.cleanarchitecture.domain

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

abstract class Interactor<in Params, Result> {

    open val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO

    abstract suspend fun run(params: Params): com.gabor.cleanarchitecture.domain.Result<Throwable, Result>

    suspend operator fun invoke(
        params: Params,
        isAsync: Boolean = true
    ): com.gabor.cleanarchitecture.domain.Result<Throwable, Result> = coroutineScope {
        val resultDuration = if (isAsync) {
            logMessage("Starting async")
            val job = async(coroutineDispatcher) { runWithMeasure(params) }
            job.await()
        } else {
            logMessage("Running without dispatcher")
            runWithMeasure(params)
        }

        logMessage("Run invocation finished within: ${resultDuration.duration} milliseconds")

        return@coroutineScope resultDuration.result
    }

    private suspend fun runWithMeasure(params: Params): RunDuration<com.gabor.cleanarchitecture.domain.Result<Throwable, Result>> {
        return measureResultTime { run(params) }
    }

    private fun logMessage(message: String) {
        Log.d(this@Interactor.javaClass.toString(), message)
    }
}

object None

suspend operator fun <T> Interactor<None, T>.invoke(
    isAsync: Boolean = true
): Result<Throwable, T> = invoke(None, isAsync)

suspend fun <Result> measureResultTime(block: suspend () -> Result): RunDuration<Result> {
    val before = System.currentTimeMillis()
    val result = block()
    val after = System.currentTimeMillis()
    return RunDuration(result, after - before)
}

class RunDuration<out Result>(
    val result: Result,
    val duration: Long
)
