package com.uber.tagir.advent2018.day03

import com.uber.tagir.advent2018.utils.resourceAsString

fun main(args: Array<String>) {
    with(Day03()) {
        overlaps()
        single()
    }
}

data class Claim(val id: Int, val left: Int, val top: Int, val width: Int, val height: Int)

fun parseClaim(s: String): Claim {
    // using toRegex doesn't support extracting groups by name in JDK 9+
    val p = """#(?<id>\d+) @ (?<left>\d+),(?<top>\d+): (?<width>\d+)x(?<height>\d+)""".toPattern()
    val m = p.matcher(s)
    m.find()
    fun extract(name: String) = m.group(name).toInt()

    return Claim(
        id = extract("id"),
        left = extract("left"),
        top = extract("top"),
        width = extract("width"),
        height = extract("height")
    )
}

class Day03 {
    private val input = resourceAsString("input.txt").lines()
    private val claims = input.map(::parseClaim)
    private val field = buildField(claims)

    private fun buildField(claims: Collection<Claim>): List<Array<MutableSet<Claim>>> {
        val width: Int = claims.map { it.left + it.width }.max()!!
        val height: Int = claims.map { it.top + it.height }.max()!!

        val field = (0 until height).map { Array(width) { mutableSetOf<Claim>() } }

        for (c in claims) {
            for (row in c.top until (c.top + c.height)) {
                for (col in c.left until (c.left + c.width)) {
                    field[row][col].add(c)
                }
            }
        }

        return field
    }

    fun overlaps() {
        val nOverlaps = field.map { row ->
            row.count { it.size > 1 }
        }.sum()

        println(nOverlaps)
    }

    fun single() {
        val remainingClaims = claims.toMutableSet()

        for (row in field) {
            for (cell in row) {
                if (cell.size > 1) {
                    remainingClaims -= cell
                }
            }
        }

        println(remainingClaims)
    }

}
