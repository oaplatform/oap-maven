plugins {
    `java-platform`
    `maven-publish`
}

val interp4jVersion = project.property("interp4jVersion") as String
val lombokVersion = project.property("lombokVersion") as String

dependencies {
    constraints {
        api("com.google.code.gson:gson:2.13.2")
        api("org.apache.commons:commons-lang3:3.20.0")
        api("org.apache.commons:commons-math3:3.6.1")
        api("commons-io:commons-io:2.21.0")
        api("org.apache.commons:commons-collections4:4.5.0")
        api("org.apache.commons:commons-text:1.15.0")
        api("org.apache.commons:commons-compress:1.28.0")
        api("commons-codec:commons-codec:1.20.0")
        api("org.apache.commons:commons-configuration2:2.13.0")
        api("org.apache.commons:commons-exec:1.6.0")
        api("org.apache.commons:commons-csv:1.14.1")
        api("commons-net:commons-net:3.12.0")
        api("joda-time:joda-time:2.14.0")
        api("ch.qos.logback:logback-classic:1.5.21")
        api("ch.qos.logback:logback-core:1.5.21")
        api("org.slf4j:slf4j-api:2.0.17")
        api("com.google.guava:guava:33.5.0-jre")
        api("org.testng:testng:7.11.0")
        api("org.assertj:assertj-core:3.27.6")
        api("io.micrometer:micrometer-core:1.16.1")
        api("io.micrometer:micrometer-registry-prometheus:1.16.1")
        api("io.micrometer:micrometer-registry-prometheus-simpleclient:1.16.1")
        api("software.amazon.awssdk:s3:2.40.5")
        api("software.amazon.awssdk:s3-transfer-manager:2.40.5")
        api("software.amazon.awssdk:cognitoidentity:2.40.5")
        api("software.amazon.awssdk:cognitoidentityprovider:2.40.5")
        api("software.amazon.awssdk:secretsmanager:2.40.5")
        api("software.amazon.awssdk:route53:2.40.5")
        api("software.amazon.awssdk.crt:aws-crt:0.40.3")
        api("org.apache.velocity:velocity-engine-core:2.4.1")
        api("dev.khbd.interp4j:interp4j-core:$interp4jVersion")
        api("org.projectlombok:lombok:$lombokVersion")
    }
}

publishing {
    publications {
        create<MavenPublication>("javaPlatform") {
            from(components["javaPlatform"])
        }
    }
    repositories {
        maven {
            url = uri(project.findProperty("altRepositoryUri")
                ?: "https://maven.xenoss.net/repository/oap-maven/")
            val oapUsername = project.findProperty("oap.repository.user") as String?
            val oapPassword = project.findProperty("oap.repository.password") as String?
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
