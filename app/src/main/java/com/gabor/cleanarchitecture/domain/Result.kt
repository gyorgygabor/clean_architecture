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

/**
 * Represents a value of one of two possible types.
 * Instances of [Result] are either an instance of [Failure] or [Success].
 * [Failure] is used for "failure" and [Success] is used for "success".
 *
 * @see Failure
 * @see Success
 */
sealed class Result<out L, out R> {
    /** * Represents the left side of [Result] class which by convention is a "Failure". */
    data class Failure<out L>(val error: L) : Result<L, Nothing>()

    /** * Represents the right side of [Result] class which by convention is a "Success". */
    data class Success<out R>(val data: R) : Result<Nothing, R>()

    companion object {
        /**
         * Returns an instance that encapsulates the given [value] as successful value.
         */
        fun <T> success(value: T): Result<Throwable, T> =
            Result.Success(value)

        /**
         * Returns an instance that encapsulates the given [Throwable] [exception] as failure.
         */
        fun <T> failure(exception: Throwable): Result<Throwable, T> =
            Result.Failure(exception)
    }
}
