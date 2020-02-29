# TCP Chat Server and Client in Android Studio
TCP Chat server and client created in IntelliJ and Android Studio and programmed with Kotlin.
Project uses Socket objects to provide a connection to the server and create a connection for the Android client. Kotlinx serialization libraries are implemented to serialize ChatMessage objects between server and client.

### Prerequisities
To download and and use the software you need: 
<ul>
  <li>Computer</li>
  <li>Internet connection</li>
  <li>Android Studio</li>
  <li>IntelliJ software (I used community edition, which is FREE!)</li>
</ul>
 
###  Usage
Explanation on how to run and build the Chat client mobile application and how
TCP/IP protocol is used to establish a connection and communicate with server and client.

<details><summary><b>Show instructions</b></summary>

1. Download or clone this GitHub repository.

2. Open the Client folder in Android Studio (3.5.3v at the time of writing this) 

3. Open the Server folder in IntelliJ (2019.3.3 at the time of writing this) 

#### How to run server and client and compile client app into an APK file.

* <b> Running server in IntelliJ. </b>
To run the server press the 'Run' button or use shortcut Ctrl + Shift + F10 (on Windows or Linux).
<p align="center">
  <img src="readme_images/IntelliJ Server Run project.png" alt="play project" width="650">
</p>

* <b> Stopping the server. </b>
To stop the server you can similiarily press the 'Stop' button.
<p align="center">
  <img src="readme_images/IntelliJ stop running server.png" alt="build project" width="650">
</p>

* <b> Running client in Android Studio. </b>
To run the client press the 'Run' button or use shortcut Ctrl + Shift + F10 (on Windows or Linux).
Both IDEs are built from the same code, thus they both have the same functionality.
<p align="center">
  <img src="readme_images/android studio run play button.png" alt="play project" width="650">
</p>

* <b> Stopping client emulation in Android Studio. </b>
To stop the client you can similiarily press the 'Stop' button.
<p align="center">
  <img src="readme_images/android studio stop running button.png" alt="play project" width="650">
</p>

* <b> Building project. </b>
Builds an APK of all modules in the current project for their selected variant. When IDE finishes building, a confirmation notification appears, providing a link to the APK file. The path to file is in <i><b>Chat Client Android Studio/app/build/outputs/apk/debug/</b></i> and default file name is app-debug.apk
<p align="center">
  <img src="readme_images/Android studio build APK .jpeg" alt="build project" width="650">
</p>
</details>

<details><summary><b>How to establish a connection to server.</b></summary>
1. Server socket is bound to a port and uses the assigned IP address. For this example we can use private address '192.168.1.10' and port '9999'.
  
  
2. In chat client a connection is established by using the above address and port. 
</details>

### Automation testing
Not implemented :( 

### Application screenshots
Images of how the application looks.
<details><summary><b>Screenshots</b></summary>
<p align="center">
  <img src="app_screenshots/connect_screen.png" alt="details" width="200">
  <img src="app_screenshots/connected_screen.png" alt="details" width="200">
  <img src="app_screenshots/connected_screen02.png" alt="details" width="200">
  <img src="app_screenshots/current_users.png" alt="details" width="200">
  <img src="app_screenshots/message_derek.png.png" alt="details" width="200">
  <img src="app_screenshots/message_history.png" alt="details" width="200"> 
  <img src="app_screenshots/tap_button_list.png" alt="details" width="200"> 
  <img src="app_screenshots/top_chatters.png" alt="details" width="200">
</p>
</details>

### Change log
See CHANGELOG [here](CHANGELOG.md)
