package com.nerdery.kotlin.playground.nat.xbox.system

import com.nerdery.kotlin.playground.nat.xbox.exceptions.NotFoundException
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class SystemService @Inject constructor(val systemRepository: SystemRepository) {

    fun listAll(): MutableIterable<System> = systemRepository.findAll()

    fun getOne(id: Long): System = systemRepository.findOne(id) ?: throw NotFoundException(id, System::class.simpleName)

    fun save(system: System): System = systemRepository.save(system)

    fun update(id: Long, system: System): System = when {
        systemRepository.findOne(id) != null -> systemRepository.save(system.copy(id = id))
        else -> throw NotFoundException(id, System::class.simpleName)
    }

    fun delete(id: Long) = when {
        systemRepository.findOne(id) != null -> systemRepository.delete(id)
        else -> throw NotFoundException(id, System::class.simpleName)
    }
}