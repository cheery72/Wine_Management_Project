package io.directional.wine.service

import io.directional.wine.dto.CreateImporterRequest
import io.directional.wine.entity.Importer
import io.directional.wine.repository.ImporterRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(MockitoExtension::class, SpringExtension::class)
class ImporterServiceTest{

    @Mock
    private lateinit var importerRepository: ImporterRepository

    private lateinit var importerService: ImporterService

    @BeforeEach
    fun setup() {
        importerService = ImporterService(importerRepository)
    }

    @Test
    @DisplayName("수입사 생성 성공 테스트")
    fun createImporter_Success_Test() {
        // given
        val createImporterRequest = CreateImporterRequest(
            importerName = "importerName"
        )

        // when
        importerService.createImporter(createImporterRequest)

        // then
        val importerCaptor: ArgumentCaptor<Importer> = ArgumentCaptor.forClass(Importer::class.java)
        Mockito.verify(importerRepository).save(importerCaptor.capture())
        val savedImporter: Importer = importerCaptor.value
        assertEquals(savedImporter.name,createImporterRequest.importerName)
        assertEquals(savedImporter.deleted,false)
        Mockito.verify(importerRepository).save(Mockito.any(Importer::class.java))
    }
}