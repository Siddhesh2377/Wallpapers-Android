package com.dark.wallpapers.services

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.dark.wallpapers.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.sin

class MyLiveWallpaperService : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return MyWallpaperEngine()
    }

    inner class MyWallpaperEngine : Engine() {

        private var screenWidth = 0
        private var screenHeight = 0

        private val handler = Handler(mainLooper)
        private val drawRunner = object : Runnable {
            override fun run() {
                drawFrame()
            }
        }

        private var visible = true

        private val timeFormat = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())

        private val paint = Paint().apply {
            color = Color.WHITE
            textSize = 70f
            isAntiAlias = true
            typeface = resources.getFont(R.font.dotted)
            isFakeBoldText = true
        }

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            screenWidth = width
            screenHeight = height
        }

        override fun onVisibilityChanged(visible: Boolean) {
            this.visible = visible
            if (visible) {
                handler.post(drawRunner)
            } else {
                handler.removeCallbacks(drawRunner)
            }
        }

        private var frame = 0

        private fun drawFrame() {
            val currentTime = timeFormat.format(Date())
            val canvas = surfaceHolder.lockCanvas()

            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.bg_removed)
            val bitmapWidth = bitmap.width
            val bitmapHeight = bitmap.height

            // Calculate bobbing offset (sin wave animation)
            val bobOffset = (40 * sin(frame / 60.0)).toFloat() // Smooth up/down
            val bitmapX = 0f
            val bitmapY = ((screenHeight - 500) - bitmapHeight) / 2f + bobOffset

            // Pulse effect for time text (alpha animation)
            val alphaPulse = (128 + 127 * sin(frame / 15.0)).toInt().coerceIn(0, 255)

            canvas?.let {
                it.drawColor(Color.BLACK)

                // Draw animated text
                val lines = listOf(
                    "Hello from Canvas!",
                    currentTime
                )

                var y = (400f)
                for ((index, line) in lines.withIndex()) {
                    paint.textSize = if (index == 0) 80f else 90f
                    paint.color = if (index == 0) Color.WHITE else Color.RED
                    paint.alpha = if (index == 1) alphaPulse else 255
                    val textWidth = paint.measureText(line)
                    val x = (screenWidth - textWidth) / 2f

                    it.drawText(line, x, y, paint)
                    y += paint.textSize + 80f
                }

                // Reset alpha for bitmap
                paint.alpha = 255
                it.drawBitmap(bitmap, bitmapX, bitmapY, null)

                surfaceHolder.unlockCanvasAndPost(it)
            }

            // Increase frame count for animations
            frame++

            if (visible) {
                handler.postDelayed(drawRunner, 1000L / 60) // 60 FPS
            }
        }


        override fun onDestroy() {
            super.onDestroy()
            handler.removeCallbacks(drawRunner)
        }
    }
}