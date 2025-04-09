import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.serialization") version "2.1.20"
    kotlin("plugin.allopen") version "2.1.20"
    kotlin("plugin.noarg") version "2.1.20"
    alias(libs.plugins.quarkus)
}

group = "software.racoony"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(enforcedPlatform(libs.quarkus.bom))
    implementation(libs.quarkus.arc)
    implementation(libs.quarkus.kotlin)
    implementation(libs.quarkus.rest.jackson)
    implementation(libs.quarkus.mongodb.client)
    implementation(libs.quarkus.mongodb.panache.kotlin)
    implementation(libs.quarkus.config.yaml)

    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    implementation("org.mongodb:bson-kotlinx:5.4.0")

    implementation("io.reactivex.rxjava2:rxjava:2.2.21")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

//    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.+")

    implementation(libs.arrow.core)
    implementation(libs.arrow.fx.coroutines)
    implementation(libs.arrow.resilience)


    testImplementation("io.rest-assured:rest-assured:5.2.0")
    testImplementation("io.rest-assured:kotlin-extensions:5.2.0")
    testImplementation("io.kotest:kotest-runner-junit5:5.5.0")
    testImplementation("io.kotest:kotest-assertions-core:5.5.0")

    testImplementation(libs.testcontainers)
    testImplementation(libs.testcontainers.mongodb)
    testImplementation(libs.testcontainers.junit)

    testImplementation(libs.quarkus.junit5)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.1")
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
