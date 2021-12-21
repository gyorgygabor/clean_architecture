package com.gabor.assessment.domain


/**
 * Represents a value of one of two possible types.
 * Instances of [Result] are either an instance of [Left] or [Right].
 * [Left] is used for "failure" and [Right] is used for "success".
 *
 * @see Left
 * @see Right
 */
sealed class Result<out L, out R> {
    /** * Represents the left side of [Result] class which by convention is a "Failure". */
    data class Left<out L>(val a: L) : Result<L, Nothing>()

    /** * Represents the right side of [Result] class which by convention is a "Success". */
    data class Right<out R>(val b: R) : Result<Nothing, R>()

    /**
     * Returns true if this is a Right, false otherwise.
     * @see Right
     */
    val isRight get() = this is Right<R>

    /**
     * Returns true if this is a Left, false otherwise.
     * @see Left
     */
    val isLeft get() = this is Left<L>

    /**
     * Creates a Left type.
     * @see Left
     */
    fun <L> left(a: L) = Result.Left(a)


    /**
     * Creates a Left type.
     * @see Right
     */
    fun <R> right(b: R) = Result.Right(b)

    /**
     * Applies fnL if this is a Left or fnR if this is a Right.
     * @see Left
     * @see Right
     */
    fun handle(onError: (L) -> Any, onSuccess: (R) -> Any): Any =
        when (this) {
            is Left -> onError(a)
            is Right -> onSuccess(b)
        }


    companion object {
        /**
         * Returns an instance that encapsulates the given [value] as successful value.
         */
        fun <T> success(value: T): Result<Throwable, T> =
            Result.Right(value)

        /**
         * Returns an instance that encapsulates the given [Throwable] [exception] as failure.
         */
        fun <T> failure(exception: Throwable): Result<Throwable, T> =
            Result.Left(exception)
    }
}

