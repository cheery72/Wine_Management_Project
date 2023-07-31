package io.directional.wine.payload.response

import io.directional.wine.payload.dto.RegionParentDto
import io.directional.wine.payload.dto.WineWithTopRegionDto
import java.util.concurrent.ConcurrentHashMap

data class WineWithTopRegionResponse(
    val wineType: String,
    val wineKoreanName: String,
    val wineEnglishName: String,
    val wineTopRegion: List<String>,
) {
    companion object {
        private fun findParentRegionList(
                regionId: Long,
                topRegions: ConcurrentHashMap<Long,RegionParentDto>,
                regions: MutableList<String>
        ): List<String> {
            val parent = topRegions.getValue(regionId)
            regions.add(parent.nameKorean)

            if(parent.parentId != null) {
                findParentRegionList(parent.parentId,topRegions,regions)
            }

            return regions
        }

        fun fromWineWithTopRegionResponse(
            wineWithTopRegionDtoList: List<WineWithTopRegionDto>,
            topRegions: ConcurrentHashMap<Long,RegionParentDto>,
        ): List<WineWithTopRegionResponse> {

            return wineWithTopRegionDtoList.stream()
                    .map { w ->
                        WineWithTopRegionResponse(
                                wineType = w.wineType,
                                wineKoreanName = w.wineKoreanName,
                                wineEnglishName = w.wineEnglishName,
                                wineTopRegion = findParentRegionList(w.regionId,topRegions, mutableListOf())
                        )
                    }
                    .toList()
        }
    }
}