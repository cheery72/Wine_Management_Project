package io.directional.wine.repository

import io.directional.wine.entity.Grape
import io.directional.wine.repository.querydsl.GrapeRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface GrapeRepository : JpaRepository<Grape, Long>, GrapeRepositoryCustom {

    fun findByIdAndDeletedFalse(grapeId: Long): Optional<Grape>
}