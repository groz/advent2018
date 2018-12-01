package com.uber.tagir.advent2018.utils

inline fun <reified T> T.resourceAsString(name: String): String {
    return T::class.java.getResource(name).readText()
}