package com.nerdery.kotlin.playground.nat.xbox.manufacturer

import com.nerdery.kotlin.playground.nat.xbox.exceptions.NotFoundException
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class ManufacturerService @Inject constructor(val manufacturerRepository: ManufacturerRepository) {

    fun listAll(): MutableIterable<Manufacturer> = manufacturerRepository.findAll()

    fun getOne(id: Long): Manufacturer = manufacturerRepository.findOne(id) ?: throw NotFoundException(id, Manufacturer.ExceptionName)

    fun save(manufacturer: Manufacturer): Manufacturer = manufacturerRepository.save(manufacturer)

    fun update(id: Long, manufacturer: Manufacturer): Manufacturer {
        if (manufacturerRepository.findOne(id) == null) {
            throw NotFoundException(id, Manufacturer.ExceptionName)
        }

        return manufacturerRepository.save(manufacturer.copy(id = id))
    }

    fun delete(id: Long) = manufacturerRepository.delete(id)
}