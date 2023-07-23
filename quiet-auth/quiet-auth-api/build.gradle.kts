dependencies {
    implementation(project(":quiet-spring-boot-starters:quiet-jpa-spring-boot-starter"))
    implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
