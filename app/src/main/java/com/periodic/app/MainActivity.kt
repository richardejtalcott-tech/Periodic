package com.periodic.app

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.Choreographer
import android.view.Gravity
import android.view.SurfaceView
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.filament.EntityManager
import com.google.android.filament.LightManager
import com.google.android.filament.Skybox
import com.google.android.filament.utils.ModelViewer
import com.google.android.filament.utils.Utils
import java.nio.ByteBuffer
import kotlin.math.cos
import kotlin.math.sin

/** Main exhibit. The v2.2 table is retained as a transparent overlay over a real GLB room. */
class MainActivity : Activity() {
    companion object { init { Utils.init() } }

    private lateinit var surfaceView: SurfaceView
    private lateinit var modelViewer: ModelViewer
    private lateinit var choreographer: Choreographer
    private val lights = mutableListOf<Int>()
    private var startNanos = 0L
    private var rendering = false

    private val frameCallback = object : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            if (!rendering) return
            choreographer.postFrameCallback(this)
            if (startNanos == 0L) startNanos = frameTimeNanos
            val t = (frameTimeNanos - startNanos) / 1_000_000_000.0
            // Slow room parallax. The table itself stays stable and readable.
            val eyeX = sin(t * 0.075) * 0.52
            val eyeY = 0.70 + sin(t * 0.052) * 0.08
            val eyeZ = 9.15 + cos(t * 0.075) * 0.18
            modelViewer.camera.lookAt(eyeX, eyeY, eyeZ, 0.0, 0.15, -2.9, 0.0, 1.0, 0.0)
            modelViewer.render(frameTimeNanos)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        WindowInsetsHelper.applyImmersive(window)

        choreographer = Choreographer.getInstance()

        val root = FrameLayout(this)
        surfaceView = SurfaceView(this).also { it.holder.setFormat(PixelFormat.OPAQUE) }
        root.addView(surfaceView, FrameLayout.LayoutParams(-1, -1))

        modelViewer = ModelViewer(surfaceView)
        modelViewer.scene.skybox = Skybox.Builder()
            .color(0.002f, 0.006f, 0.011f, 1f)
            .build(modelViewer.engine)
        modelViewer.view.setPostProcessingEnabled(true)
        modelViewer.view.setShadowingEnabled(true)
        modelViewer.camera.setExposure(10.5f, 1f / 60f, 100f)

        val environment = assets.open("scientific_lab.glb").use { ByteBuffer.wrap(it.readBytes()) }
        modelViewer.loadModelGlb(environment)
        // Do not call transformToUnitCube(): the room was authored in deliberate world coordinates.
        modelViewer.camera.lookAt(0.0, 0.70, 9.2, 0.0, 0.15, -2.9, 0.0, 1.0, 0.0)
        addLights()

        val table = PeriodicTableView(this) { number ->
            startActivity(Intent(this, ElementDetailActivity::class.java).putExtra("atomicNumber", number))
            overridePendingTransition(R.anim.exhibit_enter, R.anim.exhibit_exit)
        }
        root.addView(table, FrameLayout.LayoutParams(-1, -1))
        setContentView(root)
    }

    private fun addLights() {
        addDirectional(0.72f, 0.88f, 1.0f, 105_000f, -0.35f, -0.72f, -0.52f, true)
        addDirectional(0.18f, 0.62f, 0.82f, 55_000f, 0.58f, -0.15f, -0.35f, false)
        addDirectional(0.55f, 0.22f, 0.42f, 27_000f, -0.25f, 0.05f, 0.62f, false)
    }

    private fun addDirectional(r: Float, g: Float, b: Float, intensity: Float, x: Float, y: Float, z: Float, shadows: Boolean) {
        val entity = EntityManager.get().create()
        LightManager.Builder(LightManager.Type.DIRECTIONAL)
            .color(r, g, b).intensity(intensity).direction(x, y, z).castShadows(shadows)
            .build(modelViewer.engine, entity)
        modelViewer.scene.addEntity(entity)
        lights += entity
    }

    private fun buildBrand() = LinearLayout(this).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        setPadding(20, 8, 20, 8)
        setBackgroundColor(Color.argb(72, 4, 9, 15))
        addView(TextView(this@MainActivity).apply {
            text = "PERIODIC"; textSize = 19f; setTextColor(Color.rgb(228, 239, 248)); letterSpacing = .12f
            setShadowLayer(12f, 0f, 0f, Color.rgb(91, 176, 228))
        })
        addView(TextView(this@MainActivity).apply {
            text = "   matter, revealed"; textSize = 11f; setTextColor(Color.rgb(155, 188, 205)); letterSpacing = .08f
        })
    }

    override fun onResume() {
        super.onResume(); rendering = true; choreographer.postFrameCallback(frameCallback)
    }

    override fun onPause() {
        rendering = false; choreographer.removeFrameCallback(frameCallback); super.onPause()
    }

    override fun onDestroy() {
        rendering = false
        if (::choreographer.isInitialized) choreographer.removeFrameCallback(frameCallback)
        if (!::modelViewer.isInitialized) { super.onDestroy(); return }
        lights.forEach {
            modelViewer.scene.removeEntity(it)
            modelViewer.engine.destroyEntity(it)
            EntityManager.get().destroy(it)
        }
        modelViewer.destroyModel()
        modelViewer.scene.skybox?.let { modelViewer.engine.destroySkybox(it) }
        super.onDestroy()
    }
}
