package com.nerdery.kotlin.playground.nat.xbox.system

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class System(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = -1,
        val name: String,
        val manufacturerId: Long
) {
    companion object {
        const val ExceptionName = "System"
    }
}