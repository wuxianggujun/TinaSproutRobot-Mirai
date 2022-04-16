plugins {
    // val kotlinVersion = "1.5.32"
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    //id("net.mamoe.mirai-console") version "2.10.0-RC2"
    id("net.mamoe.mirai-console") version "2.11.0-M2.2"
}

group = "wxgj.tinasproutrobot.mirai"
version = "0.1.1"

repositories {
    mavenLocal()
    maven("https://repo.mirai.mamoe.net/snapshots")
    maven { url = uri("https://maven.aliyun.com/nexus/content/groups/public/") }
    mavenCentral()
}

dependencies {

//    implementation("com.alibaba:fastjson:1.2.80")
//    implementation("org.apache.commons:commons-lang3:3.12.0")
//    implementation("org.apache.httpcomponents:httpclient:4.5.13")
//
//    implementation("org.jsoup:jsoup:1.14.3")
//    implementation("org.ini4j:ini4j:0.5.4")
//

//    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.5")

    implementation(fileTree(mapOf("dir" to "src/main/resources/libs", "include" to listOf("*.jar"))))

}


// config JVM target to 1.8 for kotlin compilation tasks
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}