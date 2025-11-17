# Git Repository Setup Checklist

## Initial Setup (Project Owner - Do Once)

- [ ] 1. Initialize Git repository (if not already done)

  ```bash
  git init
  ```

- [ ] 2. Verify the Gradle wrapper is present

  - [ ] `gradlew` exists
  - [ ] `gradlew.bat` exists
  - [ ] `gradle/wrapper/gradle-wrapper.jar` exists
  - [ ] `gradle/wrapper/gradle-wrapper.properties` exists

- [ ] 3. Create/verify `.gitignore` is properly configured

- [ ] 4. Add all project files

  ```bash
  git add .
  ```

- [ ] 5. Verify `local.properties` is NOT staged (should be ignored)

  ```bash
  git status
  # local.properties should NOT appear in the list
  ```

- [ ] 6. Make initial commit

  ```bash
  git commit -m "Initial commit: Android project setup"
  ```

- [ ] 7. Create GitHub repository

  - Go to https://github.com/new
  - Create new repository (public or private)
  - Do NOT initialize with README, .gitignore, or license (you already have these)

- [ ] 8. Push to GitHub

  ```bash
  git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
  git branch -M main
  git push -u origin main
  ```

- [ ] 9. Share repository URL with team members

- [ ] 10. (Optional) Protect main branch
  - Go to repository Settings → Branches
  - Add branch protection rule for `main`
  - Require pull request reviews before merging

## Team Member Setup (Each Person)

- [ ] 1. Install prerequisites

  - [ ] Java JDK 11 or higher
  - [ ] Android Studio (latest stable)
  - [ ] Git

- [ ] 2. Clone repository

  ```bash
  git clone <repository-url>
  cd FinalProject
  ```

- [ ] 3. Open in Android Studio

  - File → Open → Select FinalProject folder

- [ ] 4. Wait for Gradle sync to complete

  - This may take 5-10 minutes first time
  - Downloads Gradle 8.13 automatically
  - Downloads all dependencies

- [ ] 5. Accept any SDK license agreements

- [ ] 6. Install any missing SDK platforms (if prompted)

- [ ] 7. Verify build works

  - Build → Make Project (Ctrl+F9)
  - Should complete without errors

- [ ] 8. Run on device/emulator
  - Ensures everything is working

## Ongoing Collaboration

### Before Starting Work

```bash
git checkout main
git pull origin main
git checkout -b feature/your-feature-name
```

### After Making Changes

```bash
git add .
git commit -m "Description of changes"
git push origin feature/your-feature-name
```

### Create Pull Request

- Go to GitHub repository
- Click "Pull requests" → "New pull request"
- Select your feature branch
- Add description and create PR
- Wait for team review

### After PR is Merged

```bash
git checkout main
git pull origin main
git branch -d feature/your-feature-name
```

## Common Issues & Solutions

### Issue: "Gradle version mismatch"

**Solution:**

```bash
# Delete .gradle folder
rm -rf .gradle
# In Android Studio: File → Invalidate Caches → Restart
```

### Issue: "SDK not found"

**Solution:** File → Settings → Android SDK → Install required SDK platforms

### Issue: Different build results on different machines

**Verify:**

- [ ] Everyone is using the Gradle wrapper (`./gradlew` not global `gradle`)
- [ ] Everyone has the same JDK version (check with `java -version`)
- [ ] Everyone has synced to the same git commit
- [ ] No one has modified version numbers in `libs.versions.toml`
- [ ] No one has committed `local.properties`

### Issue: Merge conflicts in build files

**Prevention:**

- Don't modify version numbers without team discussion
- Don't add new dependencies without team agreement
- Communicate before making structural changes

## Quick Reference

### Check Git status

```bash
git status
```

### See what changed

```bash
git diff
```

### See commit history

```bash
git log --oneline
```

### Switch branches

```bash
git checkout branch-name
```

### Update from main

```bash
git checkout main
git pull origin main
```

### Build from command line

```bash
# Windows
gradlew.bat build

# macOS/Linux
./gradlew build
```
