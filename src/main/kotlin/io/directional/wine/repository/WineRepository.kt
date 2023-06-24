package io.directional.wine.repository

import io.directional.wine.entity.Wine
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface WineRepository : JpaRepository<Wine, Long> {

    fun findByIdAndDeletedFalse(windId: Long): Optional<Wine>
}