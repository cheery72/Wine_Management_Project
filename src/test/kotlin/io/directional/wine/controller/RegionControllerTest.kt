package io.directional.wine.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.directional.wine.dto.CreateRegionRequest
import io.directional.wine.service.RegionService
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

@WebMvcTest(RegionController::class)
class RegionControllerTest{

    @MockBean
    private lateinit var regionService: RegionService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private val ctx: WebApplicationContext? = null

    private val BASE_URL = "/api/v1"

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx!!)
            .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .build()
    }

    private fun Any.toJsonString(): String = ObjectMapper().writeValueAsString(this)

    @Test
    @DisplayName("지역 생성 성공 테스트")
    fun createRegion_Success_Test() {

        val createRegionRequest = CreateRegionRequest(
            regionNameKorean = "korea",
            regionNameEnglish = "english",
            regionParentId = 1L
        )

        Mockito.doNothing().`when`(regionService).createRegion(Mockito.mock(CreateRegionRequest::class.java))

        mockMvc.perform(
            MockMvcRequestBuilders.post("$BASE_URL/regions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRegionRequest.toJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)

        Mockito.verify(regionService).createRegion(createRegionRequest)
    }

}