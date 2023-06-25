package io.directional.wine.repository

import io.directional.wine.entity.Wine
import io.directional.wine.repository.querydsl.WineRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface WineRepository : JpaRepository<Wine, Long>, WineRepositoryCustom{

    fun findByIdAndDeletedFalse(windId: Long): Optional<Wine>
}