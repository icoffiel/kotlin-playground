package com.nerdery.kotlin.playground.nat.xbox.exceptions

import com.nerdery.kotlin.playground.nat.xbox.manufacturer.Manufacturer
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException
import kotlin.reflect.KClass

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(id: Long, kClass: KClass<Manufacturer>) : RuntimeException("Could not find [${kClass.simpleName}:$id]")