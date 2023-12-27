package name.dashkal.minecraft.hexresearch.util

/**
 * A disjoint or union type that may be either a Left or a Right value.
 *
 * Functions that require a bias do so to the Right.
 * Functions that need to short circuit will do so from the Left.
 */
sealed interface Either<L, R> {
    /**
     * Swaps a Left to a Right and a Right to a left.
     */
    fun swap(): Either<R, L> {
        return when (this) {
            is Left -> right(this.left)
            is Right -> left(this.right)
        }
    }

    /**
     * Map a Right value through the given function, returning a new Either.
     *
     * Left values are left unchanged.
     */
    fun <RR> map(f: (value: R) -> RR): Either<L, RR> {
        return when (this) {
            is Left -> left(this.left)
            is Right -> right(f(this.right))
        }
    }

    /**
     * Map a Right value through the given function, returning the result of that function.
     *
     * Left values are left unchanged.
     */
    fun <RR> flatMap(f: (value: R) -> Either<L, RR>): Either<L, RR> {
        return when (this) {
            is Left -> left(this.left)
            is Right -> f(this.right)
        }
    }

    /**
     * Converts this Either to an Option, preserving a right hand value, but dropping a left hand value.
     */
    fun toOption(): Option<R> {
        return when (this) {
            is Left -> Option.none()
            is Right -> Option.some(this.right)
        }
    }

    companion object {
        /** Create an Either with a value on the Left */
        @JvmStatic fun <L, R> left(left: L): Either<L, R> = Left(left)
        /** Create an Either with a value on the Right */
        @JvmStatic fun <L, R> right(right: R): Either<L, R> = Right(right)

        /**
         * If passed a Left value that's a subtype of Throwable, throw it.
         *
         * If passed a Right value, return it.
         */
        @JvmStatic fun <L : Throwable, R> getOrThrow(either: Either<L, R>): R {
            return when (either) {
                is Left -> throw either.left
                is Right -> either.right
            }
        }
    }
}

/** An Either that has a Left-handed value. */
data class Left<L,R>(val left: L) : Either<L, R>
/** An Either that has a Right-handed value. */
data class Right<L,R>(val right: R) : Either<L, R>
