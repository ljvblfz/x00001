App模块化及工程扩展 

http://www.apkbus.com/android-14556-1-4.html


Http Download


http://dampce032.iteye.com/blog/975642
http://www.eoeandroid.com/thread-108676-1-1.html
http://blog.csdn.net/ameyume/article/details/6528205




 
* 1.在AndroidMainfest.xml中进行权限配置  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /> 取得写入SDCard的权限 
* 2.取得SDCard的路径： Environment.getExternalStorageDirectory() 



emulator create sdcard

http://www.tylerfrankenstein.com/create-android-emulator-sd-card-and-write-data-to-it

http://www.streamhead.com/android-tutorial-sd-card/


D:\02-FounderResources\Android_Tool\android-sdk\tools

command> mksdcard -l mysdcard 1024M sdcard.img


-sdcard D:\02-FounderResources\Android_Tool\android-sdk\tools\sdcard.img
