package io.directional.wine.repository

import io.directional.wine.entity.Region
import io.directional.wine.repository.querydsl.RegionRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RegionRepository : JpaRepository<Region, Long>, RegionRepositoryCustom {
    fun findByIdAndDeletedFalse(regionId: Long): Optional<Region>

}