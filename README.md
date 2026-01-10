# 로그 아카이빙 배치 (Spring Batch)

## 📖 개요

Spring Boot + Spring Batch로 대용량 로그를 주기적으로 아카이빙(이관) / 정리(purge) 하는 배치 프로젝트를 만드는 연습용 프로젝트입니다.
현재는 Hello Job 실행 및 배치 메타 테이블(BATCH_*) 생성/기록까지 성공한 상태

---

## 🛠 기술 스택
- Java 17
- Spring Boot 4.0.1
- Spring Batch 6.x
- MySQL
- Gradle


## 🔍 주요 기능(현재까지)
- helloJob 실행 (Tasklet 기반 Step)
- Job 실행 시 JobParameters(runAt)를 넣어 매 실행마다 새로운 실행 이력 생성
- Spring Batch 메타데이터 테이블(BATCH_*)에 JobInstance / JobExecution / StepExecution 실행 이력 저장
- 실행 결과 로그로 COMPLETED 확인


## 🧪 학습 포인트
- Spring Batch 기본 개념 이해(Job / Step / Tasklet / JobParameters / JobRepository)
- 배치 실행 이력 관리 메커니즘 이해
- MySQL 기반 배치 메타 스키마 자동 생성 설정 실습


## 🧪 앞으로 추가할 기능 (로드맵)
- Chunk 기반 배치(Reader/Processor/Writer)로 전환
- 로그 테이블 예시:api_log → api_log_archive로 이관(archive) / 일정 기간 지난 로그 purge(삭제)
- 운영형 실행 방식으로 변경 스케줄러 / REST 트리거 / 외부 실행 등
