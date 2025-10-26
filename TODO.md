# TODO - Convos Android

## 🎯 Current Status: 25-30% Complete

This file contains the prioritized next steps for continuing the iOS to Android conversion.

## 🔥 Critical Path (Next Session)

### 1. Repository Layer & Data Mappers
**Priority**: CRITICAL
**Estimated Time**: 3-4 hours

- [ ] Create `Mappers.kt` with entity-to-domain converters
  - [ ] `DBConversationEntity` → `Conversation`
  - [ ] `DBMessageEntity` → `Message`
  - [ ] `DBInboxEntity` → `Inbox`
  - [ ] Domain → Entity mappers for saving
  
- [ ] Create repository interfaces
  - [ ] `ConversationRepository`
  - [ ] `MessageRepository`
  - [ ] `InboxRepository`
  - [ ] `ProfileRepository`

- [ ] Implement repositories with Flow
  - [ ] `ConversationRepositoryImpl`
  - [ ] `MessageRepositoryImpl`
  - [ ] `InboxRepositoryImpl`

**Why Critical**: Bridges database and domain layers. Required for all features.

**Files to Port**:
- iOS: `ConvosCore/Sources/ConvosCore/Storage/Repositories/` (21 files)
- Android: `core/src/main/kotlin/com/convos/core/repository/`

### 2. XMTP Client Integration
**Priority**: CRITICAL
**Estimated Time**: 4-5 hours

- [ ] Verify XMTP Android SDK version compatibility
- [ ] Update `libs.versions.toml` with correct XMTP version
- [ ] Create `XMTPClientProvider.kt`
  - [ ] Client creation and initialization
  - [ ] Key management integration
  - [ ] Conversation creation
  
- [ ] Implement `MessagingServiceImpl.kt`
  - [ ] Start/stop functionality
  - [ ] Message sending
  - [ ] Message receiving
  - [ ] Sync conversations
  - [ ] Stream messages
  
- [ ] Port custom content types
  - [ ] `ExplodeSettingsCodec.kt`
  - [ ] Register with XMTP client

**Why Critical**: Core functionality of the app. Can't send/receive messages without it.

**Files to Port**:
- iOS: `ConvosCore/Sources/ConvosCore/Messaging/`
- Android: `core/src/main/kotlin/com/convos/core/messaging/`

### 3. Basic UI - Conversations List
**Priority**: HIGH
**Estimated Time**: 3-4 hours

- [ ] Create `ConversationsViewModel.kt`
  - [ ] StateFlow for conversation list
  - [ ] StateFlow for loading state
  - [ ] Load conversations from repository
  - [ ] Handle refresh
  
- [ ] Create `ConversationsScreen.kt`
  - [ ] LazyColumn with conversation items
  - [ ] Pull-to-refresh
  - [ ] Empty state
  - [ ] Loading state
  
- [ ] Create `ConversationListItem.kt`
  - [ ] Avatar display
  - [ ] Name and preview text
  - [ ] Timestamp
  - [ ] Unread indicator
  
- [ ] Update `MainActivity.kt` to show conversations screen

**Why High**: First visible feature. Makes progress tangible.

**Files to Port**:
- iOS: `Convos/Conversations List/`
- Android: `app/src/main/kotlin/com/convos/android/ui/conversations/`

## 📋 Medium Priority (Following Sessions)

### 4. Conversation Detail (Chat View)
**Estimated Time**: 4-5 hours

- [ ] Create `ConversationViewModel.kt`
  - [ ] StateFlow for messages
  - [ ] Send message function
  - [ ] Load more messages (pagination)
  
- [ ] Create `ConversationScreen.kt`
  - [ ] Message list (reversed LazyColumn)
  - [ ] Message input field
  - [ ] Send button
  
- [ ] Create message bubble components
  - [ ] `OutgoingMessageBubble.kt`
  - [ ] `IncomingMessageBubble.kt`
  - [ ] `MessageUpdateItem.kt` (system messages)
  
- [ ] Handle message types
  - [ ] Text messages
  - [ ] Emoji messages
  - [ ] Reactions
  - [ ] Replies

**Files to Port**:
- iOS: `Convos/Conversation Detail/` (65 files!)
- Android: `app/src/main/kotlin/com/convos/android/ui/conversation/`

### 5. Navigation Setup
**Estimated Time**: 1-2 hours

- [ ] Add Navigation Compose dependency
- [ ] Create `ConvosNavigation.kt`
  - [ ] Define routes
  - [ ] NavHost setup
  
- [ ] Update MainActivity to use NavHost
- [ ] Handle deep links
- [ ] Handle back navigation

### 6. State Machines & Business Logic
**Estimated Time**: 3-4 hours

- [ ] Port `ConversationStateMachine.kt`
- [ ] Port `InboxStateMachine.kt`
- [ ] Port `InboxStateManager.kt`
- [ ] Port writer classes (12 files)

**Files to Port**:
- iOS: `ConvosCore/Sources/ConvosCore/Inboxes/`
- iOS: `ConvosCore/Sources/ConvosCore/Storage/Writers/`

