package com.uber.tagir.advent2018.day02

import com.uber.tagir.advent2018.utils.resourceAsString

fun main(args: Array<String>) {
    Day02().checksum()
    Day02().findFabric()
}

data class Repetitions(val twice: Boolean, val thrice: Boolean)

class Day02 {
    private val lines = resourceAsString("input.txt").lines()

    fun checksum() {
        val repetitions = lines.map(this::calculateRepetitions)
        val result = repetitions.count { it.twice } * repetitions.count { it.thrice }
        println(result)
    }

    private fun calculateRepetitions(line: String): Repetitions {
        val m = mutableMapOf<Char, Int>()

        for (c in line) {
            m[c] = m.getOrDefault(c, 0) + 1
        }
        // line.associateWithTo(m) { m.getOrDefault(it, 0) + 1 }

        return Repetitions(m.values.contains(2), m.values.contains(3))
    }

    fun findFabric() {
        // O(N^2 * k) feels bad
        for (i in 0 until lines.size - 1) {
            for (j in (i + 1) until lines.size) {
                val pos = differAt(lines[i], lines[j])

                if (pos != null) { // could also use ?.let { ... }
                    println(lines[i].removeRange(pos..pos))
                    return
                }
            }
        }
    }

    private fun differAt(a: String, b: String): Int? {
        if (a.length != b.length) {
            return null
        }

        var count = 0
        var pos: Int? = null

        for (idx in 0 until a.length) {
            if (a[idx] != b[idx]) {
                count++
                pos = idx
            }

            if (count > 1) {
                return null
            }
        }

        return if (count == 1) pos else null
    }
}
