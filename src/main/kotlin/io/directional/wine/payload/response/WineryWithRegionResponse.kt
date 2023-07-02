package io.directional.wine.payload.response

import io.directional.wine.payload.dto.WineryWithRegionDto

data class WineryWithRegionResponse (
    val wineryNameEnglish: String,
    val wineryNameKorean: String,
    val wineryRegionNameEnglish: String,
    val wineryRegionNameKorean: String,
) {
    companion object {
        fun List<WineryWithRegionDto>.of(): List<WineryWithRegionResponse> {
            return map { dto ->
                WineryWithRegionResponse(
                    wineryNameEnglish = dto.wineryNameEnglish,
                    wineryNameKorean = dto.wineryNameKorean,
                    wineryRegionNameEnglish = dto.wineryRegionNameEnglish,
                    wineryRegionNameKorean = dto.wineryRegionNameKorean,
                )
            }
        }
    }
}