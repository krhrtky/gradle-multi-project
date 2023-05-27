.PHONY: db-up, migration, db-migrate, db-codegen

db-up:
	docker compose up db -d

migration:
	make db-migrate db-codegen

db-migrate:
	docker compose run --rm sqldef mysqldef -h db -uroot -ppassword app --file=./volume/schema.sql

db-codegen:
	 ./gradlew backend:infrastructure:generateJooq
