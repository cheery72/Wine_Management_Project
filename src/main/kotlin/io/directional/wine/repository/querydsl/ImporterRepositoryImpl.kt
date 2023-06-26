package io.directional.wine.repository.querydsl

import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import io.directional.wine.dto.ImporterWithWineDto
import io.directional.wine.dto.QImporterWithWineDto
import io.directional.wine.entity.QImporter
import io.directional.wine.entity.QWine

class ImporterRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
    private val qImporter: QImporter = QImporter.importer,
    private val qWine: QWine = QWine.wine
) : ImporterRepositoryCustom {

    override fun findImporterNameWithWine(importerName: String): ImporterWithWineDto? {

        val importerNameExpression = Expressions.asString("%$importerName%")

        return jpaQueryFactory
            .select(
                QImporterWithWineDto
                    (
                        qImporter.name,
                        qWine.nameEnglish,
                        qWine.nameKorean
                    )
            ).from(qImporter)
            .join(qImporter.wines,qWine)
            .where(qImporter.deleted.isFalse.and(qWine.deleted.isFalse)
                .and(qImporter.name.like(importerNameExpression)))
            .orderBy(qImporter.name.asc())
            .fetchFirst()
    }
}