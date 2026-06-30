# oap-maven

The parent Gradle OAP project. It ships two artifacts:

- **`oap.java-convention`** — a Gradle convention plugin that configures Java 25, Checkstyle, Lombok, interp4j, TestNG, and maven-publish for all OAP child projects.
- **`oap-dependencies`** — a Java Platform (BOM) that manages dependency versions for all OAP child projects.

## Applying the convention plugin

In child projects, declare the plugin repository in `settings.gradle.kts`:

```kotlin
pluginManagement {
    repositories {
        maven { url = uri("https://maven.xenoss.net/repository/oap-maven/") }
        gradlePluginPortal()
    }
}
```

Then apply the plugin in `build.gradle.kts`:

```kotlin
plugins {
    id("oap.java-convention") version "25.2.0"
}
```

## Importing the BOM

```kotlin
dependencies {
    implementation(platform("oap:oap-dependencies:25.2.0"))
    implementation("org.apache.commons:commons-lang3") // version managed by BOM
}
```

## Build commands

```bash
# Build the plugin project
./gradlew build

# Publish to repository (requires oap.repository.user / oap.repository.password
# Gradle properties, or CODEARTIFACT_AUTH_TOKEN env var)
./gradlew publish

# Override the target repository
./gradlew publish -PaltRepositoryUri=https://your-repo/
```

On Windows use `gradlew.bat` instead of `./gradlew`.

Requires JDK 25 and Gradle 9.5.1+ (via wrapper).

## What the convention plugin configures

- Java 25 toolchain with sources JAR
- Compiler: `-parameters`, `-Xlint:unchecked`, `--add-modules java.xml,java.compiler`, `-proc:full`, interp4j compiler plugin + all required `--add-exports`
- Annotation processors: Lombok 1.18.46, interp4j-processor 2.0.0\_jre21
- Checkstyle 10.24.0 (rules fetched from `.idea/checkstyle.xml` in this repo)
- TestNG test runner with 12 `--add-opens` flags, `-ea`, excludes `**/*Performance*` and `**/*Perf*`
- `maven-publish` with credentials via `oap.repository.user` / `oap.repository.password` Gradle properties or `CODEARTIFACT_AUTH_TOKEN` env var
