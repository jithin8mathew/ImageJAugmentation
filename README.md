# <p align="center">Image Augmentor: An Image Data Augmentation Plugin for Deep Learning and Computer Vision using ImageJ</p>

The provided Java code implements an ImageJ plugin called "ImageAugmentor" designed for image augmentation, a technique commonly used in deep learning for data diversification. The plugin, presented as a generic dialog, enables users to select input and output directories, set augmentation parameters such as the number of generated images, and apply various image transformation methods. These methods include random flipping, scaling, rotation, noise addition, exponential transformation, gamma correction, contrast adjustment, brightness modification, smoothing, sharpening, and Gaussian distortion. The plugin leverages ImageJ's functionalities to process images based on user-defined augmentation probabilities. The resulting augmented images are saved in the chosen output format (e.g., PNG, JPG). The code is well-commented and structured, making it suitable for integration into image analysis workflows and potentially serving as a useful tool for researchers working with image datasets in the field of machine learning and computer vision.

![final_screenshot](https://github.com/jithin8mathew/ImageJAugmentation/blob/main/Mac_ImageJ_Augmentor.png)

## Installation (Linux)

### Installing Java 8
``` bash
sudo apt update
sudo apt install openjdk-8-jdk
```
If several versions of Java are installed in your system, you can switch between the versions using the command 
<span style="color: #FFC82E;">### !!! IF SEVERAL VERSIONS OF JAVA ARE INSTALLED, IT IS VERY IMPORTANT TO SET THE JAVA SDK AND JAVAC TO THE SAME VERSIONS, IF NOT THE CODE WILL GENERATE ERROR  </span>
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
* Download ImageJAugmentation using the command ```git clone https://github.com/jithin8mathew/ImageJAugmentation.git```, assuming you have git installed.
* Once ImageJ window appears, select open> ImageAugmentor.java
* CTRL + R will compile and run the script if the right Java version is chosen

## Mac installation (older silicon tech 64x AMD)
* Download Java 8 from [Oracle Java SE 8 Archive](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)
* Downlaod appropriate DMG file and dobule click on it to install.
* Downlaod Fiji from [here](https://imagej.net/software/fiji/)
* Once Fiji is downloaded, CONTROL + CLICK, the select OPEN to install the application.
* Once ImageJ window appears, select open> ImageAugmentor.java

## Augmentation methods used for this project 

### Image Augmentation Methods
1. **Flipping:**
   - Horizontal Flip
   - Vertical Flip
2. **Rotation:**
   - Random Rotation
3. **Scaling:**
   - Random Scaling
4. **Translation:**
   - Random Translation
5. **Soothing:**
   - Random Smoothing
6. **Sharpening:**
   - Random Sharpening
7. **Color Jittering:**
   - Random changes to brightness, contrast
8. **Noise:**
   - Add random noise to the image
9. **Blur:**
   - Gaussian Blur
10. **Contrast Enhancement:**
    - Adjusting contrast
11. **Brightness Adjustment:**
    - Adjusting brightness

## Using ImageJAugmentor

![final_imageJ](https://github.com/jithin8mathew/ImageJAugmentation/blob/main/imageJ.png)
<br>




<br>
