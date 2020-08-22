import Build_gradle.Versions.grpcBom
import Build_gradle.Versions.proto
import Build_gradle.Versions.reactor
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.developmentOnly
import org.gradle.kotlin.dsl.exclude
import org.gradle.kotlin.dsl.idea
import org.gradle.kotlin.dsl.implementation
import org.gradle.kotlin.dsl.java
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.runtimeOnly
import org.gradle.kotlin.dsl.testImplementation
import org.gradle.kotlin.dsl.version
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    id("org.springframework.boot") version "2.3.3.RELEASE"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
    kotlin("plugin.jpa") version "1.3.72"
    id("com.google.protobuf") version "0.8.10"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("com.vladmihalcea:hibernate-types-52:2.9.3") // for json string type on JPA

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("io.projectreactor:reactor-test")

    implementation(platform("io.grpc:grpc-bom:$grpcBom"))
    implementation("io.grpc:grpc-protobuf")
    implementation("com.google.protobuf:protobuf-java:$proto")
    implementation("com.salesforce.servicelibs:reactor-grpc-stub:$reactor")
    implementation("io.github.lognet:grpc-spring-boot-starter:3.5.0")
    runtimeOnly("io.grpc:grpc-netty")

    protobuf(files("$projectDir/proto"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

object Versions {
    const val grpcBom: String = "1.28.1"
    const val proto: String = "3.10.0"
    const val reactor: String = "1.0.0"
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$proto"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcBom"
        }
        id("reactor") {
            artifact = "com.salesforce.servicelibs:reactor-grpc:$reactor"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                // Apply the "grpc" and "reactor" plugins whose specs are defined above, without options.
                id("grpc")
                id("reactor")
            }
        }
    }
}
