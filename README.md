[![Release](https://jitpack.io/v/jasonsyf/LoadApkAndInstallLib.svg)](https://jitpack.io/#jasonsyf/LoadApkAndInstallLib)
# LoadApkAndInstallLib
APK下载安装第三方库，已上传至jitpack.io
基于rxjava,retrofit的apk下载安装demo（适配android7.0）
## 封装了一个 [library][1]
[1]: https://github.com/jasonsyf/LoadApkAndInstallLib       "library" 
### 项目Gradle 添加   
```allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```
### app Gradle添加
```compile 'com.github.jasonsyf:LoadApkAndInstallLib:v0.2.5'```
### 用法
```DownLoadUtil.insrance.startInstall(MainActivity.this, APK_URL, "test"); // Context ,url,apkname (.apk前边的)```                        
