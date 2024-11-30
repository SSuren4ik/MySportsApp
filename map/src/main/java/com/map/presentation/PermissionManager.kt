package com.map.presentation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.map.presentation.dialogFragments.PermissionRationaleDialog
import com.map.presentation.dialogFragments.PermissionSettingsDialog

class PermissionManager(
    private val context: Context,
    private val onPermissionGranted: () -> Unit,
    private val onPermissionDenied: () -> Unit,
) {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

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

    private fun hasLocationPermission(): Boolean {
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
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun shouldShowRequestPermissionRationale(): Boolean {
        return (context as? ActivityResultCaller)?.let {
            ActivityCompat.shouldShowRequestPermissionRationale(
                it as Activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } ?: false
    }

    private fun showPermissionRationaleDialog() {
        PermissionRationaleDialog(
            onPositiveAction = {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            },
            onNegativeAction = {
                onPermissionDenied()
            }
        ).show(
            (context as FragmentActivity).supportFragmentManager,
            PermissionRationaleDialog.TAG
        )
    }

    private fun showSettingsDialog() {
        PermissionSettingsDialog(
            onNegativeAction = {
                onPermissionDenied()
            }
        ).show(
            (context as FragmentActivity).supportFragmentManager,
            PermissionSettingsDialog.TAG
        )
    }
}
