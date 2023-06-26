package io.directional.wine.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import io.directional.wine.dto.GrapeDetailsWithWineNameDto
import io.directional.wine.dto.GrapeNamesWithRegions
import io.directional.wine.dto.QGrapeDetailsWithWineNameDto
import io.directional.wine.dto.QGrapeNamesWithRegions
import io.directional.wine.entity.*

class GrapeRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
    private val qGrape: QGrape = QGrape.grape,
    private val qWine: QWine = QWine.wine,
    private val qWineGrape: QWineGrape = QWineGrape.wineGrape,
    private val qGrapeShare: QGrapeShare = QGrapeShare.grapeShare,
    private val qRegion: QRegion = QRegion.region
): GrapeRepositoryCustom {

    override fun findGrapeDetailsWithWineName(gradeName: String, gradeRegion: String): GrapeDetailsWithWineNameDto {
        return jpaQueryFactory
            .select(
                QGrapeDetailsWithWineNameDto
                    (
                        qGrape.nameKorean,
                        qGrape.nameEnglish,
                        qGrape.acidity,
                        qGrape.body,
                        qGrape.sweetness,
                        qGrape.tannin,
                        qRegion.nameKorean,
                        qRegion.nameEnglish,
                        qWine.nameKorean,
                        qWine.nameEnglish
                    )
            ).from(qGrape)
            .join(qGrape.grapeShare,qGrapeShare)
            .join(qGrapeShare.region,qRegion)
            .join(qGrape.wineGrape,qWineGrape)
            .join(qWineGrape.wine,qWine)
            .where(qGrape.deleted.isFalse.and(qRegion.deleted.isFalse.and(qWine.deleted.isFalse)
                .and(qGrape.nameEnglish.eq(gradeName).or(qGrape.nameKorean.eq(gradeName)))
                .and(qRegion.nameEnglish.eq(gradeRegion).or(qRegion.nameKorean.eq(gradeRegion)))))
            .orderBy(qRegion.nameEnglish.asc())
            .fetchFirst()

    }

    override fun findGrapeNamesWithRegions(gradeName: String, gradeRegion: String): List<GrapeNamesWithRegions> {
        return jpaQueryFactory
            .select(
                QGrapeNamesWithRegions
                    (
                    qGrape.nameEnglish,
                    qGrape.nameKorean,
                    qRegion.nameEnglish,
                    qRegion.nameKorean
                )
            ).from(qGrape)
            .join(qGrape.grapeShare,qGrapeShare)
            .join(qGrapeShare.region,qRegion)
            .where(qGrape.deleted.isFalse.and(qRegion.deleted.isFalse
                .and(qGrape.nameEnglish.eq(gradeName).or(qGrape.nameKorean.eq(gradeName)))
                .and(qRegion.nameEnglish.eq(gradeRegion).or(qRegion.nameKorean.eq(gradeRegion)))))
            .orderBy(qRegion.nameEnglish.asc())
            .fetch()
    }
}