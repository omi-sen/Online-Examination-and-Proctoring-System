# Online Examination & Proctoring System
 
A Java desktop application built with Swing to simulate an online exam environment with basic proctoring and persistent file-based storage.

## Overview

This project demonstrates an end-to-end exam workflow for a college-level system, including:
- role-based login for admins and students
- admin controls for question management and exam settings
- student exam interface with timed delivery and answer review
- simple proctoring by monitoring window focus changes
- result calculation and storage
- activity logging for suspicious behavior

## Key Features

### Admin capabilities
- Add new multiple-choice questions
- Manage existing questions (edit/update)
- Set exam metadata, including title and duration
- File-based persistence for questions and exam settings

### Student experience
- Login as a student and launch the exam
- Navigate questions with previous/next controls
- Flag questions for review before submitting
- Track remaining exam time with a countdown timer
- Automatic submission when time expires
- View scored results and answer summary after submission

### Proctoring and logging
- Detects when the exam window loses focus
- Logs suspicious activity to `data/activity_log.txt`
- Auto-submits the exam after a configurable number of violations
- Records violation count and event type for each student

## Project Structure

- `src/app/Main.java` — application entry point
- `src/gui/` — Swing UI classes for login, dashboards, exam screens, and result display
- `src/model/` — domain models for users, exams, questions, results, and activity logs
- `src/service/` — business logic for authentication, exam loading, question management, results, and proctoring
- `src/storage/` — file handlers for users, questions, exam settings, results, and activity logs
- `src/util/` — shared utilities for constants, validation, timer handling, and date/time formatting

## Data Files

The application uses simple file-based persistence under the `data/` folder:
- `data/users.json` — stored users and credentials
- `data/questions.txt` — saved exam questions
- `data/results.txt` — recorded student exam results
- `data/activity_log.txt` — proctoring and suspicious activity logs
- `data/exam_settings.txt` — current exam title and duration

## Default Credentials

- Admin: `admin` / `admin123`
- Student: `student1` / `stud123`

## Running the Application

Compile and run the project using a Java-compatible IDE or from the command line.

Example command-line workflow:

```bash
javac -d bin src/app/Main.java src/gui/*.java src/model/*.java src/service/*.java src/storage/*.java src/util/*.java
java -cp bin app.Main
```

> Note: Ensure the `data/` directory is present and contains the default files before starting the application.>
<br><br>
Name: Shourya Rawat
<br>
repository link : https://github.com/omi-sen/Online-Examination-and-Proctoring-System.git
