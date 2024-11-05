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

group = "org.example"
version = "3.0.0"

java {
	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2023.0.3"

val openApiStarterVersion by extra("2.4.0")
val kLoggingVersion by extra("6.0.8")
val mockitoKotlinVersion by extra("5.3.1")
val minioVersion by extra("8.5.11")

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// security
	implementation("org.springframework.boot:spring-boot-starter-security")

	// kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// minio
	implementation("io.minio:minio:$minioVersion")

	// cloud
	implementation("org.springframework.cloud:spring-cloud-starter-config")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

	// log
	implementation("io.github.oshai:kotlin-logging-jvm:$kLoggingVersion")

	// open api
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openApiStarterVersion")
	implementation("org.springdoc:springdoc-openapi-starter-common:$openApiStarterVersion")

	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")

	// test container
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:minio")
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
					"*AfaFileApplication*"
				)
			}
		}
		verify {
			rule("Basic Line Coverage") {
				bound {
					minValue = 80
					coverageUnits = CoverageUnit.LINE
					aggregationForGroup = AggregationType.COVERED_PERCENTAGE
				}
			}

			rule("Branch Coverage") {
				bound {
					minValue = 80
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
