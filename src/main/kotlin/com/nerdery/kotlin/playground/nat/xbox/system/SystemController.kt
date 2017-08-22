package com.nerdery.kotlin.playground.nat.xbox.system

import com.nerdery.kotlin.playground.nat.xbox.constants.API_BASE
import com.nerdery.kotlin.playground.nat.xbox.system.requests.SystemRequest
import com.nerdery.kotlin.playground.nat.xbox.system.requests.toSystem
import com.nerdery.kotlin.playground.nat.xbox.system.requests.toSystemRequest
import com.nerdery.kotlin.playground.nat.xbox.system.requests.toSystemRequestList
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.inject.Inject
import javax.validation.Valid

@RestController
@RequestMapping("$API_BASE/systems")
class SystemController @Inject constructor(val systemService: SystemService) {

    @GetMapping
    fun listAll(): List<SystemRequest> =
            systemService
                    .listAll()
                    .toSystemRequestList()

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): SystemRequest =
            systemService
                    .getOne(id)
                    .toSystemRequest()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody @Valid request: SystemRequest): SystemRequest =
            systemService
                    .save(request.toSystem())
                    .toSystemRequest()

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody @Valid request: SystemRequest): SystemRequest =
            systemService
                    .update(id, request.toSystem())
                    .toSystemRequest()

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = systemService.delete(id)
}