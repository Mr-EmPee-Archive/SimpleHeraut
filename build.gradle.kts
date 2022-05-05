plugins {
    id("io.freefair.lombok").version("6.4.1")

    java
}

group = "tk.empee"
version = "1.0"

repositories {
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")

    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly("org.jetbrains:annotations:22.0.0")
    compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")

    implementation("tk.empee.configurationsManager:yaml:3.6.5")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
}