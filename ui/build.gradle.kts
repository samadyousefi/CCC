/*
 Copyright (c) 2020 Mustafa Ozhan. All rights reserved.
 */
plugins {
    id("com.android.library")
    id("androidx.navigation.safeargs")
    kotlin("android")
    kotlin("kapt")
}

android {
    with(ProjectSettings) {
        compileSdkVersion(projectCompileSdkVersion)

        defaultConfig {
            minSdkVersion(projectMinSdkVersion)
            targetSdkVersion(projectTargetSdkVersion)

            versionCode = getVersionCode(project)
            versionName = getVersionName(project)
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        buildFeatures {
            viewBinding = true
            dataBinding = true
        }
    }
}

dependencies {
    with(Dependencies) {
        implementation(kotlin)
        implementation(androidMaterial)
        implementation(constraintLayout)
        implementation(dagger)
        implementation(timber)
        implementation(admob)
        implementation(navigation)
    }
    with(Annotations) {
        kapt(daggerCompiler)
        kapt(daggerProcessor)
    }

    with(TestDependencies) {
        testImplementation(jUnit)
        testImplementation(mockK)
        testImplementation(archTesting)
        testImplementation(coroutinesTest)
    }

    with(Modules) {
        implementation(project(data))

        implementation(project(scopemob))
        implementation(project(basemob))
    }

    implementation(files(Libs.mxParser))
}