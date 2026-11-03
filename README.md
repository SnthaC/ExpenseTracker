# ExpenseTracker

An Android expense-tracking app built with Kotlin and Jetpack Compose.

The app allows users to record expenses, manage a monthly budget and monitor their spending through a dashboard with category breakdowns.

## Features

- Add, edit and delete transactions.
- Assign transactions to expense categories.
- Set and save a monthly budget.
- View daily and monthly spending.
- Display spending distribution with a donut chart.
- Store transactions and budget data locally using Room.

## Built With

- Kotlin
- Jetpack Compose
- Material 3
- Room
- Kotlin Coroutines and Flow
- Hilt
- MVVM
- Gradle Version Catalog

## Project Structure

The project is organised into three main layers:

- UI: Compose screens, components, ViewModels and UI state.
- Domain: Models, repository interfaces and use cases.
- Data: Room database, DAOs, entities, mappers and repository implementations.

## Testing

The core application is implemented. Automated testing is planned next.

- Unit tests
- Repository and ViewModel tests
- Compose UI tests
- GitHub Actions for automated builds and tests
