import Build_gradle.v.kotlintestVersion
import Build_gradle.v.ktlintVersion
import Build_gradle.v.mockkVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.4.0"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version "2.4.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
}

group = "com.maha.checkout"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

object v {

    const val kotlintestVersion = "3.3.3"
    const val mockkVersion = "1.10.0"
    const val ktlintVersion = "0.40.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("io.kotlintest:kotlintest-extensions-spring:$kotlintestVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    check {
        dependsOn(ktlintCheck)
    }

    ktlint {
        verbose.set(true)
        version.set(ktlintVersion)
        kotlinScriptAdditionalPaths { include(fileTree(".")) }
        filter { exclude("build/**") }
    }
}
