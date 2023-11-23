import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

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
        index {
            description.set("Powered by Kobweb")
        }
    }
}

kotlin {
    configAsKobwebApplication("ru")
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.animation)
                implementation("dev.gitlive:firebase-auth:1.10.4")
                implementation("dev.gitlive:firebase-database:1.10.4")
                // implementation("dev.gitlive:firebase-messaging:1.10.4")
                implementation("dev.gitlive:firebase-common:1.10.4")
                implementation("dev.gitlive:firebase-storage:1.10.4")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                implementation("dev.whyoleg.cryptography:cryptography-core:0.2.0")
                implementation("dev.whyoleg.cryptography:cryptography-provider-webcrypto:0.2.0")

            }
        }

        val jsMain by getting {
            dependencies {
                implementation(compose.html.core)
                implementation(libs.kobweb.core)
                implementation(libs.kobweb.silk)
                implementation(libs.silk.icons.fa)
                implementation("com.ionspin.kotlin:multiplatform-crypto-libsodium-bindings:0.9.0")

                // implementation(libs.kobwebx.markdown)
            }
        }
    }
}
