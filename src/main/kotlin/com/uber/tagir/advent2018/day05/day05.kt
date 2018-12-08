package com.uber.tagir.advent2018.day05

import com.uber.tagir.advent2018.utils.resourceAsString

fun main(args: Array<String>) {
    with(Day05()) {
        part1()
        part2()
    }
}

class Day05 {
    private val input: String = resourceAsString("input.txt")

    fun part1() {
        println(trigger(input).length)
    }

    fun part2() {
        val chars = input.toLowerCase().toSet()
        val polymers = chars.map { c -> "[$c]".toRegex(RegexOption.IGNORE_CASE) }
        val inputs = polymers.map { input.replace(it, "") }
        val result = inputs.map { trigger(it).length }.min()
        println(result)
    }

    private fun react(a: Char, b: Char): Boolean {
        return a != b && a.toUpperCase() == b.toUpperCase()
    }

    private fun trigger(input: String): String {
        val result = StringBuilder()

        for (i in input.indices) {
            if (!result.isEmpty() && react(result.last(), input[i])) {
                result.setLength(result.length - 1)
                continue
            }

            result.append(input[i])
        }

        return result.toString()
    }
}
