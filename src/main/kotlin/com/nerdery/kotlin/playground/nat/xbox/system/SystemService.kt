package com.nerdery.kotlin.playground.nat.xbox.system

import com.nerdery.kotlin.playground.nat.xbox.exceptions.NotFoundException
import com.nerdery.kotlin.playground.nat.xbox.manufacturer.Manufacturer
import com.nerdery.kotlin.playground.nat.xbox.manufacturer.ManufacturerRepository
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class SystemService @Inject constructor(
        val systemRepository: SystemRepository,
        val manufacturerRepository: ManufacturerRepository
) {

    fun listAll(): List<System> =
            systemRepository
                    .findAll()
                    .toList()

    fun getOne(id: Long): System = systemRepository.findOne(id) ?: throw NotFoundException(id, System.ExceptionName)

    fun save(system: System): System = when {
        manufacturerRepository.findOne(system.manufacturerId) != null -> systemRepository.save(system)
        else -> throw NotFoundException(system.manufacturerId, Manufacturer.ExceptionName)
    }

    fun update(id: Long, system: System): System = when {
        systemRepository.findOne(id) != null -> systemRepository.save(system.copy(id = id))
        else -> throw NotFoundException(id, System.ExceptionName)
    }

    fun delete(id: Long) = when {
        systemRepository.findOne(id) != null -> systemRepository.delete(id)
        else -> throw NotFoundException(id, System.ExceptionName)
    }
}