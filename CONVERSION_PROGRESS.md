# Android Conversion Progress

## ✅ Completed (Phase 1 & 2)

### Project Structure & Build System
- ✅ Created Android project structure with app and core modules
- ✅ Set up Gradle build configuration with product flavors (local, dev, prod)
- ✅ Created version catalog (libs.versions.toml) with all dependencies
- ✅ Configured ProGuard rules for release builds
- ✅ Set up Android manifest with permissions and deep linking
- ✅ Created .gitignore for Android

### Configuration & Environment
- ✅ Ported ConfigManager to Android
- ✅ Converted config.*.json files to Android assets
- ✅ Ported AppEnvironment enum with environment-specific settings
- ✅ Set up logging system with Timber integration

### Database Layer (Room)
- ✅ Created Room database architecture
- ✅ Ported all enum types (ConversationKind, Consent, MemberRole, MessageStatus, etc.)
- ✅ Created Room type converters for custom types
- ✅ Ported DBConversationEntity with all fields and relationships
- ✅ Ported DBMessageEntity with reactions and replies support
- ✅ Ported DBInboxEntity
- ✅ Ported DBConversationMemberEntity (junction table)
- ✅ Created all DAOs (ConversationDao, MessageDao, InboxDao, ConversationMemberDao)
- ✅ Created DatabaseManager with transaction support
- ✅ Set up WAL (Write-Ahead Logging) mode for concurrent access

### UI Foundation
- ✅ Created Compose theme system with colors from iOS
- ✅ Set up typography system
- ✅ Created MainActivity with Compose
- ✅ Set up ConvosApplication class
- ✅ Created resource files (strings.xml, colors.xml, themes.xml)
- ✅ Created notification drawable resources

### Notifications
- ✅ Created ConvosFirebaseMessagingService stub
- ✅ Configured FCM in manifest
- ✅ Set up notification channels configuration

## 🚧 In Progress (Phase 3)

### Domain Models
- Starting to port domain models (Conversation, Message, Inbox, etc.)

## 📋 Next Steps

### Phase 3: Domain Models & Business Logic
- Port Conversation domain model
- Port Message domain model
- Port Inbox domain model
- Port Profile models
- Port Invite models
- Create model mappers (DB entities to domain models)

### Phase 4: Session & Authentication
- Port SessionManager
- Port AuthService
- Port KeychainIdentityStore (using Android EncryptedSharedPreferences)
- Implement secure storage for cryptographic keys

### Phase 5: XMTP Integration
- Integrate XMTP Android SDK
- Port XMTPClientProvider
- Port MessagingService
- Port custom content types
- Implement message encryption/decryption

### Phase 6: Repositories & State Management
- Port all repositories (21 files from iOS)
- Port all writers (12 files from iOS)
- Implement StateFlow-based state management
- Port ConversationStateMachine
- Port InboxStateMachine

### Phase 7: UI Implementation
- Port ConversationsView (list)
- Port ConversationDetailView
- Port message bubbles and input
- Port conversation creation flow
- Port settings and profile screens

## Architecture Notes

### Key Differences from iOS
1. **Database**: GRDB → Room
2. **State Management**: @Observable/@State → StateFlow/ViewModel
3. **UI Framework**: SwiftUI → Jetpack Compose
4. **Async**: async/await → Coroutines/Flow
5. **Storage**: Keychain → EncryptedSharedPreferences/KeyStore
6. **Dependency Injection**: Manual → Potentially Hilt/Koin (TBD)

### Technology Stack
- **Language**: Kotlin
- **UI**: Jetpack Compose + Material 3
- **Database**: Room (SQLite)
- **Networking**: Retrofit + OkHttp
- **Async**: Kotlin Coroutines + Flow
- **DI**: Manual (for now)
- **Image Loading**: Coil 3
- **Serialization**: kotlinx.serialization
- **Logging**: Timber
- **XMTP**: XMTP Android SDK

## File Statistics

### Created So Far
- **Gradle files**: 4 (settings, root build, app build, core build)
- **Config files**: 3 (config.local.json, config.dev.json, config.prod.json)
- **Kotlin source files**: ~25
  - Database entities: 4
  - DAOs: 4
  - Models: 5
  - Config: 3
  - UI: 4
  - Other: 5
- **Resource files**: 7
- **Manifest files**: 2

### iOS Files to Convert (Estimated)
- **Swift files**: ~150
- **ConvosCore**: ~130 files
- **Main app**: ~100+ files
- **Total lines of code**: ~30,000+ (estimated)

## Current Status
**Phase 1 & 2**: ✅ Complete (Foundation + Database)
**Phase 3**: 🚧 In Progress (Domain Models)
**Overall Progress**: ~15% complete

