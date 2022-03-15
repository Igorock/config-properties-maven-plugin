## config-properties-maven-plugin

Config Properties Maven Plugin allows to use properties from typesafe conf files as variables in pom.xml

## Usage

To use it in Maven clone this project locally, build with `mvn install` and insert the following code in `plugins` in `pom.xml` with your `configFile` and optional `configRoot`:

```xml
<plugin>
    <groupId>org.dziuba</groupId>
    <artifactId>config-properties-maven-plugin</artifactId>
    <version>1.0.1</version>
    <executions>
        <execution>
            <phase>initialize</phase>
            <goals>
                <goal>read-project-properties</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <configFile>src/main/resources/application.conf</configFile>
        <configRoot>org.company.datasource</configRoot>
    </configuration>
</plugin>
```

Let's assume we have the following `aplication.conf` file under `resources` folder:

```properties
org.company {
  datasource {
    url = "jdbc:mysql://localhost:3306/mydb"
    user = "user"
    password = "pass"
    driver = "com.mysql.jdbc.Driver"
  }
}
```

Now you can use properties from application.conf file for example in Liquibase plugin:

```xml
<plugin>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-maven-plugin</artifactId>
    <version>3.6.3</version>
    <configuration>
        <changeLogFile>src/main/migrations/changelog.xml</changeLogFile>
        <promptOnNonLocalDatabase>false</promptOnNonLocalDatabase>
        <url>${url}</url>
        <username>${user}</username>
        <password>${password}</password>
        <driver>${driver}</driver>
    </configuration>
    <executions>
        <execution>
            <phase>process-resources</phase>
            <goals>
                <goal>update</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
