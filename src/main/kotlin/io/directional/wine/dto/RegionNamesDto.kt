package io.directional.wine.dto

import com.querydsl.core.annotations.QueryProjection

data class RegionNamesDto @QueryProjection constructor(
    val regionNameEnglish: String,
    val regionNameKorean: String,
)