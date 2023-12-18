package com.example.a3dviewer
//import kotlinx.android.synthetic.main.activity_main.*

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
//import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraExecutor = Executors.newSingleThreadExecutor()

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                val camera = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview
                )

                val arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment

                findViewById<Button>(R.id.btnCapture).setOnClickListener {
                    takePhoto(camera) { photoFile ->
                        // Display the 3D image using your preferred 3D viewer library
                        display3DImage(arFragment, photoFile)
                    }
                }

            } catch (e: Exception) {
                Log.e(TAG, "Use case binding failed", e)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto(camera: Camera, callback: (File) -> Unit) {
        val photoFile = File(externalMediaDirs.firstOrNull(), "${System.currentTimeMillis()}.jpg")

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        val imageCapture = ImageCapture.Builder().build()

        imageCapture.takePicture(
            outputOptions, cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    callback(photoFile)
                }
            })
    }

    private fun display3DImage(arFragment: ArFragment, photoFile: File) {
        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
            // Load the 3D model and place it on the detected plane
            ModelRenderable.builder()
                .setSource(this, Uri.fromFile(photoFile))
                .build()
                .thenAccept { renderable ->
                    val anchorNode = AnchorNode(hitResult.createAnchor())
                    anchorNode.renderable = renderable
                    arFragment.arSceneView.scene.addChild(anchorNode)
                }
                .exceptionally {
                    Log.e(TAG, "Error loading 3D model: ${it.message}", it)
                    null
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXBasic"
    }
}
