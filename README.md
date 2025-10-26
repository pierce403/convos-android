# Convos Android

**🚧 Active Development: iOS to Android Port in Progress**

Convos is an everyday private chat app for the surveillance age. Built on the open-source, censorship-resistant, post-quantum secure [XMTP protocol](https://xmtp.org), Convos provides instant, impermanent, and self-evidently private conversations.

## 🔄 About This Port

This repository contains the **native Android version** of Convos, ported from the original iOS Swift/SwiftUI application to **Kotlin and Jetpack Compose**.

- **Original iOS**: ~150 Swift files, ~30,000 lines of code
- **Target Platform**: Android 8.0+ (API 26+)
- **Current Status**: ~25-30% complete - Foundation established
- **Architecture**: Clean Architecture with Room, Kotlin Coroutines, Flow, and Jetpack Compose

## What is Convos?

Convos is a privacy-first messenger that offers:
- **No signup** — Simply scan, tap or share into a conversation
- **No numbers** — New identity in every conversation
- **No history** — Time bomb your groupchats with irreversible countdowns
- **No spam** — Every conversation is invitation-only
- **No tracking** — Zero data collection, not even contact info
- **No server** — Messages stored on your device, secured by XMTP

Learn more at [convos.org](https://convos.org).

## ✅ What Works Now

The Android app currently has a **solid foundation** in place:

### Infrastructure (100% Complete)
- ✅ Multi-module Gradle project setup (app + core)
- ✅ Product flavors for 3 environments (local, dev, prod)
- ✅ Build system with version catalog
- ✅ ProGuard/R8 configuration for release builds

### Database Layer (100% Complete)
- ✅ Room database with SQLite and WAL mode
- ✅ All entity classes (Conversation, Message, Inbox, ConversationMember)
- ✅ Complete DAO interfaces with CRUD operations
- ✅ Type converters for custom types
- ✅ Reactive queries with Flow

### Domain Layer (100% Complete)
- ✅ All domain models ported from iOS
- ✅ Message types (Message, Reply, Reaction)
- ✅ Conversation and member models
- ✅ Profile and inbox models

### Security & Auth (100% Complete)
- ✅ Secure storage using EncryptedSharedPreferences
- ✅ Identity store for managing cryptographic keys
- ✅ Session manager for inbox lifecycle

### UI Foundation (100% Complete)
- ✅ Jetpack Compose setup with Material 3
- ✅ Theme system with iOS color palette
- ✅ Typography system
- ✅ Basic app structure

### The App Can Currently:
- ✅ Build successfully for all flavors
- ✅ Install and launch on Android devices
- ✅ Initialize database and secure storage
- ✅ Load environment configuration

## 🚧 What Still Needs Work

### Critical Features (Next Priority)
- ❌ Repository layer (data mappers)
- ❌ XMTP Android SDK integration
- ❌ Message sending and receiving
- ❌ Conversations list UI
- ❌ Conversation detail (chat) UI
- ❌ Message bubbles and input

### Core Features (Following Priority)
- ❌ ViewModels and state management
- ❌ Navigation between screens
- ❌ Conversation creation flow
- ❌ Contact/member management
- ❌ Profile and settings screens

### Integration & Polish
- ❌ Firebase setup and configuration
- ❌ Push notifications (FCM)
- ❌ Deep linking
- ❌ Image handling and caching
- ❌ QR code generation
- ❌ Explode (self-destruct) feature
- ❌ Animations and transitions

## 🎯 Feature Parity Status

Tracking parity with the iOS version:

| Feature | iOS | Android | Notes |
|---------|-----|---------|-------|
| Database | ✅ GRDB | ✅ Room | Complete |
| Conversations List | ✅ | ❌ | UI needed |
| Chat View | ✅ | ❌ | UI needed |
| Message Sending | ✅ | ❌ | XMTP needed |
| Message Receiving | ✅ | ❌ | XMTP needed |
| Push Notifications | ✅ | ⚠️ | Stub in place |
| Conversation Creation | ✅ | ❌ | Not started |
| Profile Management | ✅ | ❌ | Not started |
| Settings | ✅ | ❌ | Not started |
| Deep Linking | ✅ | ⚠️ | Manifest configured |
| Message Reactions | ✅ | ❌ | Not started |
| Message Replies | ✅ | ❌ | Not started |
| Explode Feature | ✅ | ❌ | Not started |
| QR Code Sharing | ✅ | ❌ | Not started |
| Debug Menu | ✅ | ❌ | Not started |

Legend: ✅ Complete | ⚠️ Partial | ❌ Not Started

## 🚀 Getting Started (Android Development)

### Prerequisites

- **Android Studio** Ladybug | 2024.2.1 or newer
- **JDK 17+**
- **Android SDK** with:
  - Minimum SDK: 26 (Android 8.0)
  - Target SDK: 35 (Android 15)
  - Compile SDK: 35

### Quick Start

1. **Clone the repository**
```bash
git clone https://github.com/pierce403/convos-android.git
cd convos-android
```

2. **Open in Android Studio**
```bash
# Open the project directory in Android Studio
# Or use the command line:
open -a "Android Studio" .
```

3. **Sync Gradle**
Android Studio will prompt you to sync. Click "Sync Now" or:
```bash
./gradlew build
```

4. **Select Build Variant**
- View → Tool Windows → Build Variants
- Select `localDebug` for local development

5. **Run the App**
```bash
./gradlew installLocalDebug
```

For detailed build instructions, see [README_ANDROID.md](README_ANDROID.md).

## 📁 Project Structure

```
convos-android/
├── app/                    # Android app module (UI layer)
│   ├── src/
│   │   ├── main/kotlin/   # App code (ViewModels, Compose UI)
│   │   ├── main/res/      # Android resources
│   │   └── main/assets/   # Config files
│   └── build.gradle.kts
├── core/                   # Core business logic module
│   ├── src/main/kotlin/   # Database, domain, XMTP
│   └── build.gradle.kts
├── gradle/
│   └── libs.versions.toml # Dependency versions
├── Convos/                 # Original iOS Swift code (reference)
├── ConvosCore/             # Original iOS core logic (reference)
└── docs/
    ├── TODO.md            # Prioritized next steps
    ├── STATUS.md          # Current progress
    ├── AGENTS.md          # AI agent guidance
    └── SUMMARY.md         # Architecture decisions
```

## 🏗️ Architecture

### Technology Stack

| Layer | iOS (Original) | Android (Port) |
|-------|---------------|----------------|
| UI | SwiftUI | Jetpack Compose + Material 3 |
| Database | GRDB | Room (SQLite) |
| State | @Observable/@State | StateFlow + ViewModel |
| Async | async/await + Combine | Coroutines + Flow |
| Security | Keychain | EncryptedSharedPreferences |
| Networking | URLSession | Retrofit + OkHttp |
| Images | SDWebImage | Coil 3 |
| Messaging | XMTP iOS SDK | XMTP Android SDK |

### Design Patterns

- **Clean Architecture**: Separation of UI, domain, and data layers
- **Repository Pattern**: Abstract data sources
- **MVVM**: ViewModels manage UI state
- **Reactive Streams**: Flow for reactive data updates
- **Dependency Injection**: Manual (may add Hilt later)

## 📚 Documentation

Comprehensive documentation for this project:

- **[README_ANDROID.md](README_ANDROID.md)** - Detailed Android build and development guide
- **[TODO.md](TODO.md)** - Prioritized task list with time estimates
- **[STATUS.md](STATUS.md)** - Current completion status and statistics
- **[AGENTS.md](AGENTS.md)** - Guide for AI agents continuing the work
- **[SUMMARY.md](SUMMARY.md)** - Architecture decisions and patterns
- **[ANDROID_CONVERSION_TODO.md](ANDROID_CONVERSION_TODO.md)** - Complete phase breakdown
- **[CONVERSION_PROGRESS.md](CONVERSION_PROGRESS.md)** - Phase-by-phase progress tracking

## 🧪 Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest

# Run specific module tests
./gradlew :core:test
```

## 🤝 Contributing

This project is in active development. Key areas needing work:

1. **Repository Layer** - Data mapping between database and domain
2. **XMTP Integration** - Android SDK implementation
3. **UI Screens** - Compose implementations of iOS screens
4. **ViewModels** - State management for screens

See [TODO.md](TODO.md) for detailed task breakdown.

## 📝 Development Workflow

### For AI Agents

If you're an AI agent continuing this work:
1. **Start with [AGENTS.md](AGENTS.md)** - Contains critical guidance
2. **Check [STATUS.md](STATUS.md)** - See what's complete
3. **Review [TODO.md](TODO.md)** - Pick next task
4. **Reference iOS code** - In `Convos/` and `ConvosCore/` directories
5. **Update documentation** - Keep README.md and feature parity table current

### For Human Developers

1. Reference iOS implementation for behavior
2. Follow Kotlin/Android conventions
3. Use existing patterns established in core module
4. Update documentation as you work
5. Commit frequently with clear messages

## 🎯 Milestones

- [x] **Phase 1**: Project setup and infrastructure (100%)
- [x] **Phase 2**: Database layer with Room (100%)
- [x] **Phase 3**: Domain models (100%)
- [x] **Phase 4**: Session and auth management (100%)
- [ ] **Phase 5**: Repository layer and XMTP integration (0%)
- [ ] **Phase 6**: Basic UI implementation (0%)
- [ ] **Phase 7**: Navigation and ViewModels (0%)
- [ ] **Phase 8**: Core features (0%)
- [ ] **Phase 9**: Polish and optimization (0%)
- [ ] **Phase 10**: Release preparation (0%)

**Current Progress: 25-30% Complete**

## 📄 License

See LICENSE file for details.

## 🔗 Links

- **Website**: [convos.org](https://convos.org)
- **XMTP Protocol**: [xmtp.org](https://xmtp.org)
- **Original iOS Repo**: [Link if public]

## 📞 Support

For questions about the Android port:
- Check documentation in `/docs`
- Review iOS implementation for reference
- See [AGENTS.md](AGENTS.md) for common patterns and solutions

---

**Status**: Foundation Complete - Ready for Feature Implementation
**Last Updated**: October 26, 2025
**Current Commit**: `1c0f61f`
