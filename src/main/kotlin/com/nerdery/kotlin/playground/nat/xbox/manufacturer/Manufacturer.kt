package com.nerdery.kotlin.playground.nat.xbox.manufacturer

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Manufacturer(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = -1,
        val name: String
) {
    companion object {
        val ExceptionName = "Manufacturer"
    }
}