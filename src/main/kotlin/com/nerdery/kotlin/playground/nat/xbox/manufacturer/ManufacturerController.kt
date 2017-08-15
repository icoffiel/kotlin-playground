package com.nerdery.kotlin.playground.nat.xbox.manufacturer

import com.nerdery.kotlin.playground.nat.xbox.constants.UrlConstants
import org.springframework.web.bind.annotation.*
import javax.inject.Inject

@RestController
@RequestMapping("${UrlConstants.API_BASE}/manufacturer")
class ManufacturerController @Inject constructor(val manufacturerService: ManufacturerService) {

    @GetMapping
    fun getAll() = manufacturerService.listAll()

    @PostMapping
    fun save(@RequestBody manufacturer: Manufacturer) = manufacturerService.save(manufacturer)
}