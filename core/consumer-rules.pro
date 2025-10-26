# Consumer ProGuard rules for core module

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# XMTP
-keep class org.xmtp.** { *; }

# Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-keep,includedescriptorclasses class com.convos.core.**$$serializer { *; }
-keepclassmembers class com.convos.core.** {
    *** Companion;
}

