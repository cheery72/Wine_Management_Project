## 프로젝트 개요
- 와인 데이터를 관리하고 와인 농장, 와인 포도 품종 및 지역을 관리하는 프로젝트입니다.

## 기술 스택
- Kotlin, Spring Framework, Spring Boot, Spring MVC
- MySQL
- Spring Data JPA, Querydsl
- Gradle

## ERD


## 주요 기능
### 와인 조회
- 필터링: 와인 종류, 알코올 도수 범위, 와인의 가격 범위, 와인의 스타일, 와인의 등급, 지역
- 정렬: 와인 이름, 알코올 도수, 산도, 바디감, 단맛, 타닌, 와인의 점수, 와인의 가격
- 검색: 와인 이름
- 다수 조회 시: 와인의 종류, 와인 이름, 최상위 지역 이름
- 단일 조회 시: 와인의 종류, 와인 이름, 알코올 도수, 산도, 바디감, 단맛, 타닌, 와인의 점수, 와인의 가격, 와인의 스타일, 와인의 등급, 수입사 이름, 와이너리 이름, 와이너리 지역, 지역 이름 및 모든 상위 지역 이름, 와인의 향, 와인과 어울리는 음식, 포도 품종

### 와이너리 조회
- 필터링: 지역
- 정렬: 와이너리 이름
- 검색: 와이너리 이름
- 다수 조회 시: 와이너리 이름, 지역 이름
- 단일 조회 시: 와이너리 이름, 지역 이름, 와이너리의 와인

### 포도 품종 조회
- 필터링: 지역
- 정렬: 포도 품종 이름, 산도, 바디감, 단맛, 타닌
- 검색: 포도 품종 이름
- 다수 조회 시: 포도 품종 이름, 지역 이름
- 단일 조회 시: 포도 품종 이름, 산도, 바디감, 단맛, 타닌, 지역 이름, 포도 품종의 와인

### 지역 조회
- 필터링: 상위 지역
- 정렬: 지역 이름
- 검색: 지역 이름
- 다수 조회 시: 지역 이름
- 단일 조회 시: 지역 이름, 모든 상위 지역 이름, 포도 품종, 와이너리 이름, 와인 이름

### 수입사 조회
- 정렬: 수입사 이름
- 검색: 수입사 이름
- 다수 조회 시: 수입사 이름
- 단일 조회 시: 수입사 이름, 수입사의 와인