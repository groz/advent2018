package com.uber.tagir.advent2018.day04

import com.uber.tagir.advent2018.utils.resourceAsString

fun main(args: Array<String>) {
    with(Day04()) {
        strategy1()
        strategy2()
    }
}

class Day04 {
    private val input = resourceAsString("input.txt").lines().sorted()
    private val schedule = parseSchedule(input)

    private fun parseSchedule(lines: Collection<String>): Map<Int, Map<Int, Int>> {
        // output type: {guardId -> {minute -> count}}
        val entry = """\[(\d{4}-\d{2}-\d{2}) \d{2}:(\d{2})] (.*)""".toRegex()
        val beginShift = """Guard #(\d+) begins shift""".toRegex()
        val fallsAsleep = "falls asleep"
        val wakesUp = "wakes up"

        var currentGuardId: Int? = null
        val schedule = mutableMapOf<Int, MutableMap<Int, Int>>()
        var asleepFrom: Int? = null

        // imperative nightmare :(
        for (line in lines) {
            val (date, time, event) = entry.matchEntire(line)!!.destructured

            val nextGuardId: Int? = beginShift.matchEntire(event)?.groups?.get(1)?.value?.toInt()

            if (currentGuardId == null) {
                currentGuardId = nextGuardId
                asleepFrom = null
                continue
            }

            if (event == fallsAsleep) {
                asleepFrom = time.toInt()
                continue
            }

            if (asleepFrom != null) {
                val asleepUntil: Int = if (event == wakesUp) time.toInt() else 59

                val guardsSchedule = schedule.getOrDefault(currentGuardId, mutableMapOf())

                for (minute in asleepFrom until asleepUntil) {
                    guardsSchedule[minute] = guardsSchedule.getOrDefault(minute, 0) + 1
                }

                schedule[currentGuardId] = guardsSchedule

                if (event == wakesUp) {
                    asleepFrom = null
                }
            }


            if (nextGuardId != null) {
                currentGuardId = nextGuardId
                asleepFrom = null
            }
        }

        return schedule
    }

    private fun choiceHash(guardId: Int, sleepMap: Map<Int, Int>): Int? {
        return sleepMap.maxBy { (minute, count) -> count }?.let { (minute, count) -> guardId * minute }
    }

    fun strategy1() {
        val maxByTotalSleepTime = schedule.maxBy { (_, sleepMap) -> sleepMap.values.sum() }

        val result = maxByTotalSleepTime?.let { (guardId, sleepMap) -> choiceHash(guardId, sleepMap) }

        println(result)
    }

    fun strategy2() {
        val maxByMinute = schedule.maxBy { (_, sleepMap) -> sleepMap.values.max() ?: 0 }

        val result = maxByMinute?.let { (guardId, sleepMap) -> choiceHash(guardId, sleepMap) }

        println(result)
    }
}
