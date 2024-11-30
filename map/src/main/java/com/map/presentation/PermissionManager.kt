package com.map.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class PermissionManager(
    private val context: Context,
    private val onPermissionGranted: () -> Unit,
    private val onPermissionDenied: () -> Unit
) {

    private var requestPermissionLauncher: ActivityResultLauncher<String>? = null

    fun initialize(activityResultCaller: ActivityResultCaller) {
        requestPermissionLauncher = activityResultCaller.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                onPermissionGranted()
            } else {
                showSettingsDialog()
            }
        }
    }

    fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkAndRequestLocationPermission() {
        when {
            hasLocationPermission() -> {
                onPermissionGranted()
            }

            shouldShowRequestPermissionRationale() -> {
                showPermissionRationaleDialog()
            }

            else -> {
                requestPermissionLauncher?.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun shouldShowRequestPermissionRationale(): Boolean {
        return (context as? ActivityResultCaller)?.let {
            androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale(
                it as android.app.Activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } ?: false
    }

    private fun showPermissionRationaleDialog() {
        AlertDialog.Builder(context)
            .setTitle("Требуется разрешение")
            .setMessage("Для работы с картой нужно разрешение на доступ к геолокации. Пожалуйста, предоставьте его.")
            .setPositiveButton("ОК") { _, _ ->
                requestPermissionLauncher?.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
                onPermissionDenied()
            }
            .show()
    }

    private fun showSettingsDialog() {
        AlertDialog.Builder(context)
            .setTitle("Разрешение отклонено")
            .setMessage("Вы отклонили разрешение на геолокацию. Вы можете включить его в настройках приложения.")
            .setPositiveButton("Настройки") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
                onPermissionDenied()
            }
            .show()
    }
}
