apply {
    from("$rootDir/library-build.gradle")
}



dependencies {
    "implementation"(project(Modules.constants))
    "implementation"(project(Modules.core))
}