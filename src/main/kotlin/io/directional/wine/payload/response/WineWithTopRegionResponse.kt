package io.directional.wine.payload.response

import io.directional.wine.payload.dto.WineWithTopRegionDto

data class WineWithTopRegionResponse(
    val wineType: String,
    val wineKoreanName: String,
    val wineEnglishName: String,
    val wineTopRegion: List<String>,
) {
    companion object {
        fun fromWineWithTopRegionResponse(
            wineWithTopRegionDtoList: List<WineWithTopRegionDto?>,
            topRegions: HashMap<Long, List<String>>
        ): List<WineWithTopRegionResponse> {
            return wineWithTopRegionDtoList.stream()
                .map { w ->
                    WineWithTopRegionResponse(
                        wineType = w!!.wineType,
                        wineKoreanName = w.wineKoreanName,
                        wineEnglishName = w.wineEnglishName,
                        wineTopRegion = topRegions.getOrDefault(w.regionId, emptyList())
                    )
                }
                .toList()
        }

    }
}