# Convos Android

Convos is an everyday private chat app for the surveillance age. Built on the open-source, censorship-resistant, post-quantum secure [XMTP protocol](https://xmtp.org).

## Android Conversion Status

🚧 **This is an active conversion from iOS to Android.** See `CONVERSION_PROGRESS.md` and `ANDROID_CONVERSION_TODO.md` for details.

**Current Status**: ~20% complete
- ✅ Project structure and build system
- ✅ Database layer (Room)
- ✅ Domain models
- 🚧 Business logic and repositories
- ⏳ UI implementation
- ⏳ XMTP integration

## Prerequisites

- Android Studio Ladybug | 2024.2.1 or newer
- JDK 17+
- Android SDK with:
  - Minimum SDK: 26 (Android 8.0)
  - Target SDK: 35 (Android 15)
  - Compile SDK: 35

## Project Structure

```
convos-android/
├── app/                    # Android app module
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/    # App-level Kotlin code (UI, ViewModels)
│   │   │   ├── res/       # Android resources
│   │   │   └── assets/    # Config files
│   │   └── ...
│   └── build.gradle.kts
├── core/                   # Core business logic module
│   ├── src/
│   │   ├── main/kotlin/   # Core logic, database, domain models
│   │   └── test/          # Unit tests
│   └── build.gradle.kts
├── gradle/
│   └── libs.versions.toml # Version catalog for dependencies
├── build.gradle.kts        # Root build file
└── settings.gradle.kts     # Gradle settings
```

## Building the Project

### 1. Clone and Open

```bash
cd /path/to/convos-android
# Open in Android Studio
```

### 2. Sync Gradle

Android Studio will automatically prompt you to sync Gradle. Click "Sync Now" or run:

```bash
./gradlew build
```

### 3. Build Variants

The project has 3 flavors × 2 build types = 6 variants:

**Flavors (Environments):**
- `local` - Local development (localhost API)
- `dev` - Development server
- `prod` - Production

**Build Types:**
- `debug` - Debug build with logging
- `release` - Release build with ProGuard

**Example variants:**
- `localDebug`
- `devDebug`
- `prodRelease`

### 4. Select Build Variant

In Android Studio:
1. View → Tool Windows → Build Variants
2. Select your desired variant (e.g., `localDebug`)

### 5. Run

Click the Run button (▶️) or:

```bash
# Run local debug variant
./gradlew installLocalDebug

# Run on specific device
adb devices  # List devices
./gradlew installLocalDebug
adb shell am start -n com.convos.android.local/.MainActivity
```

## Configuration Files

Configuration is managed through JSON files in `app/src/main/assets/`:

- `config.local.json` - Local environment
- `config.dev.json` - Dev environment
- `config.prod.json` - Production environment

Each flavor automatically loads its corresponding config file.

## Architecture

### Technology Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose + Material 3
- **Database**: Room (SQLite with WAL mode)
- **Networking**: Retrofit + OkHttp
- **Async**: Kotlin Coroutines + Flow
- **Image Loading**: Coil 3
- **Serialization**: kotlinx.serialization
- **Logging**: Timber
- **XMTP**: XMTP Android SDK (to be integrated)

### Module Overview

**app module:**
- UI layer (Jetpack Compose screens)
- ViewModels
- Navigation
- Dependency injection setup

**core module:**
- Domain models (Conversation, Message, Inbox, etc.)
- Database layer (Room entities, DAOs, database)
- Business logic (repositories, use cases)
- XMTP client integration
- API client
- Utilities and extensions

### Database

The app uses Room with:
- Write-Ahead Logging (WAL) for concurrent access
- Type converters for custom types (enums, dates, JSON)
- Foreign key constraints
- Indexes for performance

**Main entities:**
- `DBConversationEntity` - Conversations
- `DBMessageEntity` - Messages
- `DBInboxEntity` - User inboxes
- `DBConversationMemberEntity` - Conversation memberships (junction table)

### State Management

- **ViewModels** - Hold UI state and business logic
- **StateFlow** - Reactive state updates
- **Flow** - Reactive data streams from database
- **Coroutines** - Asynchronous operations

## Development Workflow

### Code Style

The project follows Kotlin coding conventions:
- Use `camelCase` for variables and functions
- Use `PascalCase` for classes
- Use `SCREAMING_SNAKE_CASE` for constants
- Prefer immutability (`val` over `var`)
- Use data classes for models
- Prefer sealed classes/interfaces for polymorphism

### Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest

# Run specific module tests
./gradlew :core:test
```

### Debugging

1. **Logcat**: View → Tool Windows → Logcat
2. **Database Inspector**: View → Tool Windows → App Inspection → Database Inspector
3. **Layout Inspector**: Tools → Layout Inspector

### Common Tasks

```bash
# Clean build
./gradlew clean

# Build release APK
./gradlew assembleRelease

# Build release AAB (for Play Store)
./gradlew bundleRelease

# Check dependencies
./gradlew dependencies

# Check for outdated dependencies
./gradlew dependencyUpdates
```

## Firebase Setup

Each flavor requires its own `google-services.json`:

1. Download from Firebase Console for each environment
2. Place in appropriate flavor directory:
   - `app/src/local/google-services.json`
   - `app/src/dev/google-services.json`
   - `app/src/prod/google-services.json`

## Contributing

This project is currently in active development during the iOS-to-Android conversion. Please coordinate with the team before making major changes.

### Key Files to Know

- `libs.versions.toml` - Dependency versions
- `ConvosDatabase.kt` - Database schema
- `ConvosApplication.kt` - Application initialization
- `MainActivity.kt` - Main entry point

## Troubleshooting

### Gradle Sync Issues

```bash
./gradlew clean
# File → Invalidate Caches... → Invalidate and Restart
```

### Database Issues

```bash
# Uninstall app to clear database
adb uninstall com.convos.android.local
# Or clear app data manually in device settings
```

### Build Errors

1. Ensure JDK 17+ is selected (File → Project Structure → SDK Location)
2. Check `local.properties` has correct SDK path
3. Sync Gradle files
4. Clean and rebuild

## Learn More

- [Android Developer Documentation](https://developer.android.com/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [XMTP Documentation](https://xmtp.org/docs)

## License

See LICENSE file for details.

