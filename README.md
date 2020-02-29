# TCP Chat Server and Client in Android Studio
TCP Chat server and client created in IntelliJ and Android Studio and programmed with Kotlin.
Project uses Socket objects to provide a connection to the server and create a connection for the Android client. Kotlinx serialization libraries are implemented to serialize ChatMessage objects between server and client.

### Prerequisities
To download and and use the software you need: 
`<ul>
  <li>Computer</li>
  <li>Internet connection</li>
  <li>Android Studio</li>
  <li>IntelliJ software (I used community edition, which is FREE!)</li>
 </ul>
 `
### Usage

<details><summary><b>Show instructions</b></summary>

1. Download or clone this GitHub repository.

2. Open the Client folder in Android Studio (3.5.3v at the time of writing this) 

3. Open the Server folder in IntelliJ (2019.3.3 at the time of writing this) 

4. 

#### How to run server and client and compile client app into an APK file.

* <b> Running server in IntelliJ. </b>
To run the server press the 'Run' button or use shortcut Ctrl + Shift + F10 (on Windows or Linux).
<p align="center">
  <img src="readme_images/IntelliJ Server Run project.png" alt="play project" width="650">
</p>

* <b> Stopping the server. </b>
To stop the server you can similiarily press the 'Stop' button.
<p align="center">
  <img src="readme_images/ntelliJ stop running server.png" alt="build project" width="650">
</p>

* <b> Running client in Android Studio. </b>
To run the client press the 'Run' button or use shortcut Ctrl + Shift + F10 (on Windows or Linux).
Both IDEs are built from the same code, thus they both have the same functionality.
<p align="center">
  <img src="android studio run play button.png" alt="play project" width="650">
</p>

* <b> Stopping client emulation in Android Studio. </b>
To stop the client you can similiarily press the 'Stop' button.
<p align="center">
  <img src="android studio stop running button.png" alt="play project" width="650">
</p>

* <b> Building project. </b>
Builds an APK of all modules in the current project for their selected variant. When IDE finishes building, a confirmation notification appears, providing a link to the APK file. The path to file is in <i><b>BirdApp/app/build/outputs/apk/debug/</b></i> and default file name is app-debug.
<p align="center">
  <img src="readme_images/build_circle.jpeg" alt="build project" width="650">
</p>

</details>

### Automation testing
Not implemented :( 

### Application screenshots

<p align="center">
  <img src="readme_images/main_view.png" alt="details" width="300">
  <img src="readme_images/details_view.png" alt="details" width="300">
  <img src="readme_images/maps_view.png" alt="details" width="300">
</p>

### Change log
See CHANGELOG [here](CHANGELOG.md)
