package io.directional.wine.service

import io.directional.wine.dto.CreateRegionRequest
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

    @BeforeEach
    fun setup() {
        regionService = RegionService(regionRepository)
    }

    @Test
    @DisplayName("지역 생성 성공 테스트")
    fun createRegion_Success_Test() {
        // given
        val createRegionRequest = CreateRegionRequest(
            regionNameKorean = "korea",
            regionNameEnglish = "english",
            regionParentId = 1L
        )

        // when
        Mockito.`when`(regionRepository.findById(createRegionRequest.regionParentId!!))
            .thenReturn(Optional.ofNullable(mock(Region::class.java)))

        regionService.createRegion(createRegionRequest)

        // then
        val regionCaptor: ArgumentCaptor<Region> = ArgumentCaptor.forClass(Region::class.java)
        Mockito.verify(regionRepository).save(regionCaptor.capture())
        val savedRegion: Region = regionCaptor.value
        assertEquals(savedRegion.nameKorean,createRegionRequest.regionNameKorean)
        assertEquals(savedRegion.deleted,false)
        Mockito.verify(regionRepository).save(Mockito.any(Region::class.java))
    }
}