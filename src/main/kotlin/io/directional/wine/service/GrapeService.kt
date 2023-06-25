package io.directional.wine.service

import io.directional.wine.dto.CreateGrapeRequest
import io.directional.wine.entity.Grape
import io.directional.wine.exception.ClientException
import io.directional.wine.exception.ErrorCode
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
    fun updateGrape(grapeId: Long, createGrapeRequest: CreateGrapeRequest){
        val grape: Grape = findGrape(grapeId)

        grape.setGrapeInfo(createGrapeRequest)
    }

    private fun findGrape(grapeId: Long): Grape {
        return grapeRepository.findByIdAndDeletedFalse(grapeId)
            .orElseThrow { ClientException(ErrorCode.NOT_FOUND_GRAPE) }
    }


}