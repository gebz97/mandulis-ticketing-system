# Mandulis Ticketing System

## Short term goals:
- Get a functional Backend API (With core ticketing features) working
- Build a minimal UI

### 1.1 Basic ticketing
- Basic CRUD on a ticket
- Tag System (No projects)
- Basic assignment to one or more users and/or one group

### 1.2 Basic user features
- Registration
- Login
- Other CRUD operations
- Basic RBAC (User/Agent/Manager/Admin)

### 1.3 Reporting
- Filtering
- Aggregations

---

### 2.1 Frontend Stack

Currently the frontend stack is react


### Requirements:
1. Java 21+
2. PostgreSQL server (16+)
3. Maven

### How to run the API:
1. Create a file called ```.env``` in the root of the API with the following:
```
DB_PASSWORD=<USERNAME>
DB_USERNAME=<PASSWORD>
DB_URL=jdbc:postgresql://<HOST>:<PORT>/<DATABASE>
JWT_SECRET=<Generated JWT Secret from ./scripts/Generate-Jwt.ps1>
```
2. Run the following command in a terminal:
```
mvn clean spring-boot:run
```

---

## Version Control Guidelines

This project uses Git for version control. To ensure a clean and organized history, please follow these version control guidelines when contributing.

### Branching Strategy

We follow a simplified [Gitflow workflow](https://nvie.com/posts/a-successful-git-branching-model/):

1. **Main Branch (`main`)**:
   - This branch always contains the stable production-ready code.
   - Only merge features that are thoroughly tested.

2. **Development Branch (`dev`)**:
   - This is the default branch for active development.
   - All new features, bug fixes, and enhancements should be merged here before being considered for release.

3. **Feature Branches**:
   - Naming convention: `feature/feature-name`
   - Use these branches for developing new features. Once completed and tested, open a pull request to merge into `dev`.

4. **Hotfix Branches**:
   - Naming convention: `hotfix/issue-or-bug-name`
   - Used for critical fixes that need to be applied directly to the `main` branch. After merging into `main`, the changes should also be merged back into `dev`.

5. **Release Branches**:
   - Naming convention: `release/version-number`
   - Used for finalizing a release (e.g., testing, documentation updates) before merging into `main` and tagging a version.

### Commit Messages

Use clear and concise commit messages that describe the changes you’ve made. Follow this convention:

```
[type]: [short description]
```

- **Types**:
  - `feat`: A new feature
  - `fix`: A bug fix
  - `docs`: Documentation changes
  - `style`: Code style changes (formatting, etc.)
  - `refactor`: Code restructuring without changing behavior
  - `test`: Adding or updating tests
  - `chore`: Routine tasks like updating dependencies

**Examples**:
- `feat: add user authentication flow`
- `fix: resolve NPE in TicketService`
- `docs: update API endpoint details in README`

### Pull Requests

When submitting a pull request:

1. Ensure your branch is up-to-date with the `dev` branch.
2. Rebase if necessary to avoid merge conflicts.
3. Provide a clear and detailed description of what the PR does.
4. Include references to any related issues (e.g., `Fixes #45`).
5. Ensure all tests pass before requesting a review.

### Tagging and Versioning

We use [Semantic Versioning](https://semver.org/) for tagging releases:

- **Format**: `vMAJOR.MINOR.PATCH`
  - `MAJOR`: Incompatible API changes
  - `MINOR`: Backward-compatible functionality
  - `PATCH`: Backward-compatible bug fixes

**Examples**:
- `v1.0.0`: First stable release
- `v1.1.0`: Adds new functionality while remaining compatible
- `v1.1.1`: Fixes a bug in a backward-compatible manner

### Keeping History Clean

- **Rebasing vs. Merging**: When working on a feature branch, prefer rebasing onto `dev` instead of merging `dev` into your branch. This keeps history linear and avoids unnecessary merge commits.
  - Before merging into `dev`, use:
    ```bash
    git pull --rebase origin dev
    ```

- **Squash Commits**: For small changes or fixes, consider squashing commits to avoid cluttering the history.

### Handling Merge Conflicts

When resolving conflicts:

1. Use meaningful resolution messages.
2. Test your changes after resolving conflicts to ensure everything works as expected.

### Cleaning Up Branches

After merging a branch (feature, hotfix, release), delete it to keep the repository tidy.

---
