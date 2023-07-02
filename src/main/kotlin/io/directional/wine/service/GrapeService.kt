package io.directional.wine.service

import io.directional.wine.payload.response.GrapeDetailsWithWineNameResponse
import io.directional.wine.payload.request.CreateGrapeRequest
import io.directional.wine.entity.Grape
import io.directional.wine.exception.ClientException
import io.directional.wine.exception.ErrorCode
import io.directional.wine.payload.response.GrapeDetailsWithWineNameResponse.Companion.of
import io.directional.wine.payload.response.GrapeNamesWithRegionsResponse
import io.directional.wine.payload.response.GrapeNamesWithRegionsResponse.Companion.of
import io.directional.wine.repository.GrapeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GrapeService(
    private val grapeRepository: GrapeRepository
) {

    @Transactional
    fun createGrape(createGrapeRequest: CreateGrapeRequest) {

        val grape: Grape = Grape.toEntity(createGrapeRequest)

        grapeRepository.save(grape)
    }

    @Transactional
    fun updateGrape(grapeId: Long, createGrapeRequest: CreateGrapeRequest) {
        val grape: Grape = findGrape(grapeId)

        grape.setGrapeInfo(createGrapeRequest)
    }

    @Transactional
    fun deleteGrape(grapeId: Long) {
        val grape: Grape = findGrape(grapeId)

        grape.setDeleted()
    }

    fun findGrapeDetailsWithWineName(gradeName: String, gradeRegion: String): GrapeDetailsWithWineNameResponse? {
        return grapeRepository.findGrapeDetailsWithWineName(gradeName, gradeRegion)?.of()
    }

    fun findGrapeNamesWithRegions(grapeName: String, gradeRegion: String): List<GrapeNamesWithRegionsResponse> {
        return grapeRepository.findGrapeNamesWithRegions(grapeName, gradeRegion).of()
    }

    private fun findGrape(grapeId: Long): Grape {
        return grapeRepository.findByIdAndDeletedFalse(grapeId)
            .orElseThrow { ClientException(ErrorCode.NOT_FOUND_GRAPE) }
    }


}