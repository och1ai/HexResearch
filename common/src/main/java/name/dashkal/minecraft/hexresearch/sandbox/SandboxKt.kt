package name.dashkal.minecraft.hexresearch.sandbox

object SandboxKt {
    private fun <K> randomSample(n: Int, draw: (() -> K)): Map<K, Int> {
        val results: HashMap<K, Int> = HashMap()

        repeat(n) {
            val k = draw()
            when (val v = results[k]) {
                null -> results[k] = 1
                else -> results[k] = v + 1
            }
        }

        return results
    }

    private fun <K> printResults(results: Map<K, Int>) {
        val rs : MutableList<Map.Entry<K, Int>> = ArrayList(results.entries)
        rs.sortBy { 0 - it.value }
        for (r in rs) {
            println(r.key.toString() + ": " + r.value)
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
    }
}