### 7. Firebase Integration
**Estimated Time**: 2-3 hours

- [ ] Set up Firebase projects (local, dev, prod)
- [ ] Download `google-services.json` for each flavor
- [ ] Place in correct directories
- [ ] Test Firebase initialization
- [ ] Implement `FirebaseHelper.kt`
- [ ] Configure App Check

### 8. Push Notifications
**Estimated Time**: 3-4 hours

- [ ] Complete `ConvosFirebaseMessagingService.kt`
  - [ ] Parse notification payload
  - [ ] Decrypt XMTP message
  - [ ] Show notification
  - [ ] Handle notification tap
  
- [ ] Create notification channels
- [ ] Request notification permission (Android 13+)
- [ ] Implement `DeviceRegistrationManager.kt`
- [ ] Register FCM token with backend

### 9. Profile & Settings
**Estimated Time**: 2-3 hours

- [ ] Create `ProfileScreen.kt`
  - [ ] Display name editor
  - [ ] Avatar picker/uploader
  
- [ ] Create `SettingsScreen.kt`
  - [ ] App settings
  - [ ] Debug menu (dev/local only)
  
- [ ] Create `DebugScreen.kt`
  - [ ] Export database
  - [ ] View logs
  - [ ] Clear data

### 10. Conversation Creation
**Estimated Time**: 2-3 hours

- [ ] Create `CreateConversationScreen.kt`
  - [ ] Contact selection
  - [ ] Group name input
  - [ ] Group settings
  
- [ ] Implement conversation creation flow
- [ ] Handle invites

## 🎨 Polish & Features (Lower Priority)

### 11. Image Handling
**Estimated Time**: 2-3 hours

- [ ] Set up Coil image loader
- [ ] Create `AvatarComponent.kt`
- [ ] Implement image compression
- [ ] Add image caching
- [ ] Port `ImageCache.kt`

### 12. Deep Linking
**Estimated Time**: 2-3 hours

- [ ] Create `DeepLinkHandler.kt`
- [ ] Handle conversation deep links
- [ ] Handle invite deep links
- [ ] Set up App Links (HTTPS)
- [ ] Test deep link flows

### 13. Explode Feature
**Estimated Time**: 2-3 hours

- [ ] Port explode logic
- [ ] Add countdown UI
- [ ] Implement message deletion
- [ ] Add confirmation dialogs

### 14. Additional Features
- [ ] QR code generation
- [ ] Contact sharing
- [ ] Message search
- [ ] Conversation pinning
- [ ] Conversation muting
- [ ] Member management

## 🧪 Testing & Quality

### Unit Tests
- [ ] Repository tests
- [ ] ViewModel tests
- [ ] Database migration tests
- [ ] Mapper tests

### Integration Tests
- [ ] XMTP client tests
- [ ] End-to-end message flow tests
- [ ] Database transaction tests

### UI Tests
- [ ] Compose UI tests for screens
- [ ] Navigation tests

## 📦 Release Preparation

### App Store Setup
- [ ] Create Play Store listing
- [ ] Prepare screenshots
- [ ] Write store description
- [ ] Upload app icon assets

### Release Build
- [ ] Configure signing keys
- [ ] Test release build
- [ ] Set up crash reporting
- [ ] Configure analytics

### CI/CD
- [ ] Update GitHub Actions (or keep Bitrise)
- [ ] Automated builds
- [ ] Automated tests
- [ ] Deploy to internal testing track

## 🎓 Documentation
- [ ] API documentation
- [ ] Architecture decision records
- [ ] Contribution guidelines
- [ ] User documentation

## 📊 Progress Tracking

Use this checklist to track overall progress:

- [x] Project setup (100%)
- [x] Database layer (100%)
- [x] Domain models (100%)
- [x] Session/Auth (100%)
- [ ] Repositories (0%)
- [ ] XMTP integration (0%)
- [ ] Basic UI (0%)
- [ ] Navigation (0%)
- [ ] ViewModels (0%)
- [ ] Firebase (0%)
- [ ] Notifications (0%)
- [ ] Features (0%)
- [ ] Testing (0%)
- [ ] Release prep (0%)

**Overall: 25-30% Complete**

## 💡 Tips for Next Session

1. **Start with Mappers**: They're small and give quick wins
2. **Test XMTP Early**: It's the most complex integration
3. **Build UI Incrementally**: Get something on screen fast for motivation
4. **Use Mock Data**: Don't block UI work on XMTP integration
5. **Commit Frequently**: Small, focused commits are easier to review

## 🚨 Blockers & Risks

- **XMTP SDK Compatibility**: Verify Android SDK has feature parity with iOS
- **Key Management**: Ensure crypto operations work on Android
- **Performance**: Test on low-end devices early
- **Battery Usage**: Monitor background sync impact

## 📞 When You Need Help

- Check iOS implementation in corresponding files
- Refer to `AGENTS.md` for AI agent guidance
- See `SUMMARY.md` for architecture decisions
- Android Studio has excellent Kotlin/Compose docs built-in

---

**Last Updated**: October 26, 2025
**Next Milestone**: Repository Layer + XMTP Integration (Target: 50%)

