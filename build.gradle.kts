plugins {
    `kotlin-dsl`
    `maven-publish`
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
