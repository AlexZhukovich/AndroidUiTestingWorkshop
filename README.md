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

I recommend to an emulator with the following parameters: 
- Pixel 5
- Android 12 Google APIs (API 31)

## Test case execution

### The "Shot" library
To execute screenshot tests which uses the ["shot"](https://github.com/pedrovgs/Shot) library, we can use the following commands:
- `./gradlew :app:debugExecuteScreenshotTests -Pandroid.testInstrumentationRunnerArguments.annotation=com.alexzh.moodtracker.annotation.AppScreenshotTest` - execute all screenshot tests
- `./gradlew :app:debugExecuteScreenshotTests -Precord -Pandroid.testInstrumentationRunnerArguments.annotation=com.alexzh.moodtracker.annotation.AppScreenshotTest` - execute all screenshot and update screenshots

### The "Maestro" framework

The "Maestro" framework includes the "Maestro Studio", you can run it by executing the `maestro studio` command.

To execute test "Maestro" test cases, you can use the `maestro test --format junit e2e-flow-test` command.

## Helpful links
- [Local and Instrumentation tests in Android](https://alexzh.com/local-and-instrumentation-tests-in-android/)
- [Testing Android Fragment in isolation](https://alexzh.com/testing-android-fragment-in-isolation/)
- [How to group Android tests](https://alexzh.com/how-to-group-android-tests/)
- [Get String resources in Jetpack Compose Tests](https://alexzh.com/get-string-resources-in-jetpack-compose-tests/)
- [Jetpack Compose: Testing animations](https://alexzh.com/jetpack-compose-testing-animations/)
- [UI testing of Android Runtime Permissions](https://alexzh.com/ui-testing-of-android-runtime-permissions/)
- [Maestro framework: documentation](https://maestro.mobile.dev)
