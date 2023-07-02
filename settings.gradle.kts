rootProject.name = "quiet"

fun addSubprojects(dir: File) {
    dir.listFiles()?.forEach { subDir ->
        if (subDir.isDirectory && subDir.listFiles()?.any { it.name == "build.gradle.kts" } == true) {
            val relativePath = subDir.relativeTo(rootDir).path.replace(File.separator, ":")
            include(":$relativePath")
        } else if (subDir.isDirectory) {
            addSubprojects(subDir)
        }
    }
}

addSubprojects(rootDir)