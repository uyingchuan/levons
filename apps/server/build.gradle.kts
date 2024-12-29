plugins {
  kotlin("jvm") version "1.9.25"
  kotlin("plugin.spring") version "1.9.25"
  id("org.springframework.boot") version "3.4.1"
  id("io.spring.dependency-management") version "1.1.7"
  kotlin("plugin.jpa") version "1.9.25"
  kotlin("kapt") version "1.9.25"
}

group = "cn.levons"
version = "0.0.1-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(17)
  }
}

repositories {
  mavenCentral()
}

dependencies {
  runtimeOnly("com.mysql:mysql-connector-j")
  runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-logging")
  implementation("org.springframework.boot:spring-boot-starter-data-redis")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("io.jsonwebtoken:jjwt-api:0.11.5")
  implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
  implementation("com.querydsl:querydsl-apt:5.1.0:jakarta")
  implementation("cn.dev33:sa-token-spring-boot3-starter:1.39.0")
  implementation("cn.dev33:sa-token-redis-jackson:1.39.0")
  implementation("org.apache.commons:commons-pool2")
  implementation("org.mindrot:jbcrypt:0.4")

  kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}

allOpen {
  annotation("jakarta.persistence.Entity")
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
  useJUnitPlatform()
}
