package com.nerdery.kotlin.playground.nat.xbox.manufacturer

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class ManufacturerServiceUnitTest {
    lateinit private var manufacturerRepository: ManufacturerRepository
    lateinit private var manufacturerService: ManufacturerService

    private val testManufacturer = Manufacturer(1, "Manufacturer Name")

    @Before
    fun beforeAll() {
        manufacturerRepository = Mockito.mock(ManufacturerRepository::class.java)
        manufacturerService = ManufacturerService(manufacturerRepository)
    }

    @Test
    fun listAllSuccess() {
        given(manufacturerRepository.findAll())
                .willReturn(mutableListOf(testManufacturer))

        val manufacturers = manufacturerService.listAll()

        assertTrue(manufacturers.toList().size == 1)
        assertTrue(manufacturers.first() == testManufacturer)
    }

    @Test
    fun getOneSuccess() {

    }

    @Test
    fun saveSuccess() {

    }

    @Test
    fun updateSuccess() {

    }

    @Test
    fun updateNotFound() {

    }

    @Test
    fun deleteSuccess() {

    }
}