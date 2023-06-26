package io.directional.wine.repository

import io.directional.wine.entity.Importer
import io.directional.wine.repository.querydsl.ImporterRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ImporterRepository : JpaRepository<Importer, Long>, ImporterRepositoryCustom {

    fun findByIdAndDeletedFalse(importerId: Long): Optional<Importer>

    fun findAllByNameLikeAndDeletedFalseOrderByNameAsc(importerName: String): List<Importer>
}