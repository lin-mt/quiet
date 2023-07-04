plugins {
    java
    id("org.springframework.boot") version "3.1.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.23"
}

allprojects {
    group = "cn.linmt"
    version = "0.0.1-SNAPSHOT"
}

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.graalvm.buildtools.native")

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    repositories {
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://maven.aliyun.com/repository/public/") }
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    dependencies {
        implementation("org.apache.commons:commons-lang3:${property("apacheCommonsLang3Version")}")
        implementation("org.apache.commons:commons-collections4:${property("apacheCommonsCollections4Version")}")
        implementation("com.google.guava:guava:${property("googleGuavaVersion")}")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
