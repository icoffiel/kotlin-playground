package com.nerdery.kotlin.playground.nat.xbox.manufacturer

import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class ManufacturerService @Inject constructor(val manufacturerRepository: ManufacturerRepository) {

    fun listAll(): MutableIterable<Manufacturer> = manufacturerRepository.findAll()

    fun  save(manufacturer: Manufacturer): Manufacturer = manufacturerRepository.save(manufacturer)
}