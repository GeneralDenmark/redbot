package org.ungkom.utils

import java.nio.file.Path
import java.nio.file.Paths

public fun getResourcesFile(file: String): String {
    val resourcesPath = Paths.get(getResourcePath().toString(), file)

    return resourcesPath.toString()
}

public fun getResourcePath(): Path {
    return Paths.get(getProjectAbsolutePath().toString(), "/src/main/resources")
}
public fun getProjectAbsolutePath(): Path {
    return Paths.get("").toAbsolutePath()
}

public fun getProjectBeforeAbsolutePath(): Path {
    return Paths.get("").parent
}