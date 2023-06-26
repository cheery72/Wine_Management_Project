package io.directional.wine.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.directional.wine.dto.CreateGrapeRequest
import io.directional.wine.service.GrapeService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
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

@WebMvcTest(GrapeController::class)
class GrapeControllerTest {

    @MockBean
    private lateinit var grapeService: GrapeService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private val ctx: WebApplicationContext? = null

    private val BASE_URL = "/api/v1"

    private val createGrapeRequest = CreateGrapeRequest(
        grapeNameKorean = "알리아니코",
        grapeNameEnglish = "Aglianico",
        grapeAcidity = 4,
        grapeBody = 5,
        grapeSweetness = 1,
        grapeTannin = 4,
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
    @DisplayName("포도 품종 생성 성공 테스트")
    fun createGrape_Success_Test() {
        Mockito.doNothing().`when`(grapeService).createGrape(Mockito.mock(CreateGrapeRequest::class.java))

        mockMvc.perform(
            MockMvcRequestBuilders.post("$BASE_URL/grapes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createGrapeRequest.toJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)

        Mockito.verify(grapeService).createGrape(createGrapeRequest)
    }

    @Test
    @DisplayName("포도 품종 수정 성공 테스트")
    fun updateGrape_Success_Test() {
        val grapeId = 1L

        Mockito.doNothing().`when`(grapeService).updateGrape(grapeId,createGrapeRequest)
        mockMvc.perform(
            MockMvcRequestBuilders.put("$BASE_URL/{grapeId}/grapes", grapeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createGrapeRequest.toJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)

        Mockito.verify(grapeService).updateGrape(grapeId, createGrapeRequest)
    }

    @Test
    @DisplayName("포도 품종 삭제 성공 테스트")
    fun deleteGrape_Success_Test() {
        val grapeId = 1L

        Mockito.doNothing().`when`(grapeService).deleteGrape(grapeId)
        mockMvc.perform(
            MockMvcRequestBuilders.delete("$BASE_URL/{grapeId}/grapes", grapeId)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)

        Mockito.verify(grapeService).deleteGrape(grapeId)
    }

}