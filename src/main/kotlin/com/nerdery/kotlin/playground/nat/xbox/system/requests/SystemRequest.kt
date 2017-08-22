package com.nerdery.kotlin.playground.nat.xbox.system.requests

import com.nerdery.kotlin.playground.nat.xbox.system.System
import javax.validation.constraints.NotNull

/**
 * @property manufacturerId is set to a `Long?` as this maps to the primitive `long` in Java and primitives cannot be null.
 * When deserializing this property is therefore set to 0 which is not ideal for validation, therefore we mark [manufacturerId] as being potentially null in order to correctly validate.
 */
data class SystemRequest(
        @field:NotNull val manufacturerId: Long?,
        val system: SystemDTO
)

data class SystemDTO(
        val id: Long?,
        @field:NotNull val name: String
)

fun SystemRequest.toSystem(): System = System(
        name = this.system.name,
        manufacturerId = this.manufacturerId!!
)

fun System.toSystemRequest(): SystemRequest = SystemRequest(
        manufacturerId = this.manufacturerId,
        system = SystemDTO(
                id = this.id,
                name = this.name
        )
)

fun List<System>.toSystemRequestList(): List<SystemRequest> = this.map { it.toSystemRequest() }