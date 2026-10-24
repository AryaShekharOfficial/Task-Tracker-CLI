# Task Tracker CLI

A simple Command Line Interface (CLI) application built to track and manage your daily tasks. This application helps you stay organized by categorizing tasks into "todo", "in-progress", and "done", saving all data locally in a JSON file.

This project was built following the backend project challenge on [roadmap.sh](https://roadmap.sh/projects/task-tracker).

## Features

- **Create, Read, Update, and Delete (CRUD)** tasks from the terminal.
- **Mark Status:** Mark tasks as `todo`, `in-progress`, or `done`.
- **Filtered Listings:** View all tasks with or without details, or also filter specifically by status.
- **Local Storage:** Tasks are managed via a local JSON file.
- **Zero Dependencies:** Built strictly using native modules without external libraries.

## Prerequisites

Before running this project, ensure you have JDK 17 or higher installed.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/AryaShekharOfficial/Task-Tracker-CLI.git
   ```

2. Enter the project directory:
   ```bash
   cd Task-Tracker-CLI
   ```

3. Run the build script(For Linux):
    ```bash
    #Make the build script executable
    chmod +x build

    #Run the build script
    ./build
    ```

    Run the build script(For Windows):
    ```bash
    #Run the build script
    build.bat
    ```
4. Run the following command to get started:
    ```bash
    task-cli
    ```

## Usage

Below is the list of available commands.

1. **Adding a new task**
    ```bash
    task-cli add "Buy groceries"
    ```

2. **Updating a task**
    ```bash
    task-cli update 1 "Buy groceries and cook dinner"
    ```

3. **Deleting a task**
    ```bash
    task-cli delete 1
    ```

4. **Changing task status**
    ```bash
    # Mark as in-progress
    task-cli mark-in-progress 1

    # Mark as done
    task-cli mark-done 1
    ```

5. **Listing tasks (Without details)**
    ```bash
    # List all tasks
    task-cli list

    # List tasks by specific status
    task-cli list todo
    task-cli list in-progress
    task-cli list done
    ```

6. **Listing tasks (With details)**
    ```bash
    # List all tasks with details
    task-cli list details

    # List tasks with details by specific status
    task-cli list details todo
    task-cli list details in-progress
    task-cli list details done
    ```

## Data Structure

Tasks are automatically written to a `tasks.json` file in the current directory. Each task element in the JSON file looks like:

```json
{
    "###id" : "1###",
    "###description" : "Go to Home###",
    "###timeCreated" : "2026-10-24T11:16:40.343902843Z###",
    "###timeUpdated" : "2026-10-24T11:16:40.347182976Z###",
    "###status" : "todo###"
}
```

## License

This project is open-source and available under the [MIT License](LICENSE).
