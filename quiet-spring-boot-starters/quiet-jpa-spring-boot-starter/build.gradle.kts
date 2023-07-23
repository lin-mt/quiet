dependencies {
    api(project(":quiet-common"))
    api(project(":quiet-spring-boot-starters:quiet-validation-spring-boot-starter"))
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("com.querydsl:querydsl-jpa:${property("queryDslVersion")}:jakarta")
    api("io.hypersistence:hypersistence-utils-hibernate-62:${property("hypersistenceUtilsVersion")}")
    implementation("net.bytebuddy:byte-buddy:${property("byteBuddyVersion")}")
    compileOnly("com.blazebit:blaze-persistence-core-api-jakarta:${property("blazePersistenceVersion")}")
    compileOnly("com.blazebit:blaze-persistence-integration-querydsl-expressions-jakarta:${property("blazePersistenceVersion")}")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.blazebit:blaze-persistence-core-impl-jakarta:${property("blazePersistenceVersion")}")
    runtimeOnly("com.blazebit:blaze-persistence-integration-hibernate-6.2:${property("blazePersistenceVersion")}")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    annotationProcessor("com.querydsl:querydsl-apt:${property("queryDslVersion")}:jakarta")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
