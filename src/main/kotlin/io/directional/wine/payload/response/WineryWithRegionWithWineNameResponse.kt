package io.directional.wine.payload.response

import io.directional.wine.payload.dto.WineryWithRegionWithWineNameDto

data class WineryWithRegionWithWineNameResponse(
    val wineryNameEnglish: String,
    val wineryNameKorean: String,
    val wineryRegionNameEnglish: String,
    val wineryRegionNameKorean: String,
    val wineryWineNameEnglish: String,
    val wineryWineNameKorean: String,
) {
    companion object {
        fun WineryWithRegionWithWineNameDto.of() = WineryWithRegionWithWineNameResponse(
            this.wineryNameEnglish,
            this.wineryNameKorean,
            this.wineryRegionNameEnglish,
            this.wineryRegionNameKorean,
            this.wineryWineNameEnglish,
            this.wineryWineNameKorean,
        )
    }
}