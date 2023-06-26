package io.directional.wine.repository.querydsl

import io.directional.wine.dto.GrapeDetailsWithWineNameDto

interface GrapeRepositoryCustom {

    fun findGrapeDetailsWithWineName(gradeName: String, gradeRegion: String): GrapeDetailsWithWineNameDto
}