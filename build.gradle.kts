plugins {
    `kotlin-dsl`
    `maven-publish`
}

gradlePlugin {
    plugins {
        named("oap.java-convention") {
            displayName = "OAP Java Convention"
            description = "Configures Java 25, Checkstyle, Lombok, interp4j, TestNG, and maven-publish for OAP projects"
        }
    }
}

allprojects {
    repositories {
        maven {
            url = uri(findProperty("altRepositoryUri") ?: "https://artifacts.oaplatform.org/repository/oap-maven/")
        }
        mavenCentral()
    }
}

publishing {
    repositories {
        maven {
            url = uri(findProperty("altRepositoryUri") ?: "https://artifacts.oaplatform.org/repository/oap-maven/")
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
