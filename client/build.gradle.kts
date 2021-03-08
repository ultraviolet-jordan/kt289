plugins {
    kotlin("jvm")
}

group = "com.kt289"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":bzip2"))
    implementation(project(":util"))
}
