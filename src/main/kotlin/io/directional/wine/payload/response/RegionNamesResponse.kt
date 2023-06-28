package io.directional.wine.payload.response

import com.querydsl.core.annotations.QueryProjection

data class RegionNamesResponse @QueryProjection constructor(
    val regionNameEnglish: String,
    val regionNameKorean: String,
)