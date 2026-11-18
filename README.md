# SproutSignal v1 (clean build)

Spark's clean rebuild of the SproutSignal Android project.

- Same app content (v0.6 screens in `app/src/main/assets/index.html`), proper project structure
- Real launcher icon (adaptive icon: green background, white sprout)
- Version 1.0.0 (versionCode 2)
- CI (`.github/workflows/build.yml`) builds a debug APK and a release APK on every push
- Release signing: the Play Store build (AAB) gets signed with Devin's own keystore once his
  Play Console account exists; the keystore is never stored in this repo
