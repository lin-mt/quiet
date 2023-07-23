dependencies {
    api(project(":quiet-spring-boot-starters:quiet-service-spring-boot-starter"))
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    annotationProcessor("com.querydsl:querydsl-apt:${property("queryDslVersion")}:jakarta")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
