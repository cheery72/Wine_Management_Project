package io.directional.wine.payload.dto

import com.querydsl.core.annotations.QueryProjection

data class RegionParentDto @QueryProjection constructor(
    val regionId: Long,
    val nameKorean: String,
    val nameEnglish: String,
    val parentId: Long?
)
