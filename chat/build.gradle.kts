plugins {
    java
}

group = "com.kt289"
version = "1.0"

repositories { }

dependencies {
    implementation(project(":cache"))
    implementation(project(":util"))
}