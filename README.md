# LINE Message Card

A simple Android application that allows you to create beautiful message cards and share them with your friends via LINE messenger **without needing an official LINE account or creating a group**.

## Features

- 📝 **Create Custom Message Cards**: Write personalized messages to your friends
- 🎨 **Choose Card Colors**: Select from multiple color themes (Blue, Pink, Green, Yellow)
- 👤 **Add Your Name**: Optionally include your name on the card
- 👁️ **Preview Your Card**: See how your card will look before sending
- 📤 **Share via LINE**: Send your message card directly to LINE or any other messaging app

## How It Works

This app creates a custom image-based message card that you can share with anyone through LINE messenger. Since it uses standard Android sharing (via Intent), you don't need:
- An official LINE business account
- To create a LINE group
- Any special permissions or API keys

Simply create your message, choose your style, and share it directly with your friends!

## Building the App

### Prerequisites

- Android Studio Arctic Fox or later
- Android SDK 24 or higher
- Gradle 8.1.0 or later

### Build Instructions

1. Clone this repository:
```bash
git clone https://github.com/darekasanga/message.git
cd message
```

2. Open the project in Android Studio

3. Sync Gradle files (Android Studio should prompt you automatically)

4. Build the APK:
```bash
./gradlew assembleDebug
```

5. Install on your device:
```bash
./gradlew installDebug
```

Or simply run the app from Android Studio by clicking the "Run" button.

## Usage

1. **Launch the app** on your Android device
2. **Enter your message** in the text field
3. **Add your name** (optional) in the sender field
4. **Choose a color** for your message card
5. **Tap "Preview Card"** to see how it looks
6. **Tap "Send to LINE"** to share your card
   - If LINE is installed, it will open LINE directly
   - If not, you can choose any other sharing method

## Requirements

- Android 7.0 (API 24) or higher
- LINE app installed (recommended, but not required for sharing via other apps)

## Technical Details

- **Language**: Java
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Build System**: Gradle
- **UI Framework**: Material Design Components

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Contributing

Feel free to submit issues, fork the repository, and create pull requests for any improvements.

## Acknowledgments

- Built for anyone who wants to send beautiful message cards to friends without needing complex setup
- Uses Android's native sharing capabilities for maximum compatibility