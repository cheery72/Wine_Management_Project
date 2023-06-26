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
import java.util.*

@ExtendWith(MockitoExtension::class, SpringExtension::class)
class ImporterServiceTest{

    @Mock
    private lateinit var importerRepository: ImporterRepository

    private lateinit var importerService: ImporterService

    private val importer = Importer(
        name = "수입사"
    )

    private val createImporterRequest = CreateImporterRequest(
        importerName = "importerName"
    )

    @BeforeEach
    fun setup() {
        importerService = ImporterService(importerRepository)
    }

    @Test
    @DisplayName("수입사 생성 성공 테스트")
    fun createImporter_Success_Test() {
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

    @Test
    @DisplayName("수입사 수정 성공 테스트")
    fun updateImporter_Success_Test() {
        // given
        val importerId = 1L

        // when
        Mockito.`when`(importerRepository.findByIdAndDeletedFalse(importerId)).thenReturn(Optional.of(importer))

        importerService.updateImporter(importerId,createImporterRequest)

        // then
        assertEquals(importer.name,createImporterRequest.importerName)
    }

    @Test
    @DisplayName("수입사 삭제 성공 테스트")
    fun deleteImporterId_Success_Test() {
        // given
        val importerId = 1L

        // when
        Mockito.`when`(importerRepository.findByIdAndDeletedFalse(importerId)).thenReturn(Optional.of(importer))

        importerService.deleteImporter(importerId)

        // then
        Mockito.verify(importerRepository, Mockito.times(1)).findByIdAndDeletedFalse(importerId)
        assertTrue(importer.deleted)
    }
}