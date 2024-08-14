plugins {
	id("org.springframework.boot") version "3.3.1"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("plugin.jpa") version "1.9.24"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
}

group = "com.github.ryamal"
version = "0.0.1-SNAPSHOT"

kotlin {
	jvmToolchain(21)
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	// implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("mysql:mysql-connector-java:8.0.32")
	implementation("com.zaxxer:HikariCP")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("com.h2database:h2")
	implementation("org.hibernate.orm:hibernate-community-dialects:6.5.2.Final")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	// testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito:mockito-core:3.11.2")
	testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
