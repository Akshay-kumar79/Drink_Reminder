package com.akshaw.convention

import org.gradle.api.Project
import org.jetbrains.kotlin.konan.properties.loadProperties
import java.io.FileInputStream
import java.util.Properties

object Config {
    
    object LocalProperties {
        
        const val FILE = "local.properties"
        
        object SingingConfig {
            const val STORE_FILE = "storeFile"
            const val STORE_PASSWORD = "storePassword"
            const val KEY_ALIAS = "keyAlias"
            const val KEY_PASSWORD = "keyPassword"
        }
    }
    
    object VersionProperties {
        
        const val FILE = "version.properties"
        
        object Version {
            const val NAME = "versionName"
            const val CODE = "versionCode"
        }
    }
    
}

fun Project.findLocalProperty(property: String): String? {
    val file = file("${project.rootDir}/${Config.LocalProperties.FILE}")
    return if (file.exists()) {
        val localProperties = Properties()
        localProperties.load(FileInputStream(file))
        localProperties[property]?.toString()
    } else {
        println("The '${Config.LocalProperties.FILE}' file does not exist.")
        null
    }
}

fun Project.findVersionProperty(property: String): String {
    return loadProperties("${rootDir}/${Config.VersionProperties.FILE}")
        .getProperty(property)
}

fun Any?.asString() = "\"$this\""