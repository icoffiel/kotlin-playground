package com.nerdery.kotlin.playground.nat.xbox.manufacturer

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import javax.inject.Inject

@RunWith(SpringRunner::class)
@WebMvcTest(ManufacturerController::class)
class ManufacturerControllerUnitTest {

    @Inject
    lateinit private var mvc: MockMvc

    @MockBean
    lateinit private var manufacturerService: ManufacturerService

    private val testManufacturer = Manufacturer(1, "Manufacturer Name")

    @Test
    fun getAll() {
        given(manufacturerService.listAll())
                .willReturn((mutableListOf(testManufacturer)))

        mvc.perform(get("/api/manufacturer"))
                .andExpect(status().isOk)
                .andExpect(content().json("""
                    [
                        {
                          "id": 1,
                          "name": "Manufacturer Name"
                        }
                    ]
                    """))
    }

    @Test
    fun getOne() {
        given(manufacturerService.getOne(1))
                .willReturn(testManufacturer)

        mvc.perform(get("/api/manufacturer/1"))
                .andExpect(status().isOk)
                .andExpect(content().json("""
                    {
                        "id": 1,
                        "name": "Manufacturer Name"
                    }
                """))
    }

    @Test
    fun save() {
        given(manufacturerService.save(Manufacturer(name = "Manufacturer Name")))
                .willReturn(testManufacturer)

        mvc.perform(
            post("/api/manufacturer")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content("""
                        {
                            "name": "Manufacturer Name"
                        }
                    """))
                .andExpect(status().isCreated)
                .andExpect(content().json("""
                    {
                        "id": 1,
                        "name": "Manufacturer Name"
                    }
                """))
    }

    @Test
    fun update() {
        given(manufacturerService.update(1, Manufacturer(name = "Manufacturer Name")))
                .willReturn(testManufacturer)

        mvc.perform(
            put("/api/manufacturer/1")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content("""
                        {
                            "name": "Manufacturer Name"
                        }
                    """))
                .andExpect(status().isOk)
                .andExpect(content().json("""
                    {
                        "id": 1,
                        "name": "Manufacturer Name"
                    }
                """))
    }

    @Test
    fun delete() {
        doNothing().`when`(manufacturerService).delete(1)

        mvc.perform(delete("/api/manufacturer/1"))
                .andExpect(status().isOk)
                .andExpect(content().string(""))
    }

}