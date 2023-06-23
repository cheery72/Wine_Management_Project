package io.directional.wine.repository

import io.directional.wine.entity.Region
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RegionRepository : JpaRepository<Region, Long> {
    fun findByIdAndDeletedFalse(regionId: Long): Optional<Region>

}