Build a cross-platform mobile app using Kotlin Multiplatform and Jetpack Compose.
App Name: Lucky Love
Target Platforms: Android & iOS
Description:
The app consists of a single main screen.
Goal: Select the luckiest user among multiple users by detecting who is touching the screen.
User Interaction:
Users place and hold their fingers on the screen.
Each touch point generates a colorful, pulsing ring animation (40dp radius) at the touch location.
Each finger should display its own animated ring.
Each touch point is also labeled with an incremental number (1, 2, 3, ...) to differentiate between users.
The new point should be far from each other at least 50dp
Game Logic:
If no new touch points are added within 5 seconds, start a countdown from 5 to 0.
After the countdown:
Randomly select one of the current touch points.
Hide all other touch points, and animate the selected one by scaling it up to the center of the screen.
If a new finger touches the screen before the countdown ends, cancel the countdown and reset the timer.
Visual & UX:
Smooth, colorful ring animations for each touch.
Scale animation for the selected "lucky" touch point.
Should feel playful, magical, and responsive.