plugins {
    java
    checkstyle
    `maven-publish`
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
    withSourcesJar()
}

val interp4jVersion = project.property("interp4jVersion") as String
val lombokVersion = project.property("lombokVersion") as String

dependencies {
    "compileOnly"("org.projectlombok:lombok:$lombokVersion")
    "annotationProcessor"("org.projectlombok:lombok:$lombokVersion")
    "compileOnly"("dev.khbd.interp4j:interp4j-core:$interp4jVersion")
    "annotationProcessor"("dev.khbd.interp4j:interp4j-processor:$interp4jVersion")
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.addAll(
        listOf(
            "-parameters",
            "-Xlint:unchecked",
            "--add-modules", "java.xml,java.compiler",
            "-proc:full",
            "-Xplugin:interp4j",
            "-J--add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",
            "-J--add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED",
            "-J--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED",
            "-J--add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED"
        )
    )
}

checkstyle {
    toolVersion = project.property("checkstyleVersion") as String
    config = resources.text.fromUri(
        "https://raw.githubusercontent.com/oaplatform/oap-maven/master/.idea/checkstyle.xml"
    )
}

tasks.withType<Test>().configureEach {
    useTestNG()
    jvmArgs(
        "-ea",
        "--add-opens=java.base/java.lang=ALL-UNNAMED",
        "--add-opens=java.base/java.math=ALL-UNNAMED",
        "--add-opens=java.base/java.util=ALL-UNNAMED",
        "--add-opens=java.base/java.util.stream=ALL-UNNAMED",
        "--add-opens=java.base/java.util.concurrent=ALL-UNNAMED",
        "--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED",
        "--add-opens=java.base/java.net=ALL-UNNAMED",
        "--add-opens=java.base/java.text=ALL-UNNAMED",
        "--add-opens=java.base/java.io=ALL-UNNAMED",
        "--add-opens=java.base/java.nio=ALL-UNNAMED",
        "--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED",
        "--add-opens=java.sql/java.sql=ALL-UNNAMED"
    )
    exclude("**/*Performance*", "**/*Perf*")
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
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
