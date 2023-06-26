package io.directional.wine.repository.querydsl

import io.directional.wine.dto.RecursiveRegionDto
import io.directional.wine.dto.RegionDetailsDto
import io.directional.wine.dto.RegionNamesDto

interface RegionRepositoryCustom {

    fun findByIdRecursiveRegions(regionId: Long): List<RecursiveRegionDto>

    fun findRegionDetails(regionName: String, parentRegion: String): RegionDetailsDto?

    fun findRegionsName(regionName: String, parentRegion: String): List<RegionNamesDto>
}