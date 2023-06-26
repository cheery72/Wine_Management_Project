package io.directional.wine.service

import io.directional.wine.payload.request.CreateRegionRequest
import io.directional.wine.payload.dto.RegionDetailsDto
import io.directional.wine.entity.Region
import io.directional.wine.repository.RegionRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(MockitoExtension::class, SpringExtension::class)
class RegionServiceTest {

    @Mock
    private lateinit var regionRepository: RegionRepository

    private lateinit var regionService: RegionService

    private val region = Region(
        nameKorean = "korea", nameEnglish = "english", parent = mock(Region::class.java)
    )

    private val createRegionRequest = CreateRegionRequest(
        regionNameKorean = "korea", regionNameEnglish = "english", regionParentId = 1L
    )

    @BeforeEach
    fun setup() {
        regionService = RegionService(regionRepository)
    }

    @Test
    @DisplayName("지역 생성 성공 테스트")
    fun createRegion_Success_Test() {

        // when
        Mockito.`when`(regionRepository.findById(createRegionRequest.regionParentId!!))
            .thenReturn(Optional.ofNullable(mock(Region::class.java)))

        regionService.createRegion(createRegionRequest)

        // then
        val regionCaptor: ArgumentCaptor<Region> = ArgumentCaptor.forClass(Region::class.java)
        Mockito.verify(regionRepository).save(regionCaptor.capture())
        val savedRegion: Region = regionCaptor.value
        assertEquals(savedRegion.nameKorean, createRegionRequest.regionNameKorean)
        assertEquals(savedRegion.deleted, false)
        Mockito.verify(regionRepository).save(Mockito.any(Region::class.java))
    }

    @Test
    @DisplayName("지역 수정 성공 테스트")
    fun updateRegion_Success_Test() {
        // given
        val regionId = 1L

        // when
        Mockito.`when`(regionRepository.findByIdAndDeletedFalse(regionId)).thenReturn(Optional.of(region))
        Mockito.`when`(regionRepository.findById(createRegionRequest.regionParentId!!)).thenReturn(Optional.of(region))

        regionService.updateRegion(regionId, createRegionRequest)

        // then
        assertEquals(region.nameKorean, createRegionRequest.regionNameKorean)
        assertEquals(region.nameEnglish, createRegionRequest.regionNameEnglish)
    }

    @Test
    @DisplayName("지역 삭제 성공 테스트")
    fun deleteRegion_Success_Test() {
        // given
        val regionId = 1L

        // when
        Mockito.`when`(regionRepository.findByIdAndDeletedFalse(regionId)).thenReturn(Optional.of(region))

        regionService.deleteRegion(regionId)

        // then
        Mockito.verify(regionRepository, Mockito.times(1)).findByIdAndDeletedFalse(regionId)
        assertTrue(region.deleted)
    }

    @Test
    @DisplayName("지역 단일 조회 성공 테스트")
    fun findRegionDetails_Success_Test() {
        // given
        val regionName = "regionName"
        val parentRegion = "parentRegion"
        val regionDetails = RegionDetailsDto(
            regionNameEnglish = "regionNameEnglish",
            regionNameKorean = "regionNameKorean",
            regionGrapeNameKorean = "regionGrapeNameKorean",
            regionGrapeNameEnglish = "regionGrapeNameEnglish",
            regionWineNameEnglish = "regionWineNameEnglish",
            regionWineNameKorean = "regionWineNameKorean",
            regionWineryNameEnglish = "regionWineryNameEnglish",
            regionWineryNameKorean = "regionWineryNameKorean",
            regionId = 1L
        )

        // when
        Mockito.`when`(regionRepository.findRegionDetails(regionName, parentRegion)).thenReturn(regionDetails)

        val result = regionRepository.findRegionDetails(regionName, parentRegion)

        // then
        Mockito.verify(regionRepository, Mockito.times(1)).findRegionDetails(regionName, parentRegion)
        assertEquals(regionDetails.regionNameEnglish, result!!.regionNameEnglish)
        assertEquals(regionDetails.regionNameKorean, result.regionNameKorean)
        assertEquals(regionDetails.regionGrapeNameKorean, result.regionGrapeNameKorean)
        assertEquals(regionDetails.regionGrapeNameEnglish, result.regionGrapeNameEnglish)
        assertEquals(regionDetails.regionWineNameEnglish, result.regionWineNameEnglish)
        assertEquals(regionDetails.regionWineNameKorean, result.regionWineNameKorean)
        assertEquals(regionDetails.regionWineryNameEnglish, result.regionWineryNameEnglish)
        assertEquals(regionDetails.regionWineryNameKorean, result.regionWineryNameKorean)
    }
}