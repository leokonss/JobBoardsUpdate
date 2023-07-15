# Job Board profiles updater
***
Updates different Job board profiles (for now - only **Dice** profile) automatically on your schedule running the script in GitHub Actions workflow, so your profile will be up-to-date.
***
## Installation
* Fork the repository to your GitHub account
* Create 2 GitHub Actions secrets (Settings - Secrets and variables - Actions - New repository secret) with names - **DICE_EMAIL**, **DICE_PSWD** (and your Dice email and password as values).
* Change your update schedule in ci.yml file (the profile is updated at 7AM and 12PM CDT by default). More information about the schedule:
  [schedule](https://docs.github.com/en/actions/using-workflows/events-that-trigger-workflows#schedule)