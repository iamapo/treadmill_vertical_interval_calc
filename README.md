# VerticalTreadmillRun

VerticalTreadmillRun is a Compose Multiplatform treadmill elevation interval planner. It lets a runner enter a target elevation gain, total duration, warm-up and cool-down time, treadmill incline limits, pace ranges, and preferred hard/recovery interval structure, then generates a structured workout checklist.

The app is local-state only for now and is organized so persistence, PDF export, and Apple Notes export can be added later without moving the core calculation logic.

## Structure

- [`composeApp/src/commonMain/kotlin/com/redred/treadmillvertiintervallcalc/model`](./composeApp/src/commonMain/kotlin/com/redred/treadmillvertiintervallcalc/model) contains workout input, segment, type, and plan models.
- [`composeApp/src/commonMain/kotlin/com/redred/treadmillvertiintervallcalc/domain`](./composeApp/src/commonMain/kotlin/com/redred/treadmillvertiintervallcalc/domain) contains pace parsing, elevation calculation, and workout generation.
- [`composeApp/src/commonMain/kotlin/com/redred/treadmillvertiintervallcalc/presentation`](./composeApp/src/commonMain/kotlin/com/redred/treadmillvertiintervallcalc/presentation) contains the MVVM state, events, and `WorkoutPlannerViewModel`.
- [`composeApp/src/commonMain/kotlin/com/redred/treadmillvertiintervallcalc/ui`](./composeApp/src/commonMain/kotlin/com/redred/treadmillvertiintervallcalc/ui) contains the Compose Multiplatform screens and cards.

## Build and Run Android

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

## Build and Run Desktop

```shell
./gradlew :composeApp:run
```

## Build and Run iOS

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.
