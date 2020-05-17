# Spring without Boot

A kotlin-jetty-spring example with a small set of runtime dependencies.

## Deployment environment

You'll need the below environment variables to both run and test the application.

```bash
export PORT=8080

export DATABASE_URL=postgresql://collective:collective@localhost:5432/spring_test
```

## Database Setup

The example app uses postgresql.

```bash
psql -c "create database spring_test;"
psql -c "create user collective with password 'collective';"
PGPASSWORD=collective psql --username=collective -h localhost spring_test
```

### Database Migrations

Use the below to migrate development or production databases. The test database will be automatically migration during test setup.

```bash
flyway -user=collective -password=collective -url="jdbc:postgresql://localhost:5432/spring_test" -locations=filesystem:databases/spring clean migrate
```

## Testing

Once you migrate the test database, build using Gradle.

```bash
./gradlew clean build
```