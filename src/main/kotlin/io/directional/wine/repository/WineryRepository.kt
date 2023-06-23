package io.directional.wine.repository

import io.directional.wine.entity.Winery
import org.springframework.data.jpa.repository.JpaRepository

interface WineryRepository : JpaRepository<Winery, Long> {
}