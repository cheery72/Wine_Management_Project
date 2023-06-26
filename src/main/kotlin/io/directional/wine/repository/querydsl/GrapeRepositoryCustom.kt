package io.directional.wine.repository.querydsl

import io.directional.wine.dto.GrapeDetailsWithWineNameDto
import io.directional.wine.dto.GrapeNamesWithRegions

interface GrapeRepositoryCustom {

    fun findGrapeDetailsWithWineName(gradeName: String, gradeRegion: String): GrapeDetailsWithWineNameDto

    fun findGrapeNamesWithRegions(gradeName: String, gradeRegion: String): List<GrapeNamesWithRegions>
}