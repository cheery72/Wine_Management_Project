package io.directional.wine.repository

import io.directional.wine.entity.Winery
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface WineryRepository : JpaRepository<Winery, Long> {
    fun findByIdAndDeletedFalse(wineryId: Long): Optional<Winery>

}