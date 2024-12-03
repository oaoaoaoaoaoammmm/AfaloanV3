import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.CoverageUnit
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    id("org.jetbrains.kotlinx.kover") version "0.8.0" // code coverage
    id("io.gitlab.arturbosch.detekt") version "1.23.6" // static analyse
}

group = "com.example"
version = "3.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

val openApiStarterVersion by extra("2.4.0")
val kLoggingVersion by extra("6.0.8")
val mockitoKotlinVersion by extra("5.3.1")

extra["springCloudVersion"] = "2023.0.3"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // cloud
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    // mail
    implementation("org.springframework.boot:spring-boot-starter-mail")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // log
    implementation("io.github.oshai:kotlin-logging-jvm:$kLoggingVersion")

    // open api
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openApiStarterVersion")
    implementation("org.springdoc:springdoc-openapi-starter-common:$openApiStarterVersion")

    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // kafka
    implementation("org.springframework.kafka:spring-kafka")

    // tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")

    // testcontainers
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    //testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.testcontainers:kafka")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

detekt {
    config.setFrom("$projectDir/../config/detekt/detekt.yml")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}