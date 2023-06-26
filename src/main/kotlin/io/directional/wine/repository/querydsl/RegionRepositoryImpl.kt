package io.directional.wine.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import io.directional.wine.dto.QRegionDetailsDto
import io.directional.wine.dto.RecursiveRegionDto
import io.directional.wine.dto.RegionDetailsDto
import io.directional.wine.entity.*
import jakarta.persistence.EntityManager

class RegionRepositoryImpl (
    private val jpaQueryFactory: JPAQueryFactory,
    private val qRegion: QRegion = QRegion.region,
    private val qWinery: QWinery = QWinery.winery,
    private val qGrapeShare: QGrapeShare = QGrapeShare.grapeShare,
    private val qGrape: QGrape = QGrape.grape,
    private val qWine: QWine = QWine.wine,
    private val entityManager: EntityManager,

) : RegionRepositoryCustom {

    override fun findByIdRecursiveRegions(regionId: Long): List<RecursiveRegionDto> {
        val nativeQuery = """
                WITH RECURSIVE RecursiveRegion (region_id, created_at, modified_at, deleted, name_english, name_korean, parent_id) AS (
                    SELECT * FROM region WHERE region_id = :regionId AND deleted = false
                    UNION ALL
                    SELECT R.region_id, R.created_at, R.modified_at, R.deleted, R.name_english, R.name_korean, R.parent_id
                    FROM RecursiveRegion RR
                    JOIN region R ON R.region_id = RR.parent_id
                )
                SELECT * FROM RecursiveRegion
            """

        val query = entityManager.createNativeQuery(nativeQuery)
        query.setParameter("regionId", regionId)

        val resultList = query.resultList
        return RecursiveRegionDto.fromRecursiveRegionDTO(resultList)

    }

    override fun findRegionDetails(regionName: String, parentRegion: String): RegionDetailsDto? {

        return jpaQueryFactory
            .select(
                QRegionDetailsDto(
                    qRegion.nameEnglish,
                    qRegion.nameKorean,
                    qGrape.nameEnglish,
                    qGrape.nameKorean,
                    qWinery.nameEnglish,
                    qWinery.nameKorean,
                    qWine.nameEnglish,
                    qWine.nameKorean,
                    qRegion.id
                )
            ).from(qRegion)
            .join(qRegion.grapeShare,qGrapeShare)
            .join(qGrapeShare.grape,qGrape)
            .join(qRegion.winery,qWinery)
            .join(qWinery.wine,qWine)
            .where(
                qRegion.deleted.isFalse.and(qGrape.deleted.isFalse
                .and(qWinery.deleted.isFalse.and(qWine.deleted.isFalse)))
                .and(qRegion.nameEnglish.eq(regionName).or(qRegion.nameKorean.eq(regionName)))
                .and(qRegion.parent.nameEnglish.eq(parentRegion).or(qRegion.parent.nameKorean.eq(parentRegion))))
            .orderBy(qRegion.nameEnglish.asc())
            .fetchFirst()
    }
}