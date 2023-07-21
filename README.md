# Job Board profiles updater
***
Updates different Job board profiles (for now - only **Dice** and **Monster** profiles) automatically on your schedule running the script in GitHub Actions workflow, so your profiles will be up-to-date.
***
## Installation
* Fork the repository to your GitHub account
* Create GitHub Actions secrets (Settings - Secrets and variables - Actions - New repository secret) with names:
   * **DICE_EMAIL**, **DICE_PSWD** (and your Dice profile email and password as values);
   * **MONSTER_EMAIL**, **MONSTER_PSWD** (and your Monster profile email and password as values).
* (Optional) Update the run schedule in ci.yml file (the profile is updated at 7AM and 12PM CDT by default). More information about the schedule:
  [schedule](https://docs.github.com/en/actions/using-workflows/events-that-trigger-workflows#schedule)