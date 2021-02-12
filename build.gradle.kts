import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("idea")
    id("io.ebean") version "12.3.2"
    kotlin("jvm") version "1.4.10"
    id("org.jetbrains.kotlin.kapt") version "1.3.72"
}

group = "ungkom.org"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    // use BOM
    // implementation(platform("io.ebean:ebean-bom:1.1"))
    implementation("net.dv8tion:JDA:4.2.0_228")
    implementation("com.jagrosh:jda-utilities:3.0.5")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.72")
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("org.avaje.composite:logback:1.1")

    implementation("io.ebean:ebean:12.3.2")
    implementation("io.ebean:ebean-querybean:12.2.3")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("org.postgresql:postgresql:42.2.8")
    implementation("org.quartz-scheduler:quartz:2.3.2")
    kapt("io.ebean:kotlin-querybean-generator:12.2.3")

    testImplementation("io.ebean:ebean-test:12.3.2")
    testImplementation("org.avaje.composite:junit:1.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.3.72")

    //implementation("me.jakejmattson:DiscordKt:0.21.3")
    testImplementation(kotlin("test-junit"))

}

tasks.test {
    testLogging.showStandardStreams = true
    useJUnit()
}



ebean {
    debugLevel = 1
}


tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}