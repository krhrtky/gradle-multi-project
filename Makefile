.PHONY: db-up, setup, db-migrate-local, db-migrate-remote,db-codegen, api-image, start-backend, start-frontend, build-backend, build-frontend, setup-frontend, test-frontend

db-up:
	docker compose up db -d

setup: setup-backend setup-frontend

setup-backend: db-up db-migrate-local db-codegen

setup-frontend: open-api-client-gen install-frontend

DB_MIGRATE_COMMAND = docker compose run --rm sqldef mysqldef

dry-db-migrate-local:
	${DB_MIGRATE_COMMAND} -h db -uroot -ppassword app --file=./volume/schema.sql --dry-run

dry-db-migrate-remote:
	${DB_MIGRATE_COMMAND} -h ${DB_HOST} -u ${DB_USER} -p ${DB_PASSWORD} ${DB_NAME} --file=./volume/schema.sql --dry-run

db-migrate-local:
	${DB_MIGRATE_COMMAND} -h db -uroot -ppassword app --file=./volume/schema.sql

db-migrate-remote:
	${DB_MIGRATE_COMMAND} -h ${DB_HOST} -u ${DB_USER} -p ${DB_PASSWORD} ${DB_NAME} --file=./volume/schema.sql

db-codegen:
	./gradlew backend:infrastructure:generateJooq

graphql-codegen:
	./gradlew backend:api:generateJava

open-api-schema-gen:
	./gradlew backend:api:generateOpenApiDocs

open-api-client-gen: open-api-schema-gen
	cd front/app && pnpm orval --config ./orval.config.ts &&  cd ../../

start-backend:
	./gradlew backend:api:bootRun

start-frontend:
	pnpm recursive run dev --filter front/app

build-backend:
	./gradlew backend:api:bootJar

install-frontend:
	pnpm recursive install

build-frontend:
	pnpm recursive run build --filter front/app

test-frontend:
	pnpm recursive test
