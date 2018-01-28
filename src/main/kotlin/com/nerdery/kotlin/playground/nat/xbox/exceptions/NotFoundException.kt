package com.nerdery.kotlin.playground.nat.xbox.exceptions

import java.lang.RuntimeException

class NotFoundException constructor(
        val id: Long,
        val kClass: String = "Anonymous"
) : RuntimeException("Could not find [$kClass:$id]")