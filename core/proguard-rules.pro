# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\soft\Android\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#glide
####################库################################
-dontwarn com.bumptech.glide.**
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#gson
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *; }

#rxjava
-dontwarn io.reactivex.**
-keep class io.reactivex.** { *; }

#okhttp
-dontwarn okio.**
-keep class okio.** { *; }
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }

#retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#greendao
-dontwarn org.greenrobot.greendao.**
-keep class org.greenrobot.greendao.** { *; }
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties

