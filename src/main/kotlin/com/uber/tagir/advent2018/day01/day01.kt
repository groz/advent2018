package com.uber.tagir.advent2018.day01

import com.uber.tagir.advent2018.utils.resourceAsString

fun main(args: Array<String>) {
    Day01().part1()
    Day01().part2()
}

class Day01 {
    fun part1() {
        println(
            resourceAsString("input.txt")
                .lines()
                .map { it.toInt() }
                .sum()
        )
    }

    fun part2() {
        val changes = resourceAsString("input.txt").lines().map { it.toInt() }
        val seen = mutableSetOf(0)

        var idx = 0
        var frequency = 0
        do {
            frequency += changes[idx]
            idx = (idx + 1) % changes.size
        } while (seen.add(frequency))

        println(frequency)
    }
}
