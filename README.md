# Big Star

kotlin-jetty-spring example with a small set of runtime dependencies

```
gradle -q :applications:bigstar-server:dependencies --configuration runtime

+--- com.fasterxml.jackson.core:jackson-core:2.5.4
+--- com.fasterxml.jackson.core:jackson-databind:2.5.4
+--- com.fasterxml.jackson.core:jackson-annotations:2.5.4
+--- org.springframework:spring-context:$spring_version -> 4.1.8.RELEASE
+--- org.springframework:spring-webmvc:4.1.8.RELEASE
+--- org.springframework:spring-jdbc:4.1.8.RELEASE
+--- javax.inject:javax.inject:1
+--- com.zaxxer:HikariCP:2.3.7
+--- org.apache.commons:commons-io:1.3.2
+--- org.eclipse.jetty:jetty-server:9.3.0.M2
+--- org.eclipse.jetty:jetty-servlet:9.3.0.M2
+--- org.slf4j:slf4j-api:1.7.5 -> 1.7.10
+--- org.slf4j:slf4j-simple:1.7.5
+--- mysql:mysql-connector-java:5.1.26
+--- javax.servlet:javax.servlet-api:3.1.0
\--- org.jetbrains.kotlin:kotlin-stdlib:1.0.0-rc-1036
```

## Deployment environment

The application assumes you're deploying to a platform as a service. You'll need the below environment variables to both run and test the application.

```
PORT=8080

VCAP_SERVICES={ \"services\": { \"p-mysql\": [ { \"credentials\": { \"jdbcUrl\": \"jdbc:mysql://localhost:3306/bigstar_test?user=root&password=\" } } ] } }
```

## Database Setup

The example app uses MySQL

```
mysql -uroot --execute="create database bigstar_development"

mysql -uroot --execute="create database bigstar_test"
```

### Database Migrations

Use the below to migrate development or production databases. The test database will be automatically migration during test setup.

```
gradle -Pflyway.url="jdbc:mysql://localhost:3306/bigstar_development?user=root&password=" flywayMigrate -i
```

## Testing

Once the test database is migrated, test using the below

```
gradle clean test
```