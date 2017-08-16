package com.nerdery.kotlin.playground.nat.xbox.manufacturer

import com.nerdery.kotlin.playground.nat.xbox.constants.API_BASE
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.inject.Inject

@RestController
@RequestMapping("$API_BASE/manufacturer")
class ManufacturerController @Inject constructor(val manufacturerService: ManufacturerService) {

    @GetMapping
    fun getAll(): MutableIterable<Manufacturer> = manufacturerService.listAll()

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): Manufacturer = manufacturerService.getOne(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody manufacturer: Manufacturer): Manufacturer = manufacturerService.save(manufacturer)

    @PutMapping("/{id}")
    fun update(@PathVariable id:Long, @RequestBody manufacturer: Manufacturer): Manufacturer = manufacturerService.update(id, manufacturer)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = manufacturerService.delete(id)
}