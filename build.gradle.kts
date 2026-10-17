plugins {
    id("java")
    id("org.graalvm.buildtools.native") version "1.1.14"

}

group = "dev.cadindie"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:5.5.0")
    implementation("com.google.code.gson:gson:2.14.0")
    implementation("org.tomlj:tomlj:2.1.1")
    implementation("com.github.Open-Launcher-Collective:NativeBind:2ddce1709c")
    compileOnly("org.graalvm.sdk:nativeimage:25.3.4.1")

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    annotationProcessor("com.github.Open-Launcher-Collective:NativeBind:2ddce1709c")
}

graalvmNative {
    agent {
        enabled.set(false)
        defaultMode.set("standard")
    }

    binaries {
        named("main") {
            imageName.set("libsmml")
            sharedLibrary.set(true)
        }
        create("androidARM") {
            imageName.set("libsmml")
            sharedLibrary.set(true)
            buildArgs.addAll(
                "-H:-UseContainerSupport",
                "--target=android-aarch64",
                "-Dos.arch=aarch64",
                "-H:-CheckToolchain",
                "-H:PageSize=16384",
                "-H:+UseCAPCache",
                "-H:CAPCacheDir=$rootDir/caps/aarch64",
                "--native-compiler-path=" + System.getenv("ANDROID_NDK") + "/toolchains/llvm/prebuilt/linux-x86_64/bin/aarch64-linux-android26-clang")
        }
        create("androidARMCache") {
            imageName.set("libsmml")
            sharedLibrary.set(true)
            buildArgs.addAll(
                "-H:CAPCacheDir=$rootDir/caps/aarch64",
                "-H:-UseContainerSupport",
                "-H:-UseCAPCache",
                "-H:+NewCAPCache",
                "-H:-CheckToolchain",
                "-Dos.arch=aarch64",
                $$"-Dsvm.platform=org.graalvm.nativeimage.Platform$ANDROID_AARCH64",
                "--native-compiler-options=-static",
                "--native-compiler-options=-Wl,--gc-sections",
                "--native-compiler-path=" + System.getenv("ANDROID_NDK") + "/toolchains/llvm/prebuilt/linux-x86_64/bin/aarch64-linux-android26-clang",
            )
        }
    }

    binaries.all {
        buildArgs.addAll(
            "--enable-http", "--enable-https",
            "-Os", "-march=compatibility", "-H:-ForeignAPISupport", "--link-at-build-time=dev.cadindie",
            "-H:-VMContinuations", "-H:-ReduceImplicitExceptionStackTraceInformation",
            "-g"
        )
    }
}

tasks.test {
    useJUnitPlatform()
}