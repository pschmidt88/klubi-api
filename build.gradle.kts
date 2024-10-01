import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("jvm") version "2.0.10"
    kotlin("plugin.allopen") version "2.0.10"
    kotlin("plugin.noarg") version "2.0.10"
    id("io.quarkus")
}

group = "software.racoony"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project
val testContainersVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-mongodb-client")
    implementation("io.quarkus:quarkus-mongodb-panache-kotlin")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-config-yaml")

    implementation("io.reactivex.rxjava2:rxjava:2.2.21")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.+")

    testImplementation("io.quarkus:quarkus-junit5")

    testImplementation("io.rest-assured:rest-assured:5.2.0")
    testImplementation("io.rest-assured:kotlin-extensions:5.2.0")
    testImplementation("io.kotest:kotest-runner-junit5:5.5.0")
    testImplementation("io.kotest:kotest-assertions-core:5.5.0")

    testImplementation("org.testcontainers:testcontainers:${testContainersVersion}")
    testImplementation("org.testcontainers:mongodb:${testContainersVersion}")
    testImplementation("org.testcontainers:junit-jupiter:${testContainersVersion}")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.persistence.Entity")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

noArg {
    annotation("io.quarkus.mongodb.panache.common.MongoEntity")
    annotation("racoony.software.klubi.event_sourcing.DomainEvent")
}

kotlin {
    compilerOptions {
        apiVersion.set(KotlinVersion.KOTLIN_2_0)
        jvmTarget.set(JvmTarget.JVM_21)
        javaParameters.set(true)
    }
}
