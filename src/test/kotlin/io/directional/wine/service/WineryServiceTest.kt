package io.directional.wine.service

import io.directional.wine.payload.*
import io.directional.wine.payload.request.CreateWineryRequest
import io.directional.wine.payload.request.UpdateWineryRequest
import io.directional.wine.payload.response.WineryWithRegionResponse
import io.directional.wine.payload.response.WineryWithRegionWithWineNameResponse
import io.directional.wine.entity.Region
import io.directional.wine.entity.Winery
import io.directional.wine.exception.ClientException
import io.directional.wine.exception.ErrorCode
import io.directional.wine.payload.dto.WineryWithRegionDto
import io.directional.wine.payload.dto.WineryWithRegionWithWineNameDto
import io.directional.wine.repository.RegionRepository
import io.directional.wine.repository.WineryRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(MockitoExtension::class, SpringExtension::class)
class WineryServiceTest {

    @Mock
    private lateinit var wineryRepository: WineryRepository

    @Mock
    private lateinit var regionRepository: RegionRepository

    private lateinit var wineryService: WineryService

    private val region = mock(Region::class.java)

    private val createWineryRequest = CreateWineryRequest(
        wineryNameEnglish = "wineryNameEnglish",
        wineryNameKorean = "wineryNameKorean",
    )

    private val regionId = 1L

    private val winery = Winery(
        id = 1L,
        nameEnglish = "wineryNameEnglish",
        nameKorean = "wineryNameKorean",
        region = region
    )

    @BeforeEach
    fun setup() {
        wineryService = WineryService(wineryRepository, regionRepository)
    }


    @Test
    @DisplayName("와이너리 생성 성공 테스트")
    fun createWinery_Success_Test() {
        // when
        `when`(regionRepository.findByIdAndDeletedFalse(regionId)).thenReturn(Optional.of(region))

        wineryService.createWinery(regionId, createWineryRequest)

        // then
        val wineryCaptor: ArgumentCaptor<Winery> = ArgumentCaptor.forClass(Winery::class.java)
        verify(wineryRepository).save(wineryCaptor.capture())
        val savedWinery: Winery = wineryCaptor.value
        assertEquals(savedWinery.nameEnglish, createWineryRequest.wineryNameEnglish)
        assertEquals(savedWinery.nameKorean, createWineryRequest.wineryNameKorean)
        assertEquals(savedWinery.deleted, false)
        verify(regionRepository).findByIdAndDeletedFalse(regionId)
        verify(wineryRepository).save(any(Winery::class.java))
    }

    @Test
    @DisplayName("와이너리 생성 지역 Exception 테스트")
    fun createWinery_Region_Exception_Test() {
        // when
        `when`(regionRepository.findByIdAndDeletedFalse(regionId)).thenReturn(Optional.empty())

        val exception: ClientException = assertThrows(
            ClientException::class.java
        ) {
            wineryService.createWinery(regionId, createWineryRequest)
        }

        // then
        assertEquals(exception.errorCode, ErrorCode.NOT_FOUND_REGION)
        verify(regionRepository, times(1)).findByIdAndDeletedFalse(regionId)
    }

    @Test
    @DisplayName("와이너리 수정 성공 테스트")
    fun updateWinery_Success_Test() {
        // given
        val wineryId = 1L

        val updateWineryRequest = UpdateWineryRequest(
            regionId = 2L,
            wineryNameKorean = "wineryNameKorean 수정",
            wineryNameEnglish = "wineryNameEnglish 수정"
        )

        // when
        `when`(wineryRepository.findByIdAndDeletedFalse(wineryId)).thenReturn(Optional.of(winery))
        `when`(regionRepository.findByIdAndDeletedFalse(updateWineryRequest.regionId)).thenReturn(Optional.of(region))

        wineryService.updateWinery(wineryId, updateWineryRequest)

        // then
        assertEquals(winery.nameKorean, updateWineryRequest.wineryNameKorean)
        assertEquals(winery.nameEnglish, updateWineryRequest.wineryNameEnglish)

    }

