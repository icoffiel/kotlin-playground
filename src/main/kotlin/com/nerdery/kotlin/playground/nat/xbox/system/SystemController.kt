package com.nerdery.kotlin.playground.nat.xbox.system

import com.nerdery.kotlin.playground.nat.xbox.constants.API_BASE
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.inject.Inject

@RestController
@RequestMapping("$API_BASE/systems")
class SystemController @Inject constructor(val systemService: SystemService) {

    @GetMapping
    fun listAll(): MutableIterable<System> = systemService.listAll()

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): System = systemService.getOne(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody system: System): System = systemService.save(system)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody system: System): System = systemService.update(id, system)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = systemService.delete(id)
}