package io.directional.wine.payload.dto

import com.querydsl.core.annotations.QueryProjection

data class RegionParentDto @QueryProjection constructor(
    val regionId: Long,
    val nameKorean: String,
    val nameEnglish: String,
    val parentId: Long?
) {
    companion object {
        fun fromRegionParentDto(
                regionId: Long,
                nameKorean: String,
                nameEnglish: String,
                parentId: Long?
        ): RegionParentDto {
            return RegionParentDto(
                    regionId = regionId,
                    nameKorean = nameKorean,
                    nameEnglish = nameEnglish,
                    parentId = parentId,
            )
        }
    }
}