    @Test
    @DisplayName("와이너리 삭제 성공 테스트")
    fun deleteWinery_Success_Test() {
        // given
        val wineryId = 1L

        // when
        `when`(wineryRepository.findByIdAndDeletedFalse(wineryId)).thenReturn(Optional.of(winery))

        wineryService.deleteWinery(wineryId)

        // then
        verify(wineryRepository, times(1)).findByIdAndDeletedFalse(wineryId)
        assertTrue(winery.deleted)

    }


    @Test
    @DisplayName("와이너리 단일 조회 성공 테스트")
    fun findWineryWithRegion_Success_Test() {
        // given
        val wineryName = "name"
        val wineryRegion = "region"
        val wineryWithRegionDto = WineryWithRegionWithWineNameDto(
            wineryNameEnglish = "wineryNameEnglish",
            wineryNameKorean = "wineryNameKorean",
            wineryRegionNameEnglish = "wineryRegionNameEnglish",
            wineryRegionNameKorean = "wineryRegionNameKorean",
            wineryWineNameEnglish = "wineryWineNameEnglish",
            wineryWineNameKorean = "wineryWineNameKorean",
        )

        // when
        `when`(wineryRepository.findWineryWithRegion(wineryName, wineryRegion)).thenReturn(wineryWithRegionDto)

        val result: WineryWithRegionWithWineNameResponse? = wineryService.findWineryWithRegion(wineryName, wineryRegion)

        // then
        verify(wineryRepository, times(1)).findWineryWithRegion(wineryName, wineryRegion)
        assertEquals(result!!.wineryNameEnglish, wineryWithRegionDto.wineryNameEnglish)
        assertEquals(result.wineryNameKorean, wineryWithRegionDto.wineryNameKorean)
        assertEquals(result.wineryRegionNameKorean, wineryWithRegionDto.wineryRegionNameKorean)
        assertEquals(result.wineryRegionNameEnglish, wineryWithRegionDto.wineryRegionNameEnglish)
        assertEquals(result.wineryWineNameEnglish, wineryWithRegionDto.wineryWineNameEnglish)
        assertEquals(result.wineryWineNameKorean, wineryWithRegionDto.wineryWineNameKorean)
    }

    @Test
    @DisplayName("와이너리 다수 조회 테스트")
    fun findWineryWithRegionAll_Success_Test() {
        // given
        val wineryName = "name"
        val wineryRegion = "region"
        val wineryWithRegionDto = listOf(
            WineryWithRegionDto(
                wineryNameEnglish = "wineryNameEnglish",
                wineryNameKorean = "wineryNameKorean",
                wineryRegionNameEnglish = "wineryRegionNameEnglish",
                wineryRegionNameKorean = "wineryRegionNameKorean",
            )
        )

        // when
        `when`(wineryRepository.findWineryWithRegionAll(wineryName, wineryRegion)).thenReturn(wineryWithRegionDto)

        val result: List<WineryWithRegionResponse> = wineryService.findWineryWithRegionAll(wineryName, wineryRegion)

        // then
        verify(wineryRepository, times(1)).findWineryWithRegionAll(wineryName, wineryRegion)
        Assertions.assertThat(result.get(0).wineryNameEnglish).isEqualTo(wineryWithRegionDto.get(0).wineryNameEnglish)
        Assertions.assertThat(result.get(0).wineryNameKorean).isEqualTo(wineryWithRegionDto.get(0).wineryNameKorean)
        Assertions.assertThat(result.get(0).wineryRegionNameEnglish)
            .isEqualTo(wineryWithRegionDto.get(0).wineryRegionNameEnglish)
        Assertions.assertThat(result.get(0).wineryRegionNameKorean)
            .isEqualTo(wineryWithRegionDto.get(0).wineryRegionNameKorean)
    }
}