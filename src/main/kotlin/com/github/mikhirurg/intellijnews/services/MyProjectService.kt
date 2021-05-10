package com.github.mikhirurg.intellijnews.services

import com.github.mikhirurg.intellijnews.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
