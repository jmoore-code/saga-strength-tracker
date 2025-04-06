# SagaStrengthTracker

A streamlined, back-to-basics workout tracking application that prioritizes ease of program creation and essential tracking features, avoiding the bloat and complexity of competing apps.

## Project Vision

SagaStrengthTracker helps fitness enthusiasts track their strength training journey with a clean, intuitive interface. 

## Key Features

### MVP (Minimum Viable Product)
- **Intuitive Workout Program Creation**
    - Simple interface for building workout routines
    - Template-based workouts for quick starts
    - Easy exercise search and configuration

- **Essential Tracking**
    - Sets, reps, and weight tracking
    - Rest timer functionality
    - Workout history and progress visualization

- **User Account Management**
    - Secure authentication
    - Cloud synchronization
    - Profile management

## Tech Stack

- **Backend**: Java 17 + Spring Boot
- **Database**: PostgreSQL
- **Frontend**: React Native
- **Build System**: Gradle
- **Cloud Infrastructure**: AWS-compatible architecture

## Project Structure

```
com.sagastrengthtracker
├── model            # Entity models
├── repository       # Data access layer
├── service          # Business logic
├── controller       # REST API endpoints
├── dto              # Data Transfer Objects
├── config           # Configuration classes
├── exception        # Custom exceptions
└── util             # Utility classes
```

## Entity Model

- **User**: Authentication and profile information
- **WorkoutProgram**: Collection of workout days forming a complete program
- **WorkoutDay**: Specific day in a program with ordered exercises
- **Exercise**: Standard or custom exercises with properties
- **WorkoutExercise**: Configuration of an exercise within a workout (sets, reps, etc.)
- **WorkoutSession**: Record of a completed workout
- **ExerciseLog**: Individual set performance in a workout session
- **MuscleGroup**: Categories for muscles targeted by exercises
- **Tag**: Labels for organizing workout programs

## Development Roadmap

1. **MVP Development** (3-4 months)
    - Core backend functionality
    - Basic user interface
    - Essential tracking features

2. **Polish & Launch** (1-2 months)
    - UI/UX improvements
    - Performance optimization
    - Testing and bug fixes

3. **Feature Expansion** (Ongoing)
    - AI integration
    - Exercise library expansion
    - User-requested enhancements

## Getting Started

### Prerequisites
- Java 17+
- PostgreSQL 14+
- Gradle 7.5+

### Setup Instructions

1. Clone the repository
   ```bash
   git clone https://github.com/your-username/saga-strength-tracker.git
   cd saga-strength-tracker
   ```

2. Configure the database
    - Create a PostgreSQL database named `saga_strength_tracker`
    - Update `application.properties` with your database credentials

3. Build the project
   ```bash
   ./gradlew build
   ```

4. Run the application
   ```bash
   ./gradlew bootRun
   ```

## API Documentation

API documentation will be available via Swagger UI at `/swagger-ui.html` when the application is running.


## License

This project is proprietary and confidential. All rights reserved. See the LICENSE file for details.
This software is not open source and requires explicit permission for any use, modification, or distribution.