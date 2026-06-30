plugins {
    `kotlin-dsl`
    `maven-publish`
    id("com.github.gmazzo.buildconfig") version "5.5.2"
}

gradlePlugin {
    plugins {
        named("oap.java-convention") {
            displayName = "OAP Java Convention"
            description = "Configures Java 25, Checkstyle, Lombok, interp4j, TestNG, and maven-publish for OAP projects"
        }
    }
}

buildConfig {
    packageName("oap.maven")
    buildConfigField("String", "INTERP4J_VERSION", "\"${property("interp4jVersion")}\"")
    buildConfigField("String", "LOMBOK_VERSION", "\"${property("lombokVersion")}\"")
    buildConfigField("String", "CHECKSTYLE_VERSION", "\"${property("checkstyleVersion")}\"")
}

allprojects {
    repositories {
        maven {
            url = uri(findProperty("altRepositoryUri") ?: "https://maven.xenoss.net/repository/oap-maven/")
        }
        mavenCentral()
    }
}

publishing {
    repositories {
        maven {
            url = uri(findProperty("altRepositoryUri") ?: "https://maven.xenoss.net/repository/oap-maven/")
            val oapUsername = findProperty("oap.repository.user") as String?
            val oapPassword = findProperty("oap.repository.password") as String?
                ?: System.getenv("CODEARTIFACT_AUTH_TOKEN")
            if (oapUsername != null && oapPassword != null) {
                credentials {
                    username = oapUsername
                    password = oapPassword
                }
            }
        }
    }
}
