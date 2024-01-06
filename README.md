# ImageJAugmentation
An Imagej Plugin for Large Scale Image Data Augmentation 

* add path to lines 63, 54, 64, 65 in ImageAugmentor.java file

## Installation (Linux)

### Installing Java 8
``` bash
sudo apt update
sudo apt install openjdk-8-jdk
```
If several versions of Java are installed in your system, you can switch between the versions using the command 
### !!! IF SEVERAL VERSIONS OF JAVA ARE INSTALLED, IT IS VERY IMPORTANT TO SET THE JAVA SDK AND JAVAC TO THE SAME VERSIONS, IF NOT THE CODE WILL GENERATE ERROR  
```bash
sudo update-alternatives --config java
sudo update-alternatives --config javac
```

### Checking Java installation 
Terminal command for checking Java version
```bash
java -version
javac --version
```
Response:
```bash
openjdk version "1.8.0_392"
OpenJDK Runtime Environment (build 1.8.0_392-8u392-ga-1~22.04-b08)
OpenJDK 64-Bit Server VM (build 25.392-b08, mixed mode)

javac 1.8.0_392
```

* Downlaod Fiji from [here](https://imagej.net/software/fiji/)
* Extract the files to the desired folder
* Navigate to fiji-linux64/Fiji.app folder, right-click on ImageJ-linux64, on the permissions tab, make sure Allow executing file as a program option checkbox is selected. 
* Download ImageJAugmentation using the command ```python git clone https://github.com/jithin8mathew/ImageJAugmentation.git```, assuming you have git installed.
* Once ImageJ window appears, select open> ImageAugmentor.java
* CTRL + R will compile and run the script if the right Java version is chosen

## Using ImageJAugmentor

![final_imageJ](https://github.com/jithin8mathew/ImageJAugmentation/blob/main/imageJ.png)
<br>

![final_screenshot](https://github.com/jithin8mathew/ImageJAugmentation/blob/main/GDB_UI.png)


<br>
