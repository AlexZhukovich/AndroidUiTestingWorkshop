# Workshop: Android UI Testing

During this workshop, we will cover the following topics:
- Introduction to Android testing
- How to interact with `Activity`, `Fragment`, `Jetpack Compose` in Android UI tests
- What is "End-to-End(E2E) testing"?
- What is "Functional testing"?
- What is "Screenshot testing"?
- How to apply E2E, functional and screenshot testing techniques to the testing Android apps?
- Which frameworks we can use for testing Android apps?

The **"mood-tracker-android"** is an Android application that allows you to collect information about the emotional state during the day.

The **"mood-tracker-api"** is an API that allows you to store information about users. This project created for demo in this project as it doesn’t save emotional state data in the remote database.
 
## Requirements

To configure and run an "Android app" and "API" projects, I recommend using the following IDEs:
- [Android Studio](https://developer.android.com/studio)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- [Maestro](https://maestro.mobile.dev/getting-started/installing-maestro)

## How to set up the projects

### The "mood-tracker-api" configuration

This project requires a few environment variables which can be added in the IntelliJ IDEA:
- `SECRET_KEY`
- `SESSION_ENCRYPT_KEY` (HEX string)
- `SESSION_AUTH_KEY` (HEX string)

### Android emulator

I recommend to an emulator with the following characteristics: 
- Pixel 6
- Android 11 Google APIs

## Test case execution

To execute screenshot tests which uses the ["shot"](https://github.com/pedrovgs/Shot) library, we can use the following commands:
- `./gradlew :app:debugExecuteScreenshotTests` - execute all screenshot tests
- `./gradlew :app:debugExecuteScreenshotTests -Precord` - execute all screenshot and update screenshots

