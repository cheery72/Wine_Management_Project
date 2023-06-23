package io.directional.wine.repository

import io.directional.wine.entity.Importer
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ImporterRepository : JpaRepository<Importer, Long> {

    fun findByIdAndDeletedFalse(importerId: Long): Optional<Importer>

}