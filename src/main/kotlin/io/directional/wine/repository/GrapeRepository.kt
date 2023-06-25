package io.directional.wine.repository

import io.directional.wine.entity.Grape
import org.springframework.data.jpa.repository.JpaRepository

interface GrapeRepository : JpaRepository<Grape, Long> {
}