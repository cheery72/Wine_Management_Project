package io.directional.wine.repository

import io.directional.wine.entity.Winery
import io.directional.wine.repository.querydsl.WineryRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface WineryRepository : JpaRepository<Winery, Long>, WineryRepositoryCustom {
    fun findByIdAndDeletedFalse(wineryId: Long): Optional<Winery>

}