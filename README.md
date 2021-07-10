# Play-JOOQ

[![Latest release](https://img.shields.io/github/v/release/PierreAdam/play-jooq)](https://github.com/PierreAdam/play-jooq/releases/latest)
![JDK](https://img.shields.io/badge/JDK-1.8+-blue.svg)
![Scala](https://img.shields.io/badge/Scala-2.12%20|%202.13-orange.svg)
[![Build Status](https://travis-ci.com/PierreAdam/play-jooq.svg?branch=master)](https://travis-ci.com/PierreAdam/play-jooq)
[![GitHub license](https://img.shields.io/github/license/PierreAdam/play-jooq)](https://raw.githubusercontent.com/PierreAdam/play-jooq/master/LICENSE)

Play-JOOQ is a Play Framework module that aims to integrate JOOQ in a Play project in Java or Scala.

The motivation behind Play-JOOQ was to be able to have a consistent way of using JOOQ across several projects
while keeping as much as possible the native Play Framework configuration and functionalities.

For more information on how to use JOOQ, checkout [JOOQ's documentation](https://www.jooq.org/).


## Build the module and local deployment

```shell
$> sbt +publishLocal
```


### Publishing to Sonatype

```shell
$> sbt +publishSigned
$> sbt sonatypeBundleRelease
```

## How to import the library

### With Sbt

```scala
libraryDependencies += "com.jackson42.play.jooq" % "play-jooq" % "21.07u1"
```

### With Maven

```xml

<dependency>
    <groupId>com.jackson42.play</groupId>
    <artifactId>play-datatables</artifactId>
    <version>21.07u1</version>
</dependency>
``` 

## Configuration

Play-JOOQ utilize a part of the existing Play Framework configuration.
As such, you will need to configure the standard Play database source. 

```
db {
  default {
    driver = "org.postgresql.Driver"
    url = "jdbc:postgresql://127.0.0.1/myDatabase"
    username = "postgres"
    password = "1234"
  }
  
  my-secondary-db {
    ...
  }
}
```

In addition, you will need to configure the extra configuration required for JOOQ.

```
jooq {
  default {
    database {
      # The type of the database is required for the code generation. A list of the available type can be found here:
      # https://www.jooq.org/doc/3.2/manual/code-generation/codegen-advanced/codegen-config-database/codegen-database-name/
      type = "org.jooq.meta.postgres.PostgresDatabase"
      
      # More information about the dialect: https://www.jooq.org/doc/3.2/manual/sql-building/dsl-context/sql-dialects/ 
      dialect = "POSTGRES"
      
      # This is the schema you want to use. This is dependant on the type of your database.
      # https://www.jooq.org/doc/3.2/manual/code-generation/codegen-configuration/
      inputSchema = "public"
    }
    
    target {
      # This is your source folder relative to the project root directory.
      directory = "app"
      
      # This is the package where your models should be generated.
      package = "models.generated"
    }
  }
  
  my-secondary-db {
    ...
  }
}
```

## How to use the library

In your controller you can easily inject `Jooq`.
Depending on if you're using scala or java, you might want to pay attention to the package.

Example in java.

```java
import org.jooq.DSLContext;
import org.jooq.Record;
import play.mvc.Controller;
import play.mvc.Results;
import javax.inject.Inject;

import com.jackson42.play.com.jackson42.play.jooq.java.Jooq;

public class MyController extends Controller {

    private final Jooq jooq;

    @Inject
    public MyController(final Jooq jooq) {
        this.jooq = jooq;
    }

    public Result myMethod() {
        final DSLContext defaultDbContext = this.jooq.getContext();
        final DSLContext mySecondaryDbContext = this.jooq.getContext("my-secondary-db");

        // Create a new record with a pre-set context.
        final MyTableRecord record = this.jooq.newRecord(Tables.MY_TABLE);
        record.setId(1);
        record.insert();

        final MyTableRecord retrievedRecord = defaultDbContext
                .select(Tables.MY_TABLE)
                .where(Tables.MY_TABLE.ID.eq(1))
                .fetchOne();

        return Results.ok();
    }
}
```

