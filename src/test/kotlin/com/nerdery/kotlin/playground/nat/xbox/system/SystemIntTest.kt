package com.nerdery.kotlin.playground.nat.xbox.system

import com.nerdery.kotlin.playground.KotlinPlaygroundApplication
import com.nerdery.kotlin.playground.nat.xbox.exceptions.handlers.RestExceptionHandler
import com.nerdery.kotlin.playground.nat.xbox.manufacturer.Manufacturer
import com.nerdery.kotlin.playground.nat.xbox.manufacturer.ManufacturerRepository
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
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
class SystemIntTest {

    private lateinit var mockMvc: MockMvc

    @Inject
    private lateinit var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Inject
    private lateinit var systemController: SystemController

    @Inject
    private lateinit var systemRepository: SystemRepository

    @Inject
    private lateinit var manufacturerRepository: ManufacturerRepository

    private lateinit var testManufacturer: Manufacturer
    private lateinit var testSystem: System

    private val systemUrl = "/api/systems"
    private val unknownId = 999

    @Before
    fun beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(systemController)
                .setMessageConverters(jacksonMessageConverter)
                .setControllerAdvice(RestExceptionHandler())
                .alwaysDo<StandaloneMockMvcBuilder>(MockMvcResultHandlers.print())
                .build()

        testManufacturer = manufacturerRepository.save(Manufacturer(name = "Test Manufacturer"))
        testSystem = systemRepository.save(System(name = "Test System", manufacturerId = testManufacturer.id))
    }

    @After
    fun afterEach() {
        systemRepository.deleteAll()
    }

    @Test
    fun listAllSuccess() {
        mockMvc.perform(
                get(systemUrl)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.length()", `is`(1)))
    }

    @Test
    fun listOneSuccess() {
        mockMvc.perform(
                get("$systemUrl/${testSystem.id}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.system.id", `is`(testSystem.id.toInt())))
                .andExpect(jsonPath("$.system.name", `is`(testSystem.name)))
                .andExpect(jsonPath("$.manufacturerId", `is`(testManufacturer.id.toInt())))
    }

    @Test
    fun listOneNotFound() {
        mockMvc.perform(
                get("$systemUrl/$unknownId")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound)
    }

    @Test
    fun saveSuccess() {
        val name = "New System"
        val json = """
            {
                "manufacturerId": ${testManufacturer.id},
                "system": {
                    "name": "$name"
                }
            }
        """.trimIndent()

        val beforeCount = systemRepository.findAll().toList().size
        mockMvc.perform(
                post(systemUrl)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.manufacturerId", `is`(testManufacturer.id.toInt())))
                .andExpect(jsonPath("$.system.id", notNullValue()))
                .andExpect(jsonPath("$.system.name", `is`(name)))
        val afterCount = systemRepository.findAll().toList().size

        assertEquals(beforeCount + 1, afterCount)
    }

    @Test
    fun saveSystemFailure() {
        val json = """
            {
                "system": {
                    "name": "Test System"
                }
            }
        """.trimIndent()

        mockMvc.perform(
                post(systemUrl)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun updateSuccess() {
        val name = "Updated System"
        val json = """
            {
                "manufacturerId": ${testManufacturer.id},
                "system": {
                    "name": "$name"
                }
            }
        """.trimIndent()

        mockMvc.perform(
                put("$systemUrl/${testSystem.id}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.manufacturerId", `is`(testManufacturer.id.toInt())))
                .andExpect(jsonPath("$.system.id", `is`(testSystem.id.toInt())))
                .andExpect(jsonPath("$.system.name", `is`(name)))
    }

    @Test
    fun updateNotFound() {
        val json = """
            {
                "manufacturerId": ${testManufacturer.id},
                "system": {
                    "name": "Updated System"
                }
            }
        """.trimIndent()

        mockMvc.perform(
                put("$systemUrl/$unknownId")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().isNotFound)
    }

    @Test
    fun deleteSuccess() {
        val countBefore = systemRepository.findAll().toList().size
        mockMvc.perform(
                delete("$systemUrl/${testSystem.id}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk)
        val countAfter = systemRepository.findAll().toList().size

        assertEquals(countBefore - 1, countAfter)
    }

    @Test
    fun deleteNotFound() {
        val countBefore = systemRepository.findAll().toList().size
        mockMvc.perform(
                delete("$systemUrl/$unknownId")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound)
        val countAfter = systemRepository.findAll().toList().size

        assertEquals(countBefore, countAfter)

    }
}