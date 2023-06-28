package io.directional.wine.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.directional.wine.payload.request.CreateWineRequest
import io.directional.wine.payload.request.UpdateWineRequest
import io.directional.wine.payload.response.WineDetailsResponse
import io.directional.wine.payload.response.WineWithTopRegionResponse
import io.directional.wine.service.WineService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
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

    private val importerId = 1L


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
        val createWineRequest = CreateWineRequest(
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

        Mockito.doNothing().`when`(wineService)
            .createWine(wineryId, importerId, Mockito.mock(CreateWineRequest::class.java))

        mockMvc.perform(
            MockMvcRequestBuilders.post("$BASE_URL/{wineryId}/{importerId}/wines", wineryId, importerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createWineRequest.toJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)

        Mockito.verify(wineService).createWine(wineryId, importerId, createWineRequest)
    }

    @Test
    @DisplayName("와인 수정 성공 테스트")
    fun updateWine_Success_Test() {
        val wineId = 1L
        val updateWineRequest = UpdateWineRequest(
            type = "SPARKLING",
            nameKorean = "알프레드 그라티엔, 클래식 브뤼",
            nameEnglish = "Alfred Gratien, Classic Brut",
            alcohol = 12.5,
            acidity = 4,
            body = 1,
            sweetness = 1,
            tannin = 1,
            servingTemperature = 7.0,
            score = 92.0,
            price = 190000,
            style = "French Champagne",
            grade = "AOC(AOP)",
            wineryId = 2L,
            importerId = 2L
        )

        Mockito.doNothing().`when`(wineService).updateWine(wineId, updateWineRequest)
        mockMvc.perform(
            MockMvcRequestBuilders.put("$BASE_URL/{wineId}/wines", wineId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateWineRequest.toJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)

        Mockito.verify(wineService).updateWine(wineId, updateWineRequest)
    }

    @Test
    @DisplayName("와인 삭제 성공 테스트")
    fun deleteWine_Success_Test() {
        val wineId = 1L

        Mockito.doNothing().`when`(wineService).deleteWine(wineId)
        mockMvc.perform(
            MockMvcRequestBuilders.delete("$BASE_URL/{wineId}/wines", wineId)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)

        Mockito.verify(wineService).deleteWine(wineId)
    }

    @Test
    @DisplayName("와인 단일 조회 성공 테스트")
    fun findWineDetails_Success_Test() {
        val wineName = "1881 Napa, Cabernet Sauvignon"
        val wineType = "RED"
        val alcoholMin = 13.5
        val alcoholMax = 15.0
        val priceMin = 190000
        val priceMax = 210000
        val wineStyle = "Californian Cabernet Sauvignon"
        val wineGrade = null
        val wineRegion = "나파 밸리"


        Mockito.`when`(
            wineService.findWineDetails(
                anyString(), anyString(), anyDouble(), anyDouble(), anyInt(),
                anyInt(), anyString(), anyString(), anyString()
            )
        )
            .thenReturn(mock(WineDetailsResponse::class.java))

        mockMvc.perform(
            MockMvcRequestBuilders.get("$BASE_URL/wines")
                .param("wineName", wineName)
                .param("wineType", wineType)
                .param("alcoholMin", alcoholMin.toString())
                .param("alcoholMax", alcoholMax.toString())
                .param("priceMin", priceMin.toString())
                .param("priceMax", priceMax.toString())
                .param("wineStyle", wineStyle)
                .param("wineGrade", wineGrade)
                .param("wineRegion", wineRegion)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        Mockito.verify(wineService).findWineDetails(
            wineName, wineType, alcoholMin, alcoholMax, priceMin, priceMax,
            wineStyle, wineGrade, wineRegion
        )
    }

    @Test
    @DisplayName("와인 다수 조회 테스트")
    fun findWineWithTopRegion_Success_Test() {
        val wineName = "1881 Napa, Cabernet Sauvignon"
        val wineType = "RED"
        val alcoholMin = 13.5
        val alcoholMax = 15.0
        val priceMin = 190000
        val priceMax = 210000
        val wineStyle = "Californian Cabernet Sauvignon"
        val wineGrade = null
        val wineRegion = "나파 밸리"


        Mockito.`when`(
            wineService.findWineWithTopRegion(
                anyString(), anyString(), anyDouble(), anyDouble(), anyInt(),
                anyInt(), anyString(), anyString(), anyString()
            )
        )
            .thenReturn(listOf(mock(WineWithTopRegionResponse::class.java)))

        mockMvc.perform(
            MockMvcRequestBuilders.get("$BASE_URL/wines/all")
                .param("wineName", wineName)
                .param("wineType", wineType)
                .param("alcoholMin", alcoholMin.toString())
                .param("alcoholMax", alcoholMax.toString())
                .param("priceMin", priceMin.toString())
                .param("priceMax", priceMax.toString())
                .param("wineStyle", wineStyle)
                .param("wineGrade", wineGrade)
                .param("wineRegion", wineRegion)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        Mockito.verify(wineService).findWineWithTopRegion(
            wineName, wineType, alcoholMin, alcoholMax, priceMin, priceMax,
            wineStyle, wineGrade, wineRegion
        )

    }
}