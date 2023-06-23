package io.directional.wine.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.directional.wine.dto.CreateWineRequest
import io.directional.wine.service.WineService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@WebMvcTest(WineController::class)
class WineControllerTest {

    @MockBean
    private lateinit var wineService: WineService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private val ctx: WebApplicationContext? = null

    private val BASE_URL = "/api/v1"

    private val wineryId = 1L

    private val regionId = 1L

    private val importerId = 1L

    private val createWineRequest = CreateWineRequest(
        type = "RED",
        nameKorean = "1881 나파, 카베르네 소비뇽",
        nameEnglish = "1881 Napa, Cabernet Sauvignon",
        alcohol = 14.5,
        acidity = 3,
        body = 4,
        sweetness = 1,
        tannin = 4,
        servingTemperature = 17.0,
        score = 90.0,
        price = 200000,
        style = "Californian Cabernet Sauvignon",
        grade = null,
    )

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx!!)
            .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .build()
    }

    private fun Any.toJsonString(): String = ObjectMapper().writeValueAsString(this)

    @Test
    @DisplayName("와인 생성 성공 테스트")
    fun createWine_Success_Test() {
        Mockito.doNothing().`when`(wineService).createWine(wineryId, regionId, importerId, createWineRequest)

        mockMvc.perform(
            MockMvcRequestBuilders.post("$BASE_URL/{wineryId}/{regionId}/{importerId}/wines", wineryId, regionId,importerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createWineRequest.toJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)

        Mockito.verify(wineService).createWine(wineryId, regionId, importerId, createWineRequest)
    }

}