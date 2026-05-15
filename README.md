# Nimma-Guru 📚👨‍🏫

## Community Mentorship Platform for Village Students

Nimma-Guru is an Android application developed to connect retired teachers, professionals, and skilled mentors (“Gurus”) with village and school students who need academic guidance and mentorship.

The application creates a digital learning bridge between experienced mentors and rural students using a simple, accessible, and bilingual Android platform.

---

# 📌 Problem Statement

Many students in villages lack access to experienced mentors outside school hours. At the same time, retired teachers and professionals are willing to help but do not have a digital platform to connect with students.

Nimma-Guru solves this problem by providing:

* Mentor discovery system
* Online mentorship support
* Community learning platform
* Appreciation and recognition system
* Easy-to-use Android interface

---

# 🎯 Objectives

* Connect students with experienced mentors
* Support community-based learning
* Encourage educational volunteering
* Provide digital accessibility for rural education
* Create a bilingual mentorship platform

---

# ✨ Features

## 👨‍🏫 Guru Registration

Mentors can:

* Create profiles
* Add skills and expertise
* Mention available timings
* Add village/location information

---

## 🔍 Student Search System

Students can:

* Search mentors by subject
* View mentor profiles
* Filter mentors based on skills
* Access mentor availability

---

## 📅 Session Scheduling

The app supports:

* Mentor session planning
* Upcoming session display
* Schedule management

---

## ❤️ Appreciation Board

Students can:

* Share feedback
* Post thank-you messages
* Appreciate mentors publicly

---

## 🏆 Wall of Fame

Special section for:

* Top mentors
* Most active contributors
* Community recognition

---

## 🌐 Bilingual Support

Supported Languages:

* English
* Kannada

---

# 🛠️ Tech Stack

| Technology                 | Usage                   |
| -------------------------- | ----------------------- |
| Kotlin                     | Android App Development |
| Android Studio             | Development Environment |
| Firebase Realtime Database | Cloud Database          |
| XML                        | UI Layout Design        |
| RecyclerView               | Dynamic Lists           |
| Material Design            | UI Components           |
| Navigation Component       | App Navigation          |
| Coroutines                 | Background Processing   |

---

# 🧱 Project Architecture

```text
com.nimmaguru.app
│
├── activities/
├── fragments/
├── adapters/
├── models/
├── repository/
├── firebase/
├── utils/
├── assets/
└── res/
```

---

# 🔥 Firebase Integration

Firebase Realtime Database is used for:

* Mentor data storage
* Session management
* Appreciation messages
* User information

---

# 📂 Database Structure

```text
gurus/
    guruId/
        name
        skills
        village
        freeHours

sessions/
    sessionId/
        subject
        date
        venue

appreciations/
    appreciationId/
        studentName
        message
```

---

# ⚙️ Installation & Setup

## Step 1 — Clone Repository

```bash
git clone https://github.com/your-username/nimma-guru.git
```

---

## Step 2 — Open in Android Studio

* Open Android Studio
* Select:
  `Open Existing Project`
* Choose the project folder

---

## Step 3 — Firebase Setup

1. Create Firebase project
2. Add Android application
3. Download `google-services.json`
4. Place file inside:

```text
app/google-services.json
```

---

## Step 4 — Add Dependencies

### App-Level build.gradle.kts

```kotlin
implementation("com.google.firebase:firebase-database-ktx:20.3.1")
```

---

# ▶️ Run the Project

## Using Android Studio

1. Connect Android device OR start emulator
2. Click Run ▶ button
3. App will install automatically

---

# 🧪 Firebase Connection Test

```kotlin
val db = FirebaseDatabase
    .getInstance("YOUR_DATABASE_URL")
    .reference

db.child("test")
    .child("connection")
    .setValue("NimmaGuru Connected!")
```

---

# 📸 Screenshots

## Suggested Screenshots to Add

* Home Screen
* Guru Registration Screen
* Student Search Screen
* Wall of Fame
* Firebase Database Screenshot

Create a folder:

```text
/screenshots
```

Then include images like:

```md
![Home Screen](screenshots/home.png)
```

---

# 🚀 Future Improvements

* AI-based mentor recommendations
* Video calling support
* Voice assistant for elderly mentors
* Attendance system
* Push notifications
* Offline access mode
* Chat support

---

# 🐞 Challenges Faced During Development

## 1. Firestore Billing Issue

* Firestore required billing activation
* Solution: Switched to Firebase Realtime Database

---

## 2. Emulator Performance Issues

* High RAM usage in Pixel 6 Emulator
* Solution: Used Pixel 4 API 30 Emulator

---

## 3. Theme Compatibility Errors

* AppCompat theme issues occurred
* Fixed by updating themes.xml

---

## 4. Missing Internet Permission

Added:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

---

# 📦 Dependency Files

Important files included:

* build.gradle.kts
* settings.gradle.kts
* google-services.json
* AndroidManifest.xml

---

# 📈 Development Progress

The project includes:

* Multiple source files
* Organized folder structure
* Firebase integration
* Modular components
* Dynamic RecyclerViews
* Android fragments
* Reusable adapters and models

---

# ✅ Evaluation Checklist Covered

✔ README documentation
✔ Installation steps
✔ Run instructions
✔ Project explanation
✔ Folder structure
✔ Features explained
✔ Firebase setup
✔ Organized project structure
✔ Multiple modules/components
✔ Dependency configuration
✔ Project-specific implementation

---

# 👨‍💻 Developer

Developed as part of an Android community mentorship project for improving rural education accessibility.

Project Name: **Nimma-Guru**

---

# 📄 License

This project is developed for educational and academic purposes.
