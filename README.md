# Mandulis Ticketing System
---

## Product Vision Map

### **1. Product Overview**

The Mandulis Ticketing System is an open-source issue and ticket management platform built using Spring Boot and React. It offers key functionalities such as ticket creation, assignment, prioritization, and collaboration tools.

### **2. Target Audience**

- **Small to Medium Enterprises (SMEs)**: Businesses needing internal or customer-facing issue tracking.
- **Open Source Contributors**: Developers looking for a flexible ticketing system that can be easily customized and extended.
- **Organizations with DevOps Teams**: Teams focused on issue tracking, incident management, and customer support.

### **3. Short-Term Plan (0-6 Months)**

#### **Goals:**
1. **MVP Release (v1.0.0)**:
   - Core ticketing features (create, update, delete, assign tickets).
   - User management (authentication, authorization, roles).
   - Basic reporting and filtering capabilities (by status, priority, etc.).
   - API documentation using Swagger.
   - Simple UI with basic workflows for managing tickets.

2. **API Enhancements**:
   - Implement pagination and sorting in key API endpoints.
   - Improve error handling and responses.
   - Secure the API with JWT and Spring Security.

3. **Frontend MVP**:
   - Have clear UI design requirements
   - Get a minimal functional Frontend UI working

5. **Testing and Stability**:
   - Implement unit and integration tests for critical components.
   - Set up CI/CD pipelines for automated builds and tests.

#### **Milestones:**
- **Version 0.2.0**: Initial stable development branch with core features and a UI MVP.
- **Version 0.3.0**: Enhanced Security features with authorization and/or permissions, and the attachment features.
- **Version 1.0.0**: First official release (MVP) with all core features and stable UI.

### **4. Long-Term Plan (6+ Months)**

#### **Goals:**
1. **Advanced Features**:
   - Implement SLAs and notifications for overdue tickets.
   - Introduce customizable workflows and automation (e.g., automatic ticket assignment).
   - Multi-tenant support for organizations.

2. **Scalability and Performance**:
   - Optimize database queries and indexing for large datasets.
   - Introduce caching strategies for frequent API requests.
   - Improve deployment strategies (e.g., containerization with Docker, Kubernetes).

3. **Extensibility and Integrations**:
   - Provide webhook support for external integrations (e.g., Slack, Microsoft Teams).
   - Create a plugin system for third-party extensions.
   - Integration with event streaming platforms (Kafka, RabbitMQ, etc..)

4. **Enhanced Analytics and Reporting**:
   - Build dashboards for tracking ticket resolution times, team performance, and SLA adherence.
   - Add export options (CSV, PDF) for reports.

5. **Community and Documentation**:
   - Develop comprehensive developer and user documentation.
   - Establish contribution guidelines and foster community engagement (issue tracking, discussions).
   - Organize regular releases based on community feedback and contributions.

#### **Milestones:**
- **Version 2.0.0**: Introduce advanced features, multi-tenant support, and improved scalability.
- **Version 3.0.0**: Fully extensible platform with plugin support, integrations, and analytics.
- **Ongoing Releases**: Regular updates focusing on stability, community contributions, and feature enhancements.

---
## Local Development

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
