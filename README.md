# Spring without Boot

A kotlin-jetty-spring example with a small set of runtime dependencies.

## Deployment environment

You'll need the below environment variables to both run and test the application.

```
export PORT=8080

export DATABASE_URL=mysql://collective:collective@localhost:5432/spring_test
```

## Database Setup

The example app uses postgresql

```bash
createdb
=# create database spring_test;
=# create user collective with password 'collective';
psql --username=collective --password -h localhost spring_test
```

### Database Migrations

Use the below to migrate development or production databases. The test database will be automatically migration during test setup.

```
flyway -user=collective -password=collective -url="jdbc:postgresql://localhost:5432/spring_test" -locations=filesystem:databases/spring clean migrate
```

## Testing

Once the test database is migrated, test using the below

```
./gradlew clean test
```