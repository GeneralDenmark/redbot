package org.ungkom.utils

import java.nio.file.Paths

public fun getResourcesFile(file: String): String {
    val projectDirAbsolutePath = Paths.get("").toAbsolutePath().toString()
    val resourcesPath = Paths.get(projectDirAbsolutePath, "/src/main/resources", file)

    return resourcesPath.toString()
}