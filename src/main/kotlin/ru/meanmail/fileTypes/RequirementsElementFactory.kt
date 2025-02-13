package ru.meanmail.fileTypes

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import ru.meanmail.psi.NameReq
import ru.meanmail.psi.RequirementsFile
import ru.meanmail.psi.UriReference
import ru.meanmail.psi.Versionspec


fun createFileFromText(project: Project, text: String): RequirementsFile {
    val name = "dummy.txt"
    return PsiFileFactory.getInstance(project).createFileFromText(
        name, RequirementsFileType, text
    ) as RequirementsFile
}

fun createUriReference(project: Project, name: String): UriReference {
    val file = createFileFromText(project, "-r $name")
    return file.firstChild.children[0].children[0] as UriReference
}

fun createNameReq(project: Project, text: String): NameReq? {
    val file = createFileFromText(project, text)
    return file.firstChild as? NameReq
}

fun createVersionspec(project: Project, version: String): Versionspec? {
    val preparedVersion = version.split(',')
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .joinToString(",")
    val nameReq = createNameReq(project, "packageName${preparedVersion}")
    return nameReq?.children?.find { it is Versionspec } as? Versionspec
}
