plugins {
    id("java")
    id("java-library")
    id("maven-publish")
}

group = "dev.cadindie"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:5.4.0")
    implementation("com.squareup.moshi:moshi:1.15.2")
    implementation("com.moandjiezana.toml:toml4j:0.7.2")
    compileOnly("org.graalvm.sdk:nativeimage:25.3.4.1")

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "dev.cadindie"
            artifactId = "smml"
            version = "0.1.0-alpha.1"
        }
    }
}