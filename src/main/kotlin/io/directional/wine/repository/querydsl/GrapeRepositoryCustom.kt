package io.directional.wine.repository.querydsl

import io.directional.wine.payload.dto.GrapeDetailsWithWineNameDto
import io.directional.wine.payload.dto.GrapeNamesWithRegionsDto

interface GrapeRepositoryCustom {

    fun findGrapeDetailsWithWineName(gradeName: String, gradeRegion: String): GrapeDetailsWithWineNameDto?

    fun findGrapeNamesWithRegions(gradeName: String, gradeRegion: String): List<GrapeNamesWithRegionsDto>
}