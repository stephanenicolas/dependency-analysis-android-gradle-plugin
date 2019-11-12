package com.autonomousapps

import java.io.File

internal class AndroidProject {

    internal val projectDir = File("build/functionalTest").also {
        it.mkdirs()
    }

    private val appDir = File("build/functionalTest/app").also {
        it.mkdirs()
    }

    private val mainDir = File("build/functionalTest/app/src/main").also {
        it.mkdirs()
    }

    private val resDir = File("build/functionalTest/app/src/main/res").also {
        it.mkdirs()
    }

    private val valuesDir = File("build/functionalTest/app/src/main/res/values").also {
        it.mkdirs()
    }

    private val packageRoot = File("build/functionalTest/app/src/main/java/com/autonomousapps/test").also {
        it.mkdirs()
    }

    init {
        projectDir.resolve("settings.gradle").writeText("""
            rootProject.name = 'real-app'
            include(':app')
        """.trimIndent()
        )
        projectDir.resolve("build.gradle").writeText("""
            buildscript {
                repositories {
                    google()
                    jcenter()
                }
                dependencies {
                    classpath 'com.android.tools.build:gradle:3.5.2'
                    classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.50"
                }
            }
            subprojects {
                repositories {
                    google()
                    jcenter()
                }
            }
        """.trimIndent()
        )
        appDir.resolve("build.gradle").writeText("""
            plugins {
                id('com.android.application')
                id('kotlin-android')
                id('com.autonomousapps.dependency-analysis')
            }
            android {
                compileSdkVersion 29
                defaultConfig {
                    applicationId "com.autonomousapps.test"
                    minSdkVersion 21
                    targetSdkVersion 29
                    versionCode 1
                    versionName "1.0"
                }
                compileOptions {
                    sourceCompatibility JavaVersion.VERSION_1_8
                    targetCompatibility JavaVersion.VERSION_1_8
                }
                kotlinOptions {
                    jvmTarget = "1.8"
                }
            }
            dependencies {
                implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.50"
                implementation 'androidx.appcompat:appcompat:1.1.0'
                implementation 'androidx.core:core-ktx:1.1.0'
                implementation 'com.google.android.material:material:1.0.0'
                implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
                implementation 'androidx.navigation:navigation-fragment-ktx:2.1.0'
                implementation 'androidx.navigation:navigation-ui-ktx:2.1.0'
            }
        """.trimIndent()
        )
        //android:icon="@mipmap/ic_launcher"
        //android:roundIcon="@mipmap/ic_launcher_round"
        mainDir.resolve("AndroidManifest.xml").writeText("""
            <?xml version="1.0" encoding="utf-8"?>
            <manifest xmlns:android="http://schemas.android.com/apk/res/android"
                package="com.autonomousapps.test"
                >
            
                <application
                    android:allowBackup="true"
                    android:label="Test app"
                    android:theme="@style/AppTheme"
                    >
                    <activity
                        android:name=".MainActivity"
                        android:label="MainActivity"
                        android:theme="@style/AppTheme.NoActionBar"
                        >
                        <intent-filter>
                            <action android:name="android.intent.action.MAIN" />
                            <category android:name="android.intent.category.LAUNCHER" />
                        </intent-filter>
                    </activity>
                </application>
            </manifest>            
        """.trimIndent()
        )
        valuesDir.resolve("styles.xml").writeText("""
            <?xml version="1.0" encoding="utf-8"?>
            <resources>
                <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
                    <item name="colorPrimary">@color/colorPrimary</item>
                    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
                    <item name="colorAccent">@color/colorAccent</item>
                </style>
            
                <style name="AppTheme.NoActionBar">
                    <item name="windowActionBar">false</item>
                    <item name="windowNoTitle">true</item>
                </style>
            
                <style name="AppTheme.AppBarOverlay" parent="ThemeOverlay.AppCompat.Dark.ActionBar" />
            
                <style name="AppTheme.PopupOverlay" parent="ThemeOverlay.AppCompat.Light" />
            </resources>
        """.trimIndent()
        )
        valuesDir.resolve("colors.xml").writeText("""
            <?xml version="1.0" encoding="utf-8"?>
            <resources>
                <color name="colorPrimaryDark">#0568ae</color>
                <color name="colorPrimary">#009fdb</color>
                <color name="colorAccent">#009fdb</color>
            </resources>
        """.trimIndent()
        )
        packageRoot.resolve("MainActivity.kt").writeText("""
            package com.autonomousapps.test
            
            import androidx.appcompat.app.AppCompatActivity
            
            class MainActivity : AppCompatActivity() {
                
            }
        """.trimIndent()
        )
    }
}
