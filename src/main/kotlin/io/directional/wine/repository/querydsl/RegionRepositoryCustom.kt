package io.directional.wine.repository.querydsl

import io.directional.wine.dto.RegionDetailsDto
import io.directional.wine.dto.RegionNamesDto
import io.directional.wine.entity.Region

interface RegionRepositoryCustom {
    fun findByRegionTopList(regionId: Long): List<Region>

    fun findRegionDetails(regionName: String, parentRegion: String): RegionDetailsDto?

    fun findRegionsName(regionName: String, parentRegion: String): List<RegionNamesDto>
}