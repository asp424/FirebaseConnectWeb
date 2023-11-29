import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import org.jetbrains.kotlin.ir.backend.js.compileIr

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kobweb.application)
    // alias(libs.plugins.kobwebx.markdown)

}
group = "dev.test.ru"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index{

        }
    }
}
val ktorVersion = "3.0.0-beta-1"

kotlin {
    js(IR) {
        configAsKobwebApplication("ru")
        sourceSets {
            val commonMain by getting {
                dependencies {
                    implementation(compose.runtime)
                    implementation(compose.animation)
                    implementation("dev.gitlive:firebase-auth:1.10.4")
                    implementation("dev.gitlive:firebase-database:1.10.4")
                    implementation("dev.gitlive:firebase-common:1.10.4")
                    implementation("dev.gitlive:firebase-storage:1.10.4")
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
                    implementation("io.ktor:ktor-client-core:$ktorVersion")
                    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                    implementation("io.ktor:ktor-client-logging:$ktorVersion")
                    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
             //       implementation("io.ktor:ktor-server-cors:3.0.0-beta-1")

                }
            }
            val jsMain by getting {
                dependencies {
                    implementation(compose.html.core)
                    implementation(libs.kobweb.core)
                    implementation(libs.kobweb.silk)
                    implementation(libs.silk.icons.fa)
                    // implementation(libs.kobwebx.markdown)
                }
            }
        }
        binaries.executable()
    }
}
