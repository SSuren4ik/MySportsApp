package com.map.presentation.dialogFragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class PermissionRationaleDialog(
    private val onPositiveAction: () -> Unit,
    private val onNegativeAction: () -> Unit,
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Требуется разрешение")
            .setMessage("Для работы с картой нужно разрешение на доступ к геолокации. Пожалуйста, предоставьте его.")
            .setPositiveButton("ОК") { _, _ ->
                onPositiveAction()
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
                onNegativeAction()
            }
            .create()
    }

    companion object {
        const val TAG = "PermissionRationaleDialog"
    }
}
