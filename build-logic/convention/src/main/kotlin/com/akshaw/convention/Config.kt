package com.akshaw.convention

import org.gradle.api.Project
import java.io.FileInputStream
import java.util.Properties

object Config {
    
    object LocalProperties {
        
        const val FILE = "local.properties"
        
        object SingingConfig {
            
            object Beta {
                const val STORE_FILE = "beta.upload.storeFile"
                const val STORE_PASSWORD = "beta.upload.storePassword"
                const val KEY_ALIAS = "beta.upload.keyAlias"
                const val KEY_PASSWORD = "beta.upload.keyPassword"
            }
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