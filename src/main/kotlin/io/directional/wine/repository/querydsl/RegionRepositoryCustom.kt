package io.directional.wine.repository.querydsl

import io.directional.wine.payload.dto.RegionDetailsDto
import io.directional.wine.payload.dto.RegionNamesDto
import io.directional.wine.payload.dto.RegionParentDto

interface RegionRepositoryCustom {
    fun findByRegionTopList(regionId: Long): List<RegionParentDto>

    fun findRegionDetails(regionName: String, parentRegion: String): RegionDetailsDto?

    fun findRegionsName(regionName: String, parentRegion: String): List<RegionNamesDto>
}