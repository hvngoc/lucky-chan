# Lucky Love

A cross-platform mobile app built with Kotlin Multiplatform and Jetpack Compose, targeting both Android and iOS.

## App Overview

**Lucky Love** is a playful, magical, and responsive app designed to select the luckiest user among multiple participants by detecting who is touching the screen. The app features:

- **Multi-touch Detection:** Each user places and holds their finger on the screen. Each touch point is visualized with a colorful, pulsing ring animation and labeled with an incremental number.
- **Animated Countdown:** If no new touch points are added within 5 seconds, a countdown from 5 to 0 is displayed with a large, animated scale-down effect.
- **Random Selection:** After the countdown, one of the current touch points is randomly selected. All other points are hidden, and the selected point animates by scaling up to the center of the screen.
- **Haptic Feedback:** When a new touch point is added, the device provides haptic feedback for a more engaging experience.
- **Get Ready Animation:** When the app starts (or after a reset), a 'Get ready' text appears with a scale up/down animation. It disappears as soon as at least one finger touches the screen.
- **Toolbar:** A top app bar displays the app title ("Locky"), a clear/reset action, and a dark mode toggle icon to switch between light and dark themes.
- **Dark/Light Mode:** Users can toggle between dark and light themes at any time using the toolbar icon. The UI adapts accordingly.

## Features

- Cross-platform: Android & iOS support via Kotlin Multiplatform
- Jetpack Compose UI with Compose Multiplatform
- Multi-touch support with animated, colorfu[README.md](../android-nab-wakadana/README.md)l rings for each finger
- Animated countdown and selection effects
- Haptic feedback on new touch
- Top app bar with title, reset, and dark mode toggle
- Animated 'Get ready' state
- Smooth, playful, and magical user experience

## Project Structure

- [`/composeApp`](./composeApp/src): Shared code for Compose Multiplatform applications
  - [`commonMain`](./composeApp/src/commonMain/kotlin): Common code for all targets
  - [`androidMain`](./composeApp/src/androidMain/kotlin): Android-specific code
  - [`iosMain`](./composeApp/src/iosMain/kotlin): iOS-specific code
- [`/iosApp`](./iosApp/iosApp): iOS application entry point (SwiftUI, Xcode project)

## Build and Run

### Android

To build and run the Android app:

- In your IDE, use the run configuration for Android.
- Or from the terminal:
  ```sh
  ./gradlew :composeApp:assembleDebug
  ```

### iOS

To build and run the iOS app:

- In your IDE, use the run configuration for iOS.
- Or open the [`/iosApp`](./iosApp) directory in Xcode and run the project.

## Learn More

- [Kotlin Multiplatform Documentation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Jetpack Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)

---

Enjoy using **Lucky Love** to find out who is the luckiest among your friends!

# Donate
[![N|Solid](https://raw.githubusercontent.com/hvngoc/soulan/master/buymeacoffee.png)](https://www.buymeacoffee.com/ngocjaus)