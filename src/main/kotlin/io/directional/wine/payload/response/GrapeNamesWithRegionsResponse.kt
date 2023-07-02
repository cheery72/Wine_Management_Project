package io.directional.wine.payload.response

import io.directional.wine.payload.dto.GrapeNamesWithRegionsDto

data class GrapeNamesWithRegionsResponse(
    val grapeNameEnglish: String,
    val grapeNameKorean: String,
    val grapeRegionNameEnglish: String,
    val grapeRegionNameKorean: String
) {
    companion object {
        fun List<GrapeNamesWithRegionsDto>.of(): List<GrapeNamesWithRegionsResponse> {
            return map { dto ->
                GrapeNamesWithRegionsResponse(
                    grapeNameEnglish = dto.grapeNameEnglish,
                    grapeNameKorean = dto.grapeNameKorean,
                    grapeRegionNameEnglish = dto.grapeRegionNameEnglish,
                    grapeRegionNameKorean = dto.grapeRegionNameKorean,
                )
            }
        }
    }
}