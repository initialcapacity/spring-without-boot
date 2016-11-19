# Big Star

kotlin-jetty-spring example with a small set of runtime dependencies

```
gradle -q :applications:bigstar-server:dependencies --configuration runtime

+--- aopalliance:aopalliance:1.0
+--- com.fasterxml.jackson.core:jackson-annotations:2.5.4
+--- com.fasterxml.jackson.core:jackson-core:2.5.4
+--- com.fasterxml.jackson.core:jackson-databind:2.5.4
+--- com.zaxxer:HikariCP:2.3.7
+--- commons-codec:commons-codec:1.6
+--- commons-logging:commons-logging:1.2
+--- javax.inject:javax.inject:1
+--- javax.servlet:javax.servlet-api:3.1.0
+--- mysql:mysql-connector-java:5.1.26
+--- org.apache.httpcomponents:httpclient:4.3
+--- org.apache.httpcomponents:httpcore:4.3
+--- org.eclipse.jetty:jetty-http:9.3.0.M2
+--- org.eclipse.jetty:jetty-io:9.3.0.M2
+--- org.eclipse.jetty:jetty-security:9.3.0.M2
+--- org.eclipse.jetty:jetty-server:9.3.0.M2
+--- org.eclipse.jetty:jetty-servlet:9.3.0.M2
+--- org.eclipse.jetty:jetty-util:9.3.0.M2
+--- org.javassist:javassist:3.19.0-GA
+--- org.jetbrains.kotlin:kotlin-runtime:1.0.0-rc-1036
+--- org.jetbrains.kotlin:kotlin-stdlib:1.0.0-rc-1036
+--- org.slf4j:slf4j-api:1.7.10
+--- org.springframework:spring-aop:4.1.8.RELEASE
+--- org.springframework:spring-beans:4.1.8.RELEASE
+--- org.springframework:spring-context:4.1.8.RELEASE
+--- org.springframework:spring-core:4.1.8.RELEASE
+--- org.springframework:spring-expression:4.1.8.RELEASE
+--- org.springframework:spring-jdbc:4.1.8.RELEASE
+--- org.springframework:spring-tx:4.1.8.RELEASE
+--- org.springframework:spring-web:4.1.8.RELEASE
\--- org.springframework:spring-webmvc:4.1.8.RELEASE
```

## Deployment environment

The application assumes you're deploying to a platform as a service. You'll need the below environment variables to both run and test the application.

```
export PORT=8080

export VCAP_SERVICES='{ "services": { "p-mysql": [ { "credentials": { "jdbcUrl": "jdbc:mysql://localhost:3306/bigstar_test?user=root&password=" } } ] } }'
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
flyway -url="jdbc:mysql://localhost:3306/bigstar_development?user=root&password=" -locations=filesystem:databases/bigstar migrate
```

## Testing

Once the test database is migrated, test using the below

```
gradle clean test
```