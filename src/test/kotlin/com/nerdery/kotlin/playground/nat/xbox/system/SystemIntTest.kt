package com.nerdery.kotlin.playground.nat.xbox.system

import com.nerdery.kotlin.playground.KotlinPlaygroundApplication
import com.nerdery.kotlin.playground.util.TestUtil
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

    lateinit private var mockMvc: MockMvc

    @Inject
    lateinit private var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Inject
    lateinit private var systemController: SystemController

    @Inject
    lateinit private var systemRepository: SystemRepository

    lateinit private var testSystem: System

    private val SYSTEM_URL = "/api/systems"
    private val UNKNOWN_ID = 999

    @Before
    fun beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(systemController)
                .setMessageConverters(jacksonMessageConverter)
                .alwaysDo<StandaloneMockMvcBuilder>(MockMvcResultHandlers.print())
                .build()
        testSystem = systemRepository.save(System(name = "Test System"))
    }

    @After
    fun afterEach() {
        systemRepository.deleteAll()
    }

    @Test
    fun listAllSuccess() {
        mockMvc.perform(
                get(SYSTEM_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.length()", `is`(1)))
    }

    @Test
    fun listOneSuccess() {
        mockMvc.perform(
                get("$SYSTEM_URL/${testSystem.id}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id", `is`(testSystem.id?.toInt())))
                .andExpect(jsonPath("$.name", `is`(testSystem.name)))
    }

    @Test
    fun listOneNotFound() {
        mockMvc.perform(
                get("$SYSTEM_URL/$UNKNOWN_ID")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound)
    }

    @Test
    fun saveSuccess() {
        val newSystem = System(name = "New System")

        val beforeCount = systemRepository.findAll().toList().size
        mockMvc.perform(
                post(SYSTEM_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJson(newSystem)))
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", `is`(newSystem.name)))
        val afterCount = systemRepository.findAll().toList().size

        assertEquals(beforeCount + 1, afterCount)
    }

    @Test
    fun updateSuccess() {
        val updatedSystem = testSystem.copy(name = "Updated System")

        mockMvc.perform(
                put("$SYSTEM_URL/${testSystem.id}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJson(updatedSystem)))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id", `is`(updatedSystem.id?.toInt())))
                .andExpect(jsonPath("$.name", `is`(updatedSystem.name)))
    }

    @Test
    fun updateNotFound() {
        val updatedSystem = testSystem.copy(name = "Updated System")

        mockMvc.perform(
                put("$SYSTEM_URL/$UNKNOWN_ID")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJson(updatedSystem)))
                .andExpect(status().isNotFound)
    }

    @Test
    fun deleteSuccess() {
        val countBefore = systemRepository.findAll().toList().size
        mockMvc.perform(
                delete("$SYSTEM_URL/${testSystem.id}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk)
        val countAfter = systemRepository.findAll().toList().size

        assertEquals(countBefore - 1, countAfter)
    }

    @Test
    fun deleteNotFound() {
        val countBefore = systemRepository.findAll().toList().size
        mockMvc.perform(
                delete("$SYSTEM_URL/$UNKNOWN_ID")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound)
        val countAfter = systemRepository.findAll().toList().size

        assertEquals(countBefore, countAfter)

    }
}