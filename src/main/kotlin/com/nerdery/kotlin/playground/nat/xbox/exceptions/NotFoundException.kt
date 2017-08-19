package com.nerdery.kotlin.playground.nat.xbox.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(id: Long, kClass: String?) : RuntimeException("Could not find [$kClass:$id]")