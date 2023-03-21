package com.gx.ktplayer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gx.ktplayer.utils.PermissionUtils

abstract class BaseActivity : AppCompatActivity() {
    lateinit var permissionUtils: PermissionUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestFilePermissionAction()
    }

    /**
     * 动态申请访问文件权限
     */
    private fun requestFilePermissionAction() {
        permissionUtils = PermissionUtils(this)
        permissionUtils.filePermissionCallback {
            hasFilePermission(it)
        }
        permissionUtils.requestFilePermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionUtils.onRequestResult(requestCode, permissions, grantResults)
    }

    /**
     * 获取到文件权限的回调方法
     */
    abstract fun hasFilePermission(hasPerssion: Boolean)

}