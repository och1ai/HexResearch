package name.dashkal.minecraft.hexresearch.util

sealed interface Option<A> {
    fun <B> map(f: (A) -> B): Option<B> {
        return when (this) {
            is None -> none()
            is Some -> some(f(this.value))
        }
    }

    companion object {
        @JvmStatic fun <A> none() : Option<A> { return None(Unit) }
        @JvmStatic fun <A> some(value: A): Option<A> { return Some(value) }
    }
}

data class None<A>(val nothing: Unit) : Option<A>
data class Some<A>(val value: A): Option<A>
