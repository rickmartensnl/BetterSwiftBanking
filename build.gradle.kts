val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val kmongo_version: String by project

plugins {
	application
	kotlin("jvm") version "1.7.22"
	id("io.ktor.plugin") version "2.1.3"
	id("org.jetbrains.kotlin.plugin.serialization") version "1.7.22"
}

group = "nl.voedselen"
version = "0.0.1"
application {
	mainClass.set("io.ktor.server.netty.EngineMain")
	
	val isDevelopment: Boolean = project.ext.has("development")
	applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
	implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-auto-head-response-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-cors-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-call-id-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-request-validation:$ktor_version")
	implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-status-pages:$ktor_version")
	implementation("io.ktor:ktor-server-host-common-jvm:2.1.3")
	implementation("ch.qos.logback:logback-classic:$logback_version")
	implementation("org.litote.kmongo:kmongo:$kmongo_version")
	implementation("org.litote.kmongo:kmongo-coroutine:$kmongo_version")
	implementation("org.litote.kmongo:kmongo-id-serialization:$kmongo_version")
	implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
	
	testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}