.PHONY: db-up, migration, db-migrate-local, db-migrate-remote,db-codegen, api-image

db-up:
	compose up db -d

migration: db-migrate-local db-codegen

DB_MIGRATE_COMMAND = docker compose run --rm sqldef mysqldef

db-migrate-local:
	${DB_MIGRATE_COMMAND} -h db -uroot -ppassword app --file=./volume/schema.sql

db-migrate-remote:
	${DB_MIGRATE_COMMAND} -h ${DB_HOST} -u ${DB_USER} -p ${DB_PASSWORD} ${DB_NAME} --file=./volume/schema.sql

db-codegen:
	 ./gradlew backend:infrastructure:generateJooq
