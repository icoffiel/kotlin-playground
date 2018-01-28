package com.nerdery.kotlin.playground.nat.xbox.manufacturer

import com.nerdery.kotlin.playground.KotlinPlaygroundApplication
import com.nerdery.kotlin.playground.nat.xbox.constants.API_BASE
import com.nerdery.kotlin.playground.nat.xbox.exceptions.handlers.RestExceptionHandler
import com.nerdery.kotlin.playground.util.TestUtil
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder
import javax.inject.Inject

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(KotlinPlaygroundApplication::class), webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ManufacturerIntTest {

    private lateinit var mockMvc: MockMvc

    @Inject
    private lateinit var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Inject
    private lateinit var manufacturerController: ManufacturerController

    @Inject
    private lateinit var manufacturerRepository: ManufacturerRepository

    private val manufacturerUrl = "$API_BASE/manufacturers"
    private val unknownId = 999
    private lateinit var testManufacturer: Manufacturer

    @Before
    fun beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(manufacturerController)
                .setMessageConverters(jacksonMessageConverter)
                .setControllerAdvice(RestExceptionHandler())
                .alwaysDo<StandaloneMockMvcBuilder>(MockMvcResultHandlers.print())
                .build()
        testManufacturer = manufacturerRepository.save(Manufacturer(name = "Test Manufacturer"))
    }

    @After
    fun afterEach() {
        manufacturerRepository.deleteAll()
    }

    @Test
    fun listAllSuccess() {
        mockMvc.perform(
                get(manufacturerUrl)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.length()", `is`(1)))
    }

    @Test
    fun listOneSuccess() {
        mockMvc.perform(
                get("$manufacturerUrl/${testManufacturer.id}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id", `is`(testManufacturer.id.toInt())))
                .andExpect(jsonPath("$.name", `is`(testManufacturer.name)))
    }

    @Test
    fun listOneNotFound() {
        mockMvc.perform(
                get("$manufacturerUrl/$unknownId")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound)
    }

    @Test
    fun saveSuccess() {
        val newManufacturer = Manufacturer(name = "New Manufacturer")

        val countBefore = manufacturerRepository.findAll().toList().size
        mockMvc.perform(
                post(manufacturerUrl)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJson(newManufacturer)))
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.name", `is`(newManufacturer.name)))
        val countAfter = manufacturerRepository.findAll().toList().size


        assertEquals(countBefore + 1, countAfter)
    }

    @Test
    fun updateSuccess() {
        val updatedManufacturer = Manufacturer(name = "Updated Manufacturer")

        mockMvc.perform(
                put("$manufacturerUrl/${testManufacturer.id}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJson(updatedManufacturer)))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id", `is`(testManufacturer.id.toInt())))
                .andExpect(jsonPath("$.name", `is`(updatedManufacturer.name)))
    }

    @Test
    fun updateNotFound() {
        val updatedManufacturer = Manufacturer(name = "Updated Manufacturer")

        mockMvc.perform(
                put("$manufacturerUrl/$unknownId")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJson(updatedManufacturer)))
                .andExpect(status().isNotFound)
    }

    @Test
    fun deleteSuccess() {
        val countBefore = manufacturerRepository.findAll().toList().size
        mockMvc.perform(
                delete("$manufacturerUrl/${testManufacturer.id}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk)
        val countAfter = manufacturerRepository.findAll().toList().size

        assertEquals(countBefore - 1, countAfter)
    }
}