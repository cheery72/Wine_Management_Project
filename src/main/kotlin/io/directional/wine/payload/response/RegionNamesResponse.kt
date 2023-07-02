package io.directional.wine.payload.response

import io.directional.wine.payload.dto.RegionNamesDto

data class RegionNamesResponse(
    val regionNameEnglish: String,
    val regionNameKorean: String,
) {
    companion object {
        fun List<RegionNamesDto>.of(): List<RegionNamesResponse> {
            return map { dto ->
                RegionNamesResponse(
                    regionNameEnglish = dto.regionNameEnglish,
                    regionNameKorean = dto.regionNameKorean,
                )
            }
        }
    }
}