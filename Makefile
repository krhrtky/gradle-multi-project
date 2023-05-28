.PHONY: db-up, migration, db-migrate, db-codegen, api-image

db-up:
	compose up db -d

migration: db-migrate db-codegen

db-migrate:
	docker compose run --rm sqldef mysqldef -h db -uroot -ppassword app --file=./volume/schema.sql

db-codegen:
	 ./gradlew backend:infrastructure:generateJooq

api-image:
	DB_URL=${DB_URL} \
	DB_USER=${DB_USER} \
	DB_PASSWORD=${DB_PASSWORD} \
	docker build -f backend/api/Dockerfile .
