package com.map.presentation.dialogFragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.DialogFragment

class PermissionSettingsDialog(
    private val onNegativeAction: () -> Unit,
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Разрешение отклонено")
            .setMessage("Вы отклонили разрешение на геолокацию. Вы можете включить его в настройках приложения.")
            .setPositiveButton("Настройки") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", requireContext().packageName, null)
                }
                startActivity(intent)
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
                onNegativeAction()
            }
            .create()
    }

    companion object {
        const val TAG = "PermissionSettingsDialog"
    }
}
