# &#x20;Wallpaper (Android)

A clean and minimal live wallpaper service for Android that replicates the elegant iPhone-style lockscreen experience. This project demonstrates text and image layering, animated elements, and a centered floating foreground asset.

## 🌟 Core Features

- **Text Behind Foreground**: Mimics iOS lockscreen layout by displaying time/text behind a transparent image (e.g. Earth).
- **Smooth Animation**:
  - Clock text pulses subtly (fade-in/out)
  - Foreground PNG (e.g. Earth) bobs gently up and down
- **Centered Design**: All elements (text and image) are dynamically centered based on screen size.
- **Custom Font Support**: Use custom TTF fonts for styling the time text.

## 🛠️ Tech Used

- Kotlin
- Android WallpaperService
- Canvas Drawing API
- Sin wave based animation (for motion & effects)

## 🔧 Setup

1. Clone the repo
2. Add your own transparent foreground PNG in `res/drawable/`
3. Add any `.ttf` font in `res/font/`
4. Build & run on a real Android device

## 🔓 Permissions

No permissions required. This runs as a system live wallpaper.

## Preview
<img src="https://github.com/user-attachments/assets/bf8e6ede-ced6-4aba-9321-c5b3426924e9" width="300" />




## 🧠 Notes

- For devices like Nothing Phone or Pixel, use a built-in button to launch the live wallpaper picker manually via `WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER`.
- You can expand the effect by adding weather, battery %, or even analog clock hands.

---

Built by Siddhesh Sonar 🚀

