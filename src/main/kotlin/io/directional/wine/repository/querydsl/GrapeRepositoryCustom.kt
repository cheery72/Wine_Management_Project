package io.directional.wine.repository.querydsl

import io.directional.wine.payload.response.GrapeDetailsWithWineNameResponse
import io.directional.wine.payload.response.GrapeNamesWithRegionsResponse

interface GrapeRepositoryCustom {

    fun findGrapeDetailsWithWineName(gradeName: String, gradeRegion: String): GrapeDetailsWithWineNameResponse?

    fun findGrapeNamesWithRegions(gradeName: String, gradeRegion: String): List<GrapeNamesWithRegionsResponse>
}