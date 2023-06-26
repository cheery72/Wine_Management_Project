package io.directional.wine.dto

import com.querydsl.core.annotations.QueryProjection

data class RegionDetailsDto @QueryProjection constructor(
    val regionNameEnglish: String,
    val regionNameKorean: String,
    val regionGrapeNameEnglish: String,
    val regionGrapeNameKorean: String,
    val regionWineryNameEnglish: String,
    val regionWineryNameKorean: String,
    val regionWineNameEnglish: String,
    val regionWineNameKorean: String,
    val regionId: Long,
)