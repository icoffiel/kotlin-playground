package com.nerdery.kotlin.playground.nat.xbox.manufacturer

import com.nerdery.kotlin.playground.nat.xbox.exceptions.NotFoundException
import org.hamcrest.Matchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class ManufacturerServiceUnitTest {
    private lateinit var manufacturerRepository: ManufacturerRepository
    private lateinit var manufacturerService: ManufacturerService

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
        given(manufacturerRepository.findOne(1))
                .willReturn(testManufacturer)

        val manufacturer = manufacturerService.getOne(1)

        assertEquals(manufacturer, testManufacturer)
    }

    @Test
    fun saveSuccess() {
        val unsavedManufacturer = Manufacturer(name = "Manufacturer Name")
        given(manufacturerRepository.save(unsavedManufacturer))
                .willReturn(testManufacturer)

        val manufacturer = manufacturerService.save(unsavedManufacturer)

        assertNotNull(manufacturer.id)
        assertEquals(manufacturer.name, testManufacturer.name)
    }

    @Test
    fun updateSuccess() {
        given(manufacturerRepository.findOne(1))
                .willReturn(testManufacturer)
        given(manufacturerRepository.save(testManufacturer))
                .willReturn(testManufacturer)

        val manufacturer = manufacturerService.update(1, testManufacturer)

        assertEquals(manufacturer, testManufacturer)
    }

    @Test
    fun updateNotFoundException() {
        given(manufacturerRepository.findOne(1))
                .willReturn(null)

        try {
            manufacturerService.update(1, testManufacturer)
            fail("Expected NotFoundException")
        } catch (ex: NotFoundException) {
            assertThat(ex.message, `is`("Could not find [${Manufacturer::class.simpleName}:1]"))
        }
    }

    @Test
    fun deleteSuccess() {
        manufacturerService.delete(1)

        verify(manufacturerRepository, times(1)).delete(1)
    }
}