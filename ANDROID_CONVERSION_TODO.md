# Android Conversion TODO

This document tracks the conversion of Convos from iOS (Swift) to Android (Kotlin).

## Phase 1: Project Setup & Infrastructure
- [ ] Create Android project structure (Gradle-based)
- [ ] Set up Gradle build configuration with product flavors (local, dev, prod)
- [ ] Configure package structure (com.convos.android)
- [ ] Set up version catalog for dependency management
- [ ] Configure ProGuard/R8 rules for release builds
- [ ] Set up Android manifest with required permissions
- [ ] Configure app signing for release builds

## Phase 2: Environment & Configuration
- [ ] Port ConfigManager to Android (environment switching)
- [ ] Convert config.*.json files to Android build config
- [ ] Set up Firebase for Android (3 flavors: local, dev, prod)
- [ ] Configure google-services.json for each flavor
- [ ] Port AppEnvironment to Android
- [ ] Set up Android keystore/secrets management
- [ ] Configure build variants and flavor dimensions

## Phase 3: Core Dependencies
- [ ] Add XMTP Android SDK dependency
- [ ] Add Room database dependency (replacement for GRDB)
- [ ] Add Firebase SDK (Core, Analytics, App Check)
- [ ] Add Jetpack Compose dependencies
- [ ] Add Kotlin Coroutines and Flow
- [ ] Add Retrofit/OkHttp for networking
- [ ] Add Coil for image loading
- [ ] Add Protocol Buffers support for Android
- [ ] Add secp256k1 support for Android

## Phase 4: Database Layer (ConvosCore - Storage)
- [ ] Design Room database schema
- [ ] Convert DB models (DBConversation, DBMessage, DBInbox, etc.)
- [ ] Create Room @Entity classes
- [ ] Create Room @Dao interfaces
- [ ] Convert DatabaseManager to Android Room
- [ ] Port all Repositories (21 files)
- [ ] Port all Writers (12 files)
- [ ] Port hydration logic (DB to domain model mapping)
- [ ] Implement database migrations
- [ ] Add database testing

## Phase 5: Domain Models & Business Logic
- [ ] Convert domain models (Conversation, Message, Inbox, Profile, etc.)
- [ ] Port ConversationStateMachine
- [ ] Port InboxStateMachine
- [ ] Port SessionManager and SessionManagerProtocol
- [ ] Port ConvosClient
- [ ] Port MessagingService and MessagingServiceProtocol
- [ ] Port InboxStateManager
- [ ] Port SyncingManager

## Phase 6: XMTP Integration
- [ ] Set up XMTP Android client
- [ ] Port XMTPClientProvider
- [ ] Port custom content types (ExplodeSettingsCodec)
- [ ] Port conversation authorization logic
- [ ] Port message sending/receiving
- [ ] Port push notification integration with XMTP
- [ ] Test XMTP connectivity and messaging

## Phase 7: Authentication & Security
- [ ] Port KeychainIdentityStore to Android (use EncryptedSharedPreferences/Keystore)
- [ ] Port AuthService and AuthServiceProtocol
- [ ] Port KeychainService
- [ ] Implement secure storage for identity keys
- [ ] Port ConvosKeychainItem logic

## Phase 8: API & Networking
- [ ] Port ConvosAPIClient using Retrofit
- [ ] Convert API models
- [ ] Implement API error handling
- [ ] Add network connectivity monitoring
- [ ] Implement request/response interceptors

## Phase 9: UI Layer - Foundation
- [ ] Set up Jetpack Compose theme system
- [ ] Port color system from Assets.xcassets to Compose Color
- [ ] Create typography system (Compose)
- [ ] Port Design System components
- [ ] Create reusable Compose components
- [ ] Set up navigation (Jetpack Navigation Compose)

## Phase 10: UI Layer - Core Screens
- [ ] Port ConversationsView (conversation list)
- [ ] Port ConversationsViewModel
- [ ] Port Conversation Detail view and components (65 files!)
- [ ] Port Conversation Creation flow (4 files)
- [ ] Port message input and bubble components
- [ ] Port avatar and image handling

## Phase 11: UI Layer - Additional Screens
- [ ] Port ProfileView
- [ ] Port AppSettingsView
- [ ] Port DebugView and DebugExportView
- [ ] Port shared views (22 files)
- [ ] Port explode feature UI

## Phase 12: Utilities & Extensions
- [ ] Port utility classes (AsyncTimeout, Base64URL, etc.)
- [ ] Port extension functions
- [ ] Port Logger to Android (Timber/Logcat)
- [ ] Port date/time utilities
- [ ] Port string utilities
- [ ] Port image compression utilities
- [ ] Port QR code generation

## Phase 13: Notifications
- [ ] Implement Firebase Cloud Messaging (FCM)
- [ ] Port NotificationService extension logic
- [ ] Port PushNotificationPayload
- [ ] Port DeviceRegistrationManager
- [ ] Implement notification channels
- [ ] Handle notification taps and deep linking
- [ ] Test notification delivery

## Phase 14: Deep Linking
- [ ] Configure Android App Links (Universal Links equivalent)
- [ ] Configure custom URL schemes
- [ ] Port DeepLinkHandler
- [ ] Implement intent handling
- [ ] Test deep link flows

## Phase 15: Images & Assets
- [ ] Convert app icons for Android (adaptive icons)
- [ ] Convert SVG assets to Android Vector Drawables
- [ ] Set up image caching (Coil)
- [ ] Port AvatarView with caching
- [ ] Optimize image resources

## Phase 16: Testing
- [ ] Set up JUnit for unit tests
- [ ] Port unit tests (SessionManagerTests, etc.)
- [ ] Set up Espresso for UI tests
- [ ] Create instrumented tests for database
- [ ] Test XMTP integration
- [ ] Test multi-environment builds

## Phase 17: Build & Release Pipeline
- [ ] Configure GitHub Actions for Android (or Bitrise)
- [ ] Set up automated builds
- [ ] Configure Play Store release pipeline
- [ ] Set up crash reporting (Firebase Crashlytics)
- [ ] Configure app distribution for testing

## Phase 18: Documentation & Cleanup
- [ ] Update README for Android
- [ ] Create Android best practices guide
- [ ] Document build/run instructions
- [ ] Clean up iOS-specific files (if repo is Android-only)
- [ ] Update release process documentation

## Phase 19: Testing & QA
- [ ] End-to-end testing of all features
- [ ] Test on multiple Android versions
- [ ] Test on multiple device sizes/densities
- [ ] Performance testing and optimization
- [ ] Memory leak detection
- [ ] Battery usage optimization

## Phase 20: Launch Preparation
- [ ] Prepare Play Store listing
- [ ] Create store screenshots
- [ ] Write store description
- [ ] Prepare privacy policy
- [ ] Submit for review

## Notes
- Total Swift files to convert: ~150+
- Priority: Core → UI → Polish
- Consider using shared Kotlin Multiplatform for core logic if maintaining both platforms

