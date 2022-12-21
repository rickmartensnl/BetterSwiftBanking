val kotlin_version: String by project

plugins {
	application
	kotlin("jvm") version "1.7.22"
}

group = "nl.rickmartens"
version = "0.0.1"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
	
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}