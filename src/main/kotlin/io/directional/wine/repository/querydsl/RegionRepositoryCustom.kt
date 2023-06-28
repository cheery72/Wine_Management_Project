package io.directional.wine.repository.querydsl

import io.directional.wine.payload.dto.RegionDetailsDto
import io.directional.wine.payload.response.RegionNamesResponse
import io.directional.wine.entity.Region

interface RegionRepositoryCustom {
    fun findByRegionTopList(regionId: Long): List<Region>

    fun findRegionDetails(regionName: String, parentRegion: String): RegionDetailsDto?

    fun findRegionsName(regionName: String, parentRegion: String): List<RegionNamesResponse>
}