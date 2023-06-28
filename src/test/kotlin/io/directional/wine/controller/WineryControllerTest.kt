package io.directional.wine.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.directional.wine.payload.request.CreateWineryRequest
import io.directional.wine.payload.request.UpdateWineryRequest
import io.directional.wine.payload.response.WineryWithRegionResponse
import io.directional.wine.payload.response.WineryWithRegionWithWineNameResponse
import io.directional.wine.service.WineryService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
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


@WebMvcTest(WineryController::class)
class WineryControllerTest {

    @MockBean
    private lateinit var wineryService: WineryService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private val ctx: WebApplicationContext? = null

    private val BASE_URL = "/api/v1"

    private val regionId = 1L

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx!!)
            .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .build()
    }

    private fun Any.toJsonString(): String = ObjectMapper().writeValueAsString(this)

    @Test
    @DisplayName("와이너리 생성 성공 테스트")
    fun createWinery_Success_Test() {
        val createWineryRequest = CreateWineryRequest(
            wineryNameEnglish = "wineryNameEnglish",
            wineryNameKorean = "wineryNameKorean",
        )

        Mockito.doNothing().`when`(wineryService).createWinery(regionId, Mockito.mock(CreateWineryRequest::class.java))

        mockMvc.perform(
            MockMvcRequestBuilders.post("$BASE_URL/{regionId}/winerys", regionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createWineryRequest.toJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)

        Mockito.verify(wineryService).createWinery(regionId, createWineryRequest)
    }

    @Test
    @DisplayName("와이너리 수정 성공 테스트")
    fun updateWinery_Success_Test() {
        val wineryId = 1L
        val updateWineryRequest = UpdateWineryRequest(
            regionId = 1L,
            wineryNameEnglish = "wineryNameEnglish",
            wineryNameKorean = "wineryNameKorean",
        )

        Mockito.doNothing().`when`(wineryService).updateWinery(wineryId, updateWineryRequest)
        mockMvc.perform(
            MockMvcRequestBuilders.put("$BASE_URL/{wineryId}/winerys", wineryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateWineryRequest.toJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)

        Mockito.verify(wineryService).updateWinery(wineryId, updateWineryRequest)
    }

    @Test
    @DisplayName("와이너리 삭제 성공 테스트")
    fun deleteWinery_Success_Test() {
        val wineryId = 1L

        Mockito.doNothing().`when`(wineryService).deleteWinery(wineryId)
        mockMvc.perform(
            MockMvcRequestBuilders.delete("$BASE_URL/{wineryId}/winerys", wineryId)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)

        Mockito.verify(wineryService).deleteWinery(wineryId)
    }

    @Test
    @DisplayName("와이너리 단일 조회 성공 테스트")
    fun findWineryWithRegion_Success_Test() {
        val wineryName = "wineryName"
        val wineryRegion = "wineryRegion"

        Mockito.`when`(
            wineryService.findWineryWithRegion(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
            )
        )
            .thenReturn(Mockito.mock(WineryWithRegionWithWineNameResponse::class.java))

        mockMvc.perform(
            MockMvcRequestBuilders.get("$BASE_URL/winerys")
                .param("wineryName", wineryName)
                .param("wineryRegion", wineryRegion)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        Mockito.verify(wineryService).findWineryWithRegion(wineryName, wineryRegion)
    }

    @Test
    @DisplayName("와이너리 다수 조회 성공 테스트")
    fun findWineryWithRegionAll_Success_Test() {
        val wineryName = "wineryName"
        val wineryRegion = "wineryRegion"

        Mockito.`when`(
            wineryService.findWineryWithRegionAll(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
            )
        )
            .thenReturn(listOf(Mockito.mock(WineryWithRegionResponse::class.java)))

        mockMvc.perform(
            MockMvcRequestBuilders.get("$BASE_URL/winerys/all")
                .param("wineryName", wineryName)
                .param("wineryRegion", wineryRegion)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        Mockito.verify(wineryService).findWineryWithRegionAll(wineryName, wineryRegion)
    }
}