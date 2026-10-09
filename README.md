# Quizora

**Play Smart. Know More.**

Quizora is a mini Android quiz application designed to make learning fun, interactive, and engaging. It features quizzes across Computer Science, Android Development, Web Development, Pop Culture, and Sports, with different difficulty levels to challenge users and test their knowledge.

## ✨ Features

- **Simple Sign In:** Sign in using any username and the default password. Your username is remembered on the device.
- **Multiple Quiz Categories:** Explore technical quizzes, trending pop culture questions, and sports trivia.
- **Three Difficulty Levels:** Test your knowledge with Beginner, Intermediate, and Expert quizzes.
- **Interactive Quiz Experience:** Select answers, submit responses, view correct and incorrect answers, and proceed through questions.
- **Shuffled Options:** Answer choices are randomized on each attempt.
- **Progress Tracking:** A progress bar helps you keep track of your quiz.
- **Score & Results:** View your score, percentage, performance message, and new-best indicators.
- **Personal Bests:** Best scores for each quiz level are stored locally and displayed on level cards.
- **Smooth Navigation:** Rotation-safe quiz progress and confirmation before exiting an active quiz.
- **Retry & Explore:** Try a quiz again or switch to another category.

## 📚 Quiz Categories

Quizora includes **5 quiz modes**, each containing 5 questions.

| Category | Topics |
|---|---|
| Beginner | Fundamental Computer Science, Android Development, and Web Development concepts |
| Intermediate | Programming concepts, data structures, and practical development knowledge |
| Expert | Advanced Computer Science and development concepts |
| Pop Culture Quiz | Popular artists, music, movies, TV shows, and entertainment |
| Sports Quiz | Sports trivia, tournaments, athletes, and records |

## 🔄 App Flow

`Splash Screen → Sign In → Choose a Quiz → Answer Questions → View Results`

1. **Splash Screen:** Displays the Quizora logo.
2. **Sign In:** Enter a username and the configured password.
3. **Choose a Quiz:** Select a technical difficulty level, Pop Culture, or Sports.
4. **Take the Quiz:** Select an option, submit your answer, and view the correct response.
5. **View Results:** Check your score, percentage, and personal best.
6. **Play Again:** Retry the current quiz or choose another category.

## 🛠️ Tech Stack

- **Platform:** Android
- **Programming Language:** Kotlin
- **Build System:** Gradle
- **Android Gradle Plugin:** 9.4.1
- **Kotlin Version:** 2.2.10
- **Compile SDK:** 37

## 📁 Project Structure

```text
Quizora/
├── app/
│   └── src/
│       └── main/
│           ├── java/...
│           │   └── QuizData.kt
│           └── res/
│               ├── drawable-nodpi/
│               │   └── app_logo.png
│               └── mipmap-*/
├── store_icon_512.png
└── README.md
```

*Note: The structure above is a simplified overview. Actual package and source file locations may vary.*

## 🚀 Getting Started

### Prerequisites

- Android Studio compatible with the configured Android Gradle Plugin.
- A compatible JDK and Android SDK.
- An Android device or emulator.

### Installation

1. Clone or download this repository.
2. Open the project folder in Android Studio.
3. Allow Gradle to sync and download the required dependencies.
4. Select the `app` run configuration.
5. Run the application on an emulator or connected Android device.

## 🔐 Sign-In Details

- **Username:** Any username
- **Password:** `Mosaic@05!`

The username is remembered on the device for subsequent use.

**Security note:** This is a simple project sign-in flow, not production-grade authentication. Do not reuse the default password for personal or sensitive accounts.

## 🖼️ App Assets

- `app_logo.png` — Main application logo.
- `res/mipmap-*` — Launcher icons, including adaptive icons.
- `store_icon_512.png` — 512 × 512 Play Store icon.

## 📊 Score Tracking

Quizora tracks the best score for each quiz mode locally on the device. After completing a quiz, users can review their score and percentage, see whether they achieved a new personal best, and retry or explore another category.

## 🎯 Project Goal

The goal of Quizora is to combine technical learning with entertainment through short, accessible quizzes. Whether you're revising development concepts or testing your knowledge of pop culture and sports, Quizora offers a simple way to learn, compete with yourself, and improve.

---

**Quizora — Play Smart. Know More.**
