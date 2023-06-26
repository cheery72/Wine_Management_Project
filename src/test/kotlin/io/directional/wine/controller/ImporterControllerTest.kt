package io.directional.wine.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.directional.wine.dto.CreateImporterRequest
import io.directional.wine.dto.ImporterWithWineDto
import io.directional.wine.service.ImporterService
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

@WebMvcTest(ImporterController::class)
class ImporterControllerTest{

    @MockBean
    private lateinit var importerService: ImporterService

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
    @DisplayName("수입사 생성 성공 테스트")
    fun createImporter_Success_Test() {
        val createImporterRequest = CreateImporterRequest(
            importerName = "importerName"
        )

        Mockito.doNothing().`when`(importerService).createImporter(Mockito.mock(CreateImporterRequest::class.java))

        mockMvc.perform(
            MockMvcRequestBuilders.post("$BASE_URL/importers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createImporterRequest.toJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)

        Mockito.verify(importerService).createImporter(createImporterRequest)
    }

    @Test
    @DisplayName("수입사 수정 성공 테스트")
    fun updateImporter_Success_Test() {
        val importerId = 1L

        val createImporterRequest = CreateImporterRequest(
            importerName = "importerName"
        )

        Mockito.doNothing().`when`(importerService).updateImporter(importerId,createImporterRequest)
        mockMvc.perform(
            MockMvcRequestBuilders.put("$BASE_URL/{importerId}/importers", importerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createImporterRequest.toJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)

        Mockito.verify(importerService).updateImporter(importerId,createImporterRequest)
    }

    @Test
    @DisplayName("수입사 삭제 성공 테스트")
    fun deleteImporter_Success_Test() {
        val importerId = 1L

        Mockito.doNothing().`when`(importerService).deleteImporter(importerId)
        mockMvc.perform(
            MockMvcRequestBuilders.delete("$BASE_URL/{importerId}/importers", importerId)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)

        Mockito.verify(importerService).deleteImporter(importerId)
    }

    @Test
    @DisplayName("수입사 단일 조회 성공 테스트")
    fun findImporterNameWithWine_Success_Test() {
        val importerName = "importerName"

        Mockito.`when`(importerService.findImporterNameWithWine(
            ArgumentMatchers.anyString(),
        ))
            .thenReturn(Mockito.mock(ImporterWithWineDto::class.java))

        mockMvc.perform(
            MockMvcRequestBuilders.get("$BASE_URL/importers")
                .param("importerName",importerName)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        Mockito.verify(importerService).findImporterNameWithWine(importerName)
    }
}