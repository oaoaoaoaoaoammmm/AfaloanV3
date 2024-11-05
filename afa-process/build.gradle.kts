import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.CoverageUnit
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.spring") version "1.9.23"
	kotlin("plugin.jpa") version "1.9.23"
	kotlin("jvm") version "1.9.23"
	id("org.jetbrains.kotlinx.kover") version "0.8.0" // code coverage
	id("io.gitlab.arturbosch.detekt") version "1.23.6" // static analyse
}

group = "com.example"
version = "2.0.0"

java {
	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2023.0.3"

val openApiStarterVersion by extra("2.4.0")
val kLoggingVersion by extra("6.0.8")
val preliquibaseVersion by extra("1.5.1")
val mockitoKotlinVersion by extra("5.3.1")
val shedLockVersion by extra("5.12.0")

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	//cloud
	implementation("org.springframework.cloud:spring-cloud-starter-config")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

	// data base
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.liquibase:liquibase-core")
	runtimeOnly("org.postgresql:postgresql")
	implementation ("net.lbruun.springboot:preliquibase-spring-boot-starter:$preliquibaseVersion")

	// security
	implementation("org.springframework.boot:spring-boot-starter-security")

	// feign client
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("io.github.openfeign:feign-jackson:13.5")

	// kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	testImplementation("io.projectreactor:reactor-test")

	// log
	implementation("io.github.oshai:kotlin-logging-jvm:$kLoggingVersion")

	// shedlock
	implementation("net.javacrumbs.shedlock:shedlock-spring:$shedLockVersion")
	implementation("net.javacrumbs.shedlock:shedlock-provider-jdbc-template:$shedLockVersion")

	// open api
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openApiStarterVersion")
	implementation("org.springdoc:springdoc-openapi-starter-common:$openApiStarterVersion")

	// tests
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")

	// testcontainers
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

detekt {
	config.setFrom("$projectDir/../config/detekt/detekt.yml")
}

kover {
	reports {
		total {
			html {
				onCheck = true
			}
		}
		filters {
			excludes {
				classes(
					"*configurations*",
					"*exceptions*",
					"*utils*",
					"*AfaProcessApplication*"
				)
			}
		}
		verify {
			rule("Basic Line Coverage") {
				bound {
					minValue = 70
					coverageUnits = CoverageUnit.LINE
					aggregationForGroup = AggregationType.COVERED_PERCENTAGE
				}
			}

			rule("Branch Coverage") {
				bound {
					minValue = 70
					coverageUnits = CoverageUnit.BRANCH
					aggregationForGroup = AggregationType.COVERED_PERCENTAGE
				}
			}
		}
	}
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
