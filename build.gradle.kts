import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.2"
	id("io.spring.dependency-management") version "1.0.12.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "com.example.kafka"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.6")

	implementation("org.apache.kafka:kafka-clients:3.2.1")
	implementation("com.google.code.gson:gson:2.9.1")
	implementation("org.springframework.boot:spring-boot-starter-validation:2.6.6")
	implementation("org.apache.kafka:kafka-streams:3.0.0")

	// JSON serialization
	implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")

//	implementation("org.springframework.kafka:spring-kafka")
//	implementation("com.fasterxml.jackson.core:jackson-databind:2.9.6")
//	implementation("org.apache.kafka:kafka-streams:2.6.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

