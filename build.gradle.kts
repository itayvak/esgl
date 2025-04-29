plugins {
    kotlin("jvm") version "2.1.10"
}

group = "com.itaypoo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("uk.co.electronstudio.jaylib:jaylib:5.5.+")
}

kotlin {
    jvmToolchain(17)
}