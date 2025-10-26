# Guide for AI Agents - Convos Android

This file contains guidance for AI agents working on this codebase. It captures lessons learned, conventions, and important context.

## 🎯 Project Context

**What**: Converting Convos from iOS (Swift/SwiftUI) to Android (Kotlin/Compose)

**Current Status**: ~25-30% complete (see `STATUS.md`)

**Original iOS Codebase**: ~150 Swift files, ~30,000 lines of code

**Target**: Feature-complete Android app with same functionality

## 📚 Required Reading (In Order)

1. **STATUS.md** - Current state, what's done, what's next
2. **TODO.md** - Prioritized task list
3. **SUMMARY.md** - Architecture decisions and patterns
4. **README_ANDROID.md** - Build and development instructions
5. **ANDROID_CONVERSION_TODO.md** - Complete phase breakdown (reference)

## 🏗️ Architecture Overview

### Module Structure
```
convos-android/
├── app/              # UI layer (Compose, ViewModels, Navigation)
├── core/             # Business logic (Domain, Database, XMTP)
└── gradle/           # Dependency management
```

### Key Architectural Patterns

1. **Clean Architecture**: Domain → Repository → DAO → Database
2. **Reactive UI**: Flow/StateFlow for data streams
3. **Repository Pattern**: Abstracts data sources
4. **ViewModel Pattern**: UI state management
5. **Dependency Injection**: Manual (for now, may add Hilt later)

### Technology Stack

| Layer | Technology |
|-------|-----------|
| UI | Jetpack Compose + Material 3 |
| State | StateFlow + ViewModel |
| Database | Room (SQLite) |
| Networking | Retrofit + OkHttp |
| Async | Kotlin Coroutines + Flow |
| Security | EncryptedSharedPreferences |
| Images | Coil 3 |
| Serialization | kotlinx.serialization |
| Logging | Timber |
| Messaging | XMTP Android SDK |

## 🎓 Lessons Learned

### 1. Database Design

**What Worked Well**:
- Room's type converters handle complex types elegantly
- WAL mode enables concurrent access (important for notifications)
- Foreign key constraints maintain referential integrity
- Flow-based DAOs provide reactive queries out of the box

**Watch Out For**:
- Room requires exact column/table name matching in queries
- TypeConverters must be registered in database class
- Migration strategy needs to be planned (currently using destructive)
- Index on foreign keys improves query performance significantly

**Example Pattern**:
```kotlin
@Entity(
    tableName = "message",
    foreignKeys = [
        ForeignKey(
            entity = DBConversationEntity::class,
            parentColumns = ["id"],
            childColumns = ["conversation_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("conversation_id")]
)
```

### 2. iOS to Android Mappings

| iOS Concept | Android Equivalent | Notes |
|-------------|-------------------|-------|
| `@Observable` | `StateFlow` | Use `MutableStateFlow` in ViewModels |
| `@State` | `remember { mutableStateOf() }` | Compose state |
| `async/await` | `suspend fun` | Kotlin coroutines |
| `Combine` | `Flow` | Reactive streams |
| `GRDB` | `Room` | SQLite wrapper |
| `Keychain` | `EncryptedSharedPreferences` | Secure storage |
| `UserDefaults` | `SharedPreferences` | Simple KV storage |
| `NotificationCenter` | `LocalBroadcastManager` or `Flow` | Event bus |
| `URLSession` | `Retrofit` | HTTP client |

### 3. Secure Storage

**Key Decision**: EncryptedSharedPreferences over Android Keystore directly

**Why**:
- Simpler API
- Automatic encryption of both keys and values
- Built on Android Keystore under the hood
- Google-maintained (Jetpack Security)

**Implementation**: See `core/src/main/kotlin/com/convos/core/auth/SecureStorage.kt`

### 4. Dependency Management

**Key Decision**: Use version catalog (`libs.versions.toml`)

**Why**:
- Type-safe dependency references
- Centralized version management
- Easier updates
- Better IDE support

**How to Add Dependency**:
1. Add version to `[versions]` section
2. Add library to `[libraries]` section
3. Reference as `libs.library.name` in build.gradle.kts

### 5. Product Flavors

**Three Flavors**: `local`, `dev`, `prod`

**Key Learning**: Each flavor can have its own:
- `google-services.json` (in `src/{flavor}/`)
- Assets (in `src/{flavor}/assets/`)
- Resources (in `src/{flavor}/res/`)
- Source files (in `src/{flavor}/kotlin/`)

**Accessing Flavor**:
```kotlin
BuildConfig.ENVIRONMENT // "local", "dev", or "prod"
```

### 6. Jetpack Compose Patterns

