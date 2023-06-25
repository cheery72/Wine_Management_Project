package io.directional.wine.service

import io.directional.wine.dto.*
import io.directional.wine.entity.Importer
import io.directional.wine.entity.Wine
import io.directional.wine.entity.Winery
import io.directional.wine.exception.ClientException
import io.directional.wine.exception.ErrorCode
import io.directional.wine.repository.ImporterRepository
import io.directional.wine.repository.RegionRepository
import io.directional.wine.repository.WineRepository
import io.directional.wine.repository.WineryRepository
import org.assertj.core.api.Assertions.assertThat
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
    private lateinit var importerRepository: ImporterRepository

    @Mock
    private lateinit var regionRepository: RegionRepository

    private lateinit var wineService: WineService

    private val wineryId = 1L

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

    private val wine = Wine(
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
        winery = mock(Winery::class.java),
        importer =  mock(Importer::class.java)
    )

    private val winery = mock(Winery::class.java)

    private val importer = mock(Importer::class.java)

    private val updateWineRequest = UpdateWineRequest(
        type = "SPARKLING",
        nameKorean = "알프레드 그라티엔, 클래식 브뤼",
        nameEnglish = "Alfred Gratien, Classic Brut",
        alcohol = 12.5,
        acidity = 8,
        body = 1,
        sweetness = 2,
        tannin = 3,
        servingTemperature = 7.0,
        score = 92.0,
        price = 190000,
        style = "French Champagne",
        grade = "AOC(AOP)",
        wineryId = 4L,
        importerId = 5L
    )

    private val wineDetailsDto = WineDetailsDto(
        wineType = "",
        wineKoreanName = "",
        wineEnglishName = "",
        wineAlcohol = 0.0,
        wineAcidity = 0,
        wineBody = 0,
        wineSweetness = 0,
        wineTannin = 0,
        wineScore = 0.0,
        winePrice = 0,
        wineStyle = "",
        wineGrade = "",
        wineImporter = "",
        aroma = "",
        pairing = "",
        wineGrapeKoreanName = "",
        wineGrapeEnglishName = "",
        wineRegionKoreanName = "",
        wineRegionEnglishName = "",
        wineryId = 1L,
        regionId = 5L,
    )

    @BeforeEach
    fun setup() {
        wineService = WineService(wineRepository, wineryRepository, importerRepository, regionRepository)
    }

    @Test
    @DisplayName("와인 생성 성공 테스트")
    fun createWine_Success_Test() {
        // given
        val wine = mock(Wine::class.java)

        // when
        `when`(wineryRepository.findByIdAndDeletedFalse(wineryId)).thenReturn(Optional.of(winery))
        `when`(importerRepository.findByIdAndDeletedFalse(importerId)).thenReturn(Optional.of(importer))
        `when`(wineRepository.save(any(Wine::class.java))).thenReturn(wine)

        wineService.createWine(wineryId,importerId,createWineRequest)

        // then
        val wineCaptor: ArgumentCaptor<Wine> = ArgumentCaptor.forClass(Wine::class.java)
        verify(wineRepository).save(wineCaptor.capture())
        val savedWine: Wine = wineCaptor.value
        assertEquals(savedWine.type,createWineRequest.type)
        assertEquals(savedWine.deleted,false)
        verify(wineryRepository).findByIdAndDeletedFalse(wineryId)
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
            wineService.createWine(wineryId, importerId, createWineRequest)
        }

        // then
        assertEquals(ErrorCode.NOT_FOUND_WINERY, exception.errorCode)
        verify(wineryRepository, times(1)).findByIdAndDeletedFalse(wineryId)
    }

    @Test
    @DisplayName("와인 생성 Importer Exception 테스트")
    fun createWine_Importer_Exception_Test(){
        // when
        `when`(wineryRepository.findByIdAndDeletedFalse(wineryId)).thenReturn(Optional.of(winery))
        `when`(importerRepository.findByIdAndDeletedFalse(importerId)).thenReturn(Optional.empty())

        val exception: ClientException = assertThrows(
            ClientException::class.java
        ) {
            wineService.createWine(wineryId, importerId, createWineRequest)
        }

        // then
        assertEquals(ErrorCode.NOT_FOUND_IMPORTER, exception.errorCode)
        verify(wineryRepository, times(1)).findByIdAndDeletedFalse(wineryId)
        verify(importerRepository, times(1)).findByIdAndDeletedFalse(importerId)
    }

    @Test
    @DisplayName("와인 수정 성공 테스트")
    fun updateWine_Success_Test() {
        // given
        val wineId = 1L

        // when
        `when`(wineRepository.findByIdAndDeletedFalse(wineId)).thenReturn(Optional.of(wine))
        `when`(wineryRepository.findByIdAndDeletedFalse(updateWineRequest.wineryId)).thenReturn(Optional.of(winery))
        `when`(importerRepository.findByIdAndDeletedFalse(updateWineRequest.importerId)).thenReturn(Optional.of(importer))

        wineService.updateWine(wineId,updateWineRequest)

        // then
        assertEquals(wine.type,updateWineRequest.type)
        assertEquals(wine.nameKorean,updateWineRequest.nameKorean)
        assertEquals(wine.nameEnglish,updateWineRequest.nameEnglish)
        assertEquals(wine.alcohol,updateWineRequest.alcohol)
        assertEquals(wine.acidity,updateWineRequest.acidity)
        assertEquals(wine.body,updateWineRequest.body)
        assertEquals(wine.sweetness,updateWineRequest.sweetness)
        assertEquals(wine.tannin,updateWineRequest.tannin)
        assertEquals(wine.servingTemperature,updateWineRequest.servingTemperature)
        assertEquals(wine.score,updateWineRequest.score)
        assertEquals(wine.price,updateWineRequest.price)
        assertEquals(wine.style,updateWineRequest.style)
        assertEquals(wine.grade,updateWineRequest.grade)
    }

    @Test
    @DisplayName("와인 조회 Exception 테스트")
    fun findWine_Exception_Test(){
        // given
        val wineId = 1L

        // when
        `when`(wineRepository.findByIdAndDeletedFalse(wineId)).thenReturn(Optional.empty())

        val exception: ClientException = assertThrows(
            ClientException::class.java
        ) {
            wineService.updateWine(wineId,updateWineRequest)
        }

        // then
        assertEquals(ErrorCode.NOT_FOUND_WINE, exception.errorCode)
        verify(wineRepository, times(1)).findByIdAndDeletedFalse(wineId)
    }

    @Test
    @DisplayName("와인 삭제 성공 테스트")
    fun deleteWine_Success_Test() {
        // given
        val wineId = 1L

        // when
        `when`(wineRepository.findByIdAndDeletedFalse(wineId)).thenReturn(Optional.of(wine))

        wineService.deleteWine(wineId)

        // then
        verify(wineRepository, times(1)).findByIdAndDeletedFalse(wineId)
        assertTrue(wine.deleted)

    }

    @Test
    @DisplayName("와인 단일 조회 성공 테스트")
    fun findWine_Success_Test() {
        // given
        val wineType = "RED"
        val alcoholMin = 13.5
        val alcoholMax = 15.0
        val priceMin = 190000
        val priceMax = 210000
        val wineStyle = "Californian Cabernet Sauvignon"
        val wineGrade = null
        val wineRegion = "나파 밸리"
        val recursiveRegionDto = mock(RecursiveRegionDto::class.java)

        // when
        `when`(wineRepository.findWineDetails(wineType, alcoholMin, alcoholMax, priceMin, priceMax,
            wineStyle, wineGrade, wineRegion)).thenReturn(wineDetailsDto)
        `when`(regionRepository.findByIdRecursiveRegions(wineDetailsDto.regionId)).thenReturn(listOf(recursiveRegionDto))

        val wineDetailsResponse: WineDetailsResponse? = wineService.findWineDetails(
            wineType, alcoholMin, alcoholMax, priceMin, priceMax,
            wineStyle, wineGrade, wineRegion
        )

        // then
        verify(wineRepository, times(1)).findWineDetails(wineType, alcoholMin, alcoholMax, priceMin, priceMax,
            wineStyle, wineGrade, wineRegion)
        verify(regionRepository, times(1)).findByIdRecursiveRegions(wineDetailsDto.regionId)
        assertThat(wineDetailsResponse!!.wineType).isEqualTo(wineDetailsDto.wineType)
        assertThat(wineDetailsResponse.wineKoreanName).isEqualTo(wineDetailsDto.wineKoreanName)
        assertThat(wineDetailsResponse.wineEnglishName).isEqualTo(wineDetailsDto.wineEnglishName)
        assertThat(wineDetailsResponse.wineAlcohol).isEqualTo(wineDetailsDto.wineAlcohol)
        assertThat(wineDetailsResponse.wineAcidity).isEqualTo(wineDetailsDto.wineAcidity)
        assertThat(wineDetailsResponse.wineBody).isEqualTo(wineDetailsDto.wineBody)
        assertThat(wineDetailsResponse.wineSweetness).isEqualTo(wineDetailsDto.wineSweetness)
        assertThat(wineDetailsResponse.wineTannin).isEqualTo(wineDetailsDto.wineTannin)
        assertThat(wineDetailsResponse.wineScore).isEqualTo(wineDetailsDto.wineScore)
        assertThat(wineDetailsResponse.winePrice).isEqualTo(wineDetailsDto.winePrice)
        assertThat(wineDetailsResponse.wineStyle).isEqualTo(wineDetailsDto.wineStyle)
        assertThat(wineDetailsResponse.wineGrade).isEqualTo(wineDetailsDto.wineGrade)
        assertThat(wineDetailsResponse.wineGrapeEnglishName).isEqualTo(wineDetailsDto.wineGrapeEnglishName)
        assertThat(wineDetailsResponse.wineGrapeKoreanName).isEqualTo(wineDetailsDto.wineGrapeKoreanName)
    }
}