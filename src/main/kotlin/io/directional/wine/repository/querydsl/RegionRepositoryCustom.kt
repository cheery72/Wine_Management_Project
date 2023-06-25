package io.directional.wine.repository.querydsl

import io.directional.wine.dto.RecursiveRegionDto

interface RegionRepositoryCustom {

    fun findByIdRecursiveRegions(regionId: Long): List<RecursiveRegionDto>

}