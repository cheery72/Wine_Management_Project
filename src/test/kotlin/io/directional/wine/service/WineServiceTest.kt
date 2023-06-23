package io.directional.wine.service

import io.directional.wine.dto.CreateWineRequest
import io.directional.wine.entity.Importer
import io.directional.wine.entity.Region
import io.directional.wine.entity.Wine
import io.directional.wine.entity.Winery
import io.directional.wine.exception.ClientException
import io.directional.wine.exception.ErrorCode
import io.directional.wine.repository.ImporterRepository
import io.directional.wine.repository.RegionRepository
import io.directional.wine.repository.WineRepository
import io.directional.wine.repository.WineryRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*


@ExtendWith(MockitoExtension::class, SpringExtension::class)
class WineServiceTest {

    @Mock
    private lateinit var wineRepository: WineRepository

    @Mock
    private lateinit var wineryRepository: WineryRepository

    @Mock
    private lateinit var regionRepository: RegionRepository

    @Mock
    private lateinit var importerRepository: ImporterRepository

    private lateinit var wineService: WineService

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
        wineService = WineService(wineRepository, wineryRepository, regionRepository, importerRepository)
    }

    @Test
    @DisplayName("와인 생성 성공 테스트")
    fun createWine_Success_Test() {
        // given
        val winery = mock(Winery::class.java)
        val region = mock(Region::class.java)
        val importer = mock(Importer::class.java)
        val wine = mock(Wine::class.java)

        // when
        `when`(wineryRepository.findByIdAndDeletedFalse(wineryId)).thenReturn(Optional.of(winery))
        `when`(regionRepository.findByIdAndDeletedFalse(regionId)).thenReturn(Optional.of(region))
        `when`(importerRepository.findByIdAndDeletedFalse(importerId)).thenReturn(Optional.of(importer))
        `when`(wineRepository.save(any(Wine::class.java))).thenReturn(wine)

        wineService.createWine(wineryId,regionId,importerId,createWineRequest)

        // then
        val wineCaptor: ArgumentCaptor<Wine> = ArgumentCaptor.forClass(Wine::class.java)
        verify(wineRepository).save(wineCaptor.capture())
        val savedWine: Wine = wineCaptor.value
        assertEquals(savedWine.type,createWineRequest.type)
        assertEquals(savedWine.deleted,false)
        verify(wineryRepository).findByIdAndDeletedFalse(wineryId)
        verify(regionRepository).findByIdAndDeletedFalse(regionId)
        verify(importerRepository).findByIdAndDeletedFalse(importerId)
        verify(wineRepository).save(any(Wine::class.java))
    }

    @Test
    @DisplayName("와인 생성 Winery Exception 테스트")
    fun createWine_Winery_Exception_Test(){
        // when
        `when`(wineryRepository.findByIdAndDeletedFalse(wineryId)).thenReturn(Optional.empty())

        val exception: ClientException = assertThrows(
            ClientException::class.java
        ) {
            wineService.createWine(wineryId, regionId, importerId, createWineRequest)
        }

        // then
        assertEquals(ErrorCode.NOT_FOUND_WINERY, exception.errorCode)
        verify(wineryRepository, times(1)).findByIdAndDeletedFalse(wineryId)
    }

    @Test
    @DisplayName("와인 생성 Region Exception 테스트")
    fun createWine_Region_Exception_Test(){
        // given
        val winery = mock(Winery::class.java)

        // when
        `when`(wineryRepository.findByIdAndDeletedFalse(wineryId)).thenReturn(Optional.of(winery))
        `when`(regionRepository.findByIdAndDeletedFalse(regionId)).thenReturn(Optional.empty())

        val exception: ClientException = assertThrows(
            ClientException::class.java
        ) {
            wineService.createWine(wineryId, regionId, importerId, createWineRequest)
        }

        // then
        assertEquals(ErrorCode.NOT_FOUND_REGION, exception.errorCode)
        verify(wineryRepository, times(1)).findByIdAndDeletedFalse(wineryId)
        verify(regionRepository, times(1)).findByIdAndDeletedFalse(regionId)
    }

    @Test
    @DisplayName("와인 생성 Importer Exception 테스트")
    fun createWine_Importer_Exception_Test(){
        // given
        val winery = mock(Winery::class.java)
        val region = mock(Region::class.java)

        // when
        `when`(wineryRepository.findByIdAndDeletedFalse(wineryId)).thenReturn(Optional.of(winery))
        `when`(regionRepository.findByIdAndDeletedFalse(regionId)).thenReturn(Optional.of(region))
        `when`(importerRepository.findByIdAndDeletedFalse(importerId)).thenReturn(Optional.empty())

        val exception: ClientException = assertThrows(
            ClientException::class.java
        ) {
            wineService.createWine(wineryId, regionId, importerId, createWineRequest)
        }

        // then
        assertEquals(ErrorCode.NOT_FOUND_IMPORTER, exception.errorCode)
        verify(wineryRepository, times(1)).findByIdAndDeletedFalse(wineryId)
        verify(regionRepository, times(1)).findByIdAndDeletedFalse(regionId)
        verify(importerRepository, times(1)).findByIdAndDeletedFalse(importerId)
    }
}