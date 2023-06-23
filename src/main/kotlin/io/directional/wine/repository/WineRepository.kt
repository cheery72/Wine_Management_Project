package io.directional.wine.repository

import io.directional.wine.entity.Wine
import org.springframework.data.jpa.repository.JpaRepository

interface WineRepository : JpaRepository<Wine, Long> {
}