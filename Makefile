.PHONY: db-up, setup, db-migrate-local, db-migrate-remote,db-codegen, api-image

db-up:
	docker compose up db -d

setup: db-up db-migrate-local db-codegen

DB_MIGRATE_COMMAND = docker compose run --rm sqldef mysqldef

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
