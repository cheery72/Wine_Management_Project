package io.directional.wine.service

import io.directional.wine.entity.Grape
import io.directional.wine.payload.dto.GrapeDetailsWithWineNameDto
import io.directional.wine.payload.dto.GrapeNamesWithRegionsDto
import io.directional.wine.payload.request.CreateGrapeRequest
import io.directional.wine.payload.response.GrapeNamesWithRegionsResponse
import io.directional.wine.repository.GrapeRepository
import org.assertj.core.api.Assertions
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
class GrapeServiceTest {

    @Mock
    private lateinit var grapeRepository: GrapeRepository

    private lateinit var grapeService: GrapeService

    private val createGrapeRequest = CreateGrapeRequest(
        grapeNameKorean = "알리아니코",
        grapeNameEnglish = "Aglianico",
        grapeAcidity = 4,
        grapeBody = 5,
        grapeSweetness = 1,
        grapeTannin = 4,
    )

    private val grape = Grape(
        nameKorean = "알리아니코0",
        nameEnglish = "Aglianico0",
        acidity = 1,
        body = 2,
        sweetness = 3,
        tannin = 5,
    )

    @BeforeEach
    fun setup() {
        grapeService = GrapeService(grapeRepository)
    }

    @Test
    @DisplayName("포도 품종 생성 성공 테스트")
    fun createGrape_Success_Test() {
        // when
        grapeService.createGrape(createGrapeRequest)

        // then
        val grapeCaptor: ArgumentCaptor<Grape> = ArgumentCaptor.forClass(Grape::class.java)
        Mockito.verify(grapeRepository).save(grapeCaptor.capture())
        val savedGrape: Grape = grapeCaptor.value
        assertEquals(savedGrape.nameEnglish, createGrapeRequest.grapeNameEnglish)
        assertEquals(savedGrape.nameKorean, createGrapeRequest.grapeNameKorean)
        assertEquals(savedGrape.deleted, false)
        Mockito.verify(grapeRepository).save(Mockito.any(Grape::class.java))
    }


    @Test
    @DisplayName("포도 품종 수정 성공 테스트")
    fun updateGrape_Success_Test() {
        // given
        val grapeId = 1L

        // when
        Mockito.`when`(grapeRepository.findByIdAndDeletedFalse(grapeId)).thenReturn(Optional.of(grape))

        grapeService.updateGrape(grapeId, createGrapeRequest)

        // then
        assertEquals(grape.nameKorean, createGrapeRequest.grapeNameKorean)
        assertEquals(grape.nameEnglish, createGrapeRequest.grapeNameEnglish)
        assertEquals(grape.acidity, createGrapeRequest.grapeAcidity)
        assertEquals(grape.body, createGrapeRequest.grapeBody)
        assertEquals(grape.sweetness, createGrapeRequest.grapeSweetness)
        assertEquals(grape.tannin, createGrapeRequest.grapeTannin)
    }

    @Test
    @DisplayName("포도 품종 삭제 성공 테스트")
    fun deleteGrape_Success_Test() {
        // given
        val grapeId = 1L

        // when
        Mockito.`when`(grapeRepository.findByIdAndDeletedFalse(grapeId)).thenReturn(Optional.of(grape))

        grapeService.deleteGrape(grapeId)

        // then
        Mockito.verify(grapeRepository, Mockito.times(1)).findByIdAndDeletedFalse(grapeId)
        assertTrue(grape.deleted)

    }

    @Test
    @DisplayName("포도 품종 단일 조회 성공 테스트")
    fun findGrapeDetailsWithWineName_Success_Test() {
        // given
        val grapeName = "grapeName"
        val grapeRegion = "grapeRegion"
        val grapeDetailsWithWineNameDto = GrapeDetailsWithWineNameDto(
            grapeNameKorean = "grapeNameKorean",
            grapeNameEnglish = "grapeNameEnglish",
            grapeAcidity = 1,
            grapeBody = 2,
            grapeSweetness = 3,
            grapeTannin = 4,
            grapeRegionNameKorean = "grapeRegionNameKorean",
            grapeRegionNameEnglish = "grapeRegionNameEnglish",
            grapeWineNameKorean = "grapeWineNameKorean",
            grapeWineNameEnglish = "grapeWineNameEnglish"
        )

        // when
        Mockito.`when`(grapeRepository.findGrapeDetailsWithWineName(grapeName, grapeRegion))
            .thenReturn(grapeDetailsWithWineNameDto)

        val result = grapeService.findGrapeDetailsWithWineName(grapeName, grapeRegion)

        // then
        Mockito.verify(grapeRepository, Mockito.times(1)).findGrapeDetailsWithWineName(grapeName, grapeRegion)
        assertEquals(grapeDetailsWithWineNameDto.grapeNameEnglish, result!!.grapeNameEnglish)
        assertEquals(grapeDetailsWithWineNameDto.grapeNameKorean, result.grapeNameKorean)
        assertEquals(grapeDetailsWithWineNameDto.grapeAcidity, result.grapeAcidity)
        assertEquals(grapeDetailsWithWineNameDto.grapeBody, result.grapeBody)
        assertEquals(grapeDetailsWithWineNameDto.grapeSweetness, result.grapeSweetness)
        assertEquals(grapeDetailsWithWineNameDto.grapeTannin, result.grapeTannin)
        assertEquals(grapeDetailsWithWineNameDto.grapeRegionNameKorean, result.grapeRegionNameKorean)
        assertEquals(grapeDetailsWithWineNameDto.grapeRegionNameEnglish, result.grapeRegionNameEnglish)
        assertEquals(grapeDetailsWithWineNameDto.grapeWineNameKorean, result.grapeWineNameKorean)
        assertEquals(grapeDetailsWithWineNameDto.grapeWineNameEnglish, result.grapeWineNameEnglish)
    }

    @Test
    @DisplayName("포도 품종 다수 조회 테스트")
    fun findGrapeNamesWithRegions_Success_Test() {
        // given
        val grapeName = "grapeName"
        val grapeRegion = "grapeRegion"
        val grapeNamesWithRegionDtos = listOf(
            GrapeNamesWithRegionsDto(
                grapeNameEnglish = "grapeNameEnglish",
                grapeNameKorean = "grapeNameKorean",
                grapeRegionNameEnglish = "grapeRegionNameEnglish",
                grapeRegionNameKorean = "grapeRegionNameKorean",
            )
        )

        // when
        Mockito.`when`(grapeRepository.findGrapeNamesWithRegions(grapeName, grapeRegion))
            .thenReturn(grapeNamesWithRegionDtos)

        val result: List<GrapeNamesWithRegionsResponse> = grapeService.findGrapeNamesWithRegions(grapeName, grapeRegion)

        // then
        Mockito.verify(grapeRepository, Mockito.times(1)).findGrapeNamesWithRegions(grapeName, grapeRegion)
        Assertions.assertThat(result.get(0).grapeNameEnglish).isEqualTo(grapeNamesWithRegionDtos.get(0).grapeNameEnglish)
        Assertions.assertThat(result.get(0).grapeNameKorean).isEqualTo(grapeNamesWithRegionDtos.get(0).grapeNameKorean)
        Assertions.assertThat(result.get(0).grapeRegionNameEnglish)
            .isEqualTo(grapeNamesWithRegionDtos.get(0).grapeRegionNameEnglish)
        Assertions.assertThat(result.get(0).grapeRegionNameKorean)
            .isEqualTo(grapeNamesWithRegionDtos.get(0).grapeRegionNameKorean)
    }
}