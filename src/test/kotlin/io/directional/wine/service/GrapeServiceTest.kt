package io.directional.wine.service

import io.directional.wine.dto.CreateGrapeRequest
import io.directional.wine.entity.Grape
import io.directional.wine.repository.GrapeRepository
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
class GrapeServiceTest{

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
        assertEquals(savedGrape.nameEnglish,createGrapeRequest.grapeNameEnglish)
        assertEquals(savedGrape.nameKorean,createGrapeRequest.grapeNameKorean)
        assertEquals(savedGrape.deleted,false)
        Mockito.verify(grapeRepository).save(Mockito.any(Grape::class.java))
    }


    @Test
    @DisplayName("포도 품종 수정 성공 테스트")
    fun updateGrape_Success_Test() {
        // given
        val grapeId = 1L
        val grape = Grape(
            nameKorean = "알리아니코0",
            nameEnglish = "Aglianico0",
            acidity = 1,
            body = 2,
            sweetness = 3,
            tannin = 5,
        )

        // when
        Mockito.`when`(grapeRepository.findByIdAndDeletedFalse(grapeId)).thenReturn(Optional.of(grape))

        grapeService.updateGrape(grapeId, createGrapeRequest)

        // then
        assertEquals(grape.nameKorean,createGrapeRequest.grapeNameKorean)
        assertEquals(grape.nameEnglish,createGrapeRequest.grapeNameEnglish)
        assertEquals(grape.acidity,createGrapeRequest.grapeAcidity)
        assertEquals(grape.body,createGrapeRequest.grapeBody)
        assertEquals(grape.sweetness,createGrapeRequest.grapeSweetness)
        assertEquals(grape.tannin,createGrapeRequest.grapeTannin)
    }
}