**State Management**:
```kotlin
// ViewModel
class MyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
}

// Composable
@Composable
fun MyScreen(viewModel: MyViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    // Use uiState...
}
```

**Remember**: Use `collectAsStateWithLifecycle()` to avoid memory leaks!

### 7. Flow Best Practices

**DAO Returns Flow**:
```kotlin
@Query("SELECT * FROM conversation")
fun observeAll(): Flow<List<DBConversationEntity>>
```

**Repository Transforms**:
```kotlin
fun getConversations(): Flow<List<Conversation>> {
    return conversationDao.observeAll()
        .map { entities -> entities.map { it.toDomain() } }
}
```

**ViewModel Collects**:
```kotlin
viewModelScope.launch {
    repository.getConversations()
        .collect { conversations ->
            _uiState.update { it.copy(conversations = conversations) }
        }
}
```

## 🚨 Common Pitfalls

### 1. Context Leaks
**Problem**: Storing Activity context in long-lived objects

**Solution**: Always use `applicationContext` for non-UI operations
```kotlin
class MyManager(context: Context) {
    private val appContext = context.applicationContext // ✅
}
```

### 2. Main Thread Database Access
**Problem**: Room doesn't allow DB access on main thread

**Solution**: Always use `suspend` functions or `.asFlow()`
```kotlin
// ❌ Bad
fun getData() = dao.getAll()

// ✅ Good
suspend fun getData() = dao.getAll()
// or
fun observeData() = dao.observeAll()
```

### 3. Forgetting Type Converters
**Problem**: Room doesn't know how to store custom types

**Solution**: Add `@TypeConverter` functions
```kotlin
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }
    
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}
```

### 4. Not Handling Configuration Changes
**Problem**: Composables recreate on rotation

**Solution**: Use ViewModel for state that should survive
```kotlin
// ❌ Bad - Lost on rotation
var items by remember { mutableStateOf(emptyList<Item>()) }

// ✅ Good - Survives rotation
val items by viewModel.items.collectAsStateWithLifecycle()
```

## 🔧 Development Workflow

### When Porting iOS Code

1. **Find the iOS file** in `ConvosCore/Sources/` or `Convos/`
2. **Understand the purpose** - Read comments and structure
3. **Identify dependencies** - What does it use?
4. **Map iOS → Android** - Use table above
5. **Create package structure** - Match logical grouping
6. **Port incrementally** - One class/function at a time
7. **Add TODOs for missing pieces** - Don't block on dependencies
8. **Test as you go** - Write a simple test or run the code

### Example Port Process

**iOS File**: `ConvosCore/Sources/ConvosCore/Storage/Models/Profile.swift`

```swift
// iOS
public struct Profile: Codable {
    public let inboxId: String
    public let name: String?
    
    public var displayName: String {
        name ?? "Someone"
    }
}
```

**Android Port**: `core/src/main/kotlin/com/convos/core/domain/Profile.kt`

```kotlin
// Android
data class Profile(
    val inboxId: String,
    val name: String? = null
) {
    val displayName: String
        get() = name ?: "Someone"
}
```

### Building and Testing

```bash
# Clean build
./gradlew clean

# Build specific variant
./gradlew assembleLocalDebug

# Install on device
./gradlew installLocalDebug

# Run tests
./gradlew test

# Run lint checks
./gradlew lint

# Check for outdated dependencies
./gradlew dependencyUpdates
```

## 📝 Code Conventions

### Naming

```kotlin
// Classes: PascalCase
class ConversationRepository

// Functions/Variables: camelCase
fun loadConversations()
val conversationList

// Constants: SCREAMING_SNAKE_CASE
const val MAX_RETRY_COUNT = 3

// Private members: prefix with _
private val _uiState = MutableStateFlow(UiState())
val uiState = _uiState.asStateFlow()
```

### File Organization

```kotlin
// 1. Package declaration
package com.convos.core.domain

// 2. Imports (Android first, then third-party, then internal)
import android.content.Context
import kotlinx.coroutines.flow.Flow
import com.convos.core.storage.entities.DBConversationEntity

// 3. Constants
private const val TAG = "ConversationRepository"

// 4. Interfaces
interface ConversationRepository {
    fun getConversations(): Flow<List<Conversation>>
}

// 5. Implementation
class ConversationRepositoryImpl : ConversationRepository {
    // ...
}

// 6. Extensions
fun Conversation.isExpired(): Boolean = // ...
```

### Commenting

**When to Comment**:
- Complex algorithms
- iOS port notes: `// Ported from iOS: ConvosCore/.../File.swift`
- TODOs: `// TODO: Implement XMTP integration`
- Important decisions: `// Using WAL mode for concurrent access`

**When Not to Comment**:
- Self-explanatory code
- Restating what the code does
- Over-explaining simple concepts

