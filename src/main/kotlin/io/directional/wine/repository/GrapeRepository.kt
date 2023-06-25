package io.directional.wine.repository

import io.directional.wine.entity.Grape
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface GrapeRepository : JpaRepository<Grape, Long> {

    fun findByIdAndDeletedFalse(grapeId: Long): Optional<Grape>
}