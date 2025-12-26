# Project Overview - LINE Message Card

## Purpose
This Android application solves the problem of sending personalized message cards to friends via LINE messenger without requiring:
- An official LINE business account
- Creating a LINE group
- Any special API keys or server setup

## Architecture

### Technology Stack
- **Language**: Java
- **Build System**: Gradle 8.0
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **UI Framework**: Material Design Components

### Key Components

#### 1. MainActivity.java
The main and only activity that handles:
- User input collection (message, sender name)
- Color theme selection
- Message card bitmap generation
- Preview functionality
- Sharing via Android Intent system

#### 2. Layout (activity_main.xml)
Material Design-based UI featuring:
- ScrollView for accessibility on smaller screens
- MaterialCardView for clean component grouping
- EditText fields for message and sender input
- RadioGroup for color selection
- Preview area that shows/hides dynamically
- Action buttons (Preview, Send to LINE)

#### 3. Resources
- **Strings**: Internationalization-ready string resources
- **Themes**: Material Design theme with LINE-inspired colors
- **Colors**: Launcher icon colors
- **Drawables**: Custom launcher icon
- **XML**: FileProvider paths for secure file sharing

### How It Works

1. **User Input**: User enters message, optional name, and selects color
2. **Preview Generation**: Creates a temporary preview in the UI
3. **Bitmap Creation**: Generates a PNG image with:
   - Selected background color
   - White border
   - Message text (with word wrapping)
   - Sender name
4. **File Handling**: Saves bitmap to app cache using FileProvider
5. **Sharing**: Uses Android Intent.ACTION_SEND to share:
   - First tries to open LINE specifically
   - Falls back to generic share dialog if LINE not installed

### Security Features

✅ **No Data Collection**: All processing happens locally
✅ **Secure File Sharing**: Uses FileProvider instead of file:// URIs
✅ **Cache Management**: Images stored in app cache (auto-cleaned by system)
✅ **No Network Permissions**: Works completely offline
✅ **Resource Cleanup**: Proper use of try-with-resources for I/O

### Code Quality

The codebase follows Android best practices:
- Proper resource management (try-with-resources)
- Efficient string operations (StringBuilder)
- Material Design guidelines
- AndroidX compatibility
- Null safety considerations

## Build Process

```bash
# Clone repository
git clone https://github.com/darekasanga/message.git

# Build APK
./gradlew assembleDebug

# Install on device
./gradlew installDebug
```

## Testing Approach

While automated tests are not included (minimal change principle), the app should be manually tested for:
1. Message input and validation
2. Color selection functionality
3. Preview generation
4. Bitmap creation with various message lengths
5. Sharing to LINE (if installed)
6. Sharing to other apps (fallback)
7. Handling of empty inputs
8. UI responsiveness on different screen sizes

## Future Enhancements (Out of Scope)

Potential improvements that could be added:
- Custom fonts selection
- Image/photo backgrounds
- More color themes
- Animation effects
- Message templates
- Multi-language support
- Dark mode
- Card size customization
- History of sent cards

## Files Structure

```
message/
├── app/
│   ├── build.gradle                    # App-level build config
│   ├── proguard-rules.pro             # ProGuard configuration
│   └── src/main/
│       ├── AndroidManifest.xml         # App manifest
│       ├── java/com/darekasanga/linemessagecard/
│       │   └── MainActivity.java       # Main application logic
│       └── res/
│           ├── drawable/               # App icons
│           ├── layout/                 # UI layouts
│           ├── mipmap-*/              # Launcher icons
│           ├── values/                # Strings, colors, themes
│           └── xml/                   # FileProvider paths
├── gradle/                            # Gradle wrapper files
├── build.gradle                       # Project-level build config
├── settings.gradle                    # Project settings
├── gradle.properties                  # Gradle properties
├── README.md                          # Main documentation
├── USER_GUIDE.md                      # End-user guide
└── LICENSE                           # Apache 2.0 License
```

## Compliance & Licensing

- **License**: Apache License 2.0
- **Privacy**: No data collection or external communication
- **Open Source**: All code available for review and contribution

## Success Criteria Met

✅ Users can create custom message cards
✅ Users can personalize with their own text
✅ Users can choose from multiple color themes
✅ Users can preview before sending
✅ Users can share to LINE without official account
✅ Works without groups or complex setup
✅ Clean, maintainable code
✅ Comprehensive documentation
✅ No security vulnerabilities
