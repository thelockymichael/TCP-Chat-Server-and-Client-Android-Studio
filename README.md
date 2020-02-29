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

#### How to run server and client and compile client app into an APK file.

* <b> Running project. </b>
Running project will launch the application on an emulated or physical Android device.
In the image the current emulating device is set to Pixel 3 XL.
<p align="center">
  <img src="readme_images/play_circle.jpeg" alt="play project" width="650">
</p>

* <b> Building project. </b>
Builds an APK of all modules in the current project for their selected variant. When IDE finishes building, a confirmation notification appears, providing a link to the APK file. The path to file is in <i><b>BirdApp/app/build/outputs/apk/debug/</b></i> and default file name is app-debug.
<p align="center">
  <img src="readme_images/build_circle.jpeg" alt="build project" width="650">
</p>

* <b> Make project. </b>
Make project compile all the source files in the entire project that have been modified since the last compilation are compiled. 
Dependent source files, if appropriate, are also compiled.
<p align="center">
  <img src="readme_images/make_circle.jpeg" alt="make project" width="650">
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