## 🎯 Priority Guidelines

### What to Port First
1. **Critical Path**: Items marked "CRITICAL" in TODO.md
2. **Dependencies**: Things other features depend on
3. **High Impact**: Features users will notice
4. **Low Complexity**: Easy wins for momentum

### What to Defer
1. **Polish**: Animations, transitions
2. **Edge Cases**: Rare error scenarios
3. **Optimizations**: Make it work, then make it fast
4. **Nice-to-Haves**: Features not in iOS version

## 🧪 Testing Strategy

### What to Test
- **Repository layer**: Data transformations
- **ViewModels**: State management logic
- **DAOs**: Complex queries (Room provides basic testing)
- **Business logic**: Pure functions

### What Not to Test (Yet)
- **Compose UI**: Wait until stable
- **Third-party libraries**: They're tested
- **Trivial getters/setters**: Not worth it

### Example Test

```kotlin
@Test
fun `profile displayName returns name when present`() {
    val profile = Profile(inboxId = "123", name = "Alice")
    assertEquals("Alice", profile.displayName)
}

@Test
fun `profile displayName returns default when name is null`() {
    val profile = Profile(inboxId = "123", name = null)
    assertEquals("Someone", profile.displayName)
}
```

## 🔍 Debugging Tips

### Database Inspector
1. View → Tool Windows → App Inspection
2. Select Database Inspector
3. Browse tables, run queries live

### Logcat Filtering
```
tag:ConvosApp
tag:Convos.*  // All Convos tags
package:com.convos.android.local  // By package
```

### Compose Layout Inspector
Tools → Layout Inspector → Shows Compose hierarchy

### Common Issues

**"Cannot access database on main thread"**
→ Use `suspend` function or `.asFlow()`

**"No TypeConverter found"**
→ Add `@TypeConverters` to database class

**"Unresolved reference"**
→ Sync Gradle, clean build, invalidate caches

**Compose not updating**
→ Ensure using `StateFlow` and `collectAsStateWithLifecycle()`

## 📞 Getting Help

### Documentation
- [Android Developer Docs](https://developer.android.com/)
- [Kotlin Docs](https://kotlinlang.org/docs/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose/documentation)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Flow](https://kotlinlang.org/docs/flow.html)

### In This Repo
- Check iOS implementation for reference
- See similar already-ported files
- Read inline comments and TODOs

## 💡 Wisdom for Future Agents

1. **Don't Over-Engineer**: Match iOS functionality first, optimize later
2. **Port, Don't Rewrite**: Maintain same logic flow as iOS
3. **Test Incrementally**: Don't wait until everything is done
4. **Document Decisions**: Update this file when you learn something
5. **Use TODOs Liberally**: It's okay to leave things unfinished
6. **Commit Often**: Small commits are easier to review and revert
7. **Trust the Architecture**: The foundation is solid
8. **Ask Questions**: Add TODO comments for unclear iOS code
9. **Maintain Parity**: Android should match iOS features
10. **Have Fun**: This is a cool project!

## 🎓 Kotlin/Android Tips for iOS Developers

### Kotlin Equivalents

```kotlin
// Swift: guard let else
val name = user?.name ?: return

// Swift: if let
user?.let { println(it.name) }

// Swift: map/filter/reduce (same!)
list.map { it.name }.filter { it.isNotEmpty() }

// Swift: try-catch
try {
    riskyOperation()
} catch (e: Exception) {
    handleError(e)
}

// Swift: Result type
sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val error: Throwable) : Result<T>()
}
```

### Android Lifecycle
- Activities/Fragments have complex lifecycles
- ViewModels survive configuration changes
- Use `viewModelScope` for coroutines in ViewModels
- Compose manages its own lifecycle

## 📋 Checklist for Each Feature

When porting a feature:

- [ ] Identify all iOS files involved
- [ ] Create corresponding Android package
- [ ] Port models/entities
- [ ] Port repository/data layer
- [ ] Port business logic
- [ ] Port ViewModel
- [ ] Port UI (Compose)
- [ ] Add navigation
- [ ] Test manually
- [ ] Add unit tests
- [ ] Update TODO.md
- [ ] Commit with clear message

## 🎉 Celebrating Wins

**Small Wins**:
- ✅ Successfully built first time
- ✅ Database query returns data
- ✅ First screen renders
- ✅ Navigation works

**Big Wins**:
- 🎊 First message sends
- 🎊 Conversations load
- 🎊 Push notification received
- 🎊 App ready for testing

Remember: Each checkbox checked is progress! 🚀

---

**Last Updated**: October 26, 2025
**Next Agent**: Start with `STATUS.md`, then `TODO.md`, then start porting repositories!

