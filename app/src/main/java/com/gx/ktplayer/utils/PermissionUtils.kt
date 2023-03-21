package com.gx.ktplayer.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.gx.ktplayer.ui.MainActivity

/**
 * 动态权限管理器
 */
class PermissionUtils(val activity: AppCompatActivity) {
    lateinit var onFilePermissionCallback: (result: Boolean) -> Any
    val REQUEST_WRITE_EXTERNAL_STORAGE = 1


    /**
     * 申请文件读写权限
     */
    fun requestFilePermission() {
        if (!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) || !checkPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            activity.requestPermissions(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
                ), REQUEST_WRITE_EXTERNAL_STORAGE
            )
        } else {
            onFilePermissionCallback(true)
        }
    }

    /**
     * 文件权限获取结果的回调
     */

    fun onRequestResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //已经申请权限
                    onFilePermissionCallback(true)
                } else {
                    //申请权限失败
                    onFilePermissionCallback(false)
                }
            }
        }

    }


    /**
     * 文件权限获取结果的回调
     */
    fun filePermissionCallback(onFilePermissionCallback: (result: Boolean) -> Any) {
        this.onFilePermissionCallback = onFilePermissionCallback
    }

    /**
     * 检查权限是否获取
     */
    fun checkPermission(value: String): Boolean {
        return ContextCompat.checkSelfPermission(activity, value) == PackageManager.PERMISSION_GRANTED
    }


}