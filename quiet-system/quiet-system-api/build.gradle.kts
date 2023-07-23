dependencies {
    api(project(":quiet-spring-boot-starters:quiet-service-spring-boot-starter"))
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
