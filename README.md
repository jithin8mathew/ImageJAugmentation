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
### ⚠️ IF SEVERAL VERSIONS OF JAVA ARE INSTALLED, IT IS VERY IMPORTANT TO SET THE JAVA SDK AND JAVAC TO THE SAME VERSIONS, IF NOT THE CODE WILL GENERATE ERROR
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

## Datasets used:
For object detection benchmark, we used [Global Wheat Head Detection Dataset](https://www.global-wheat.com/gwhd.html), which can be accessed for your own testing. The dataset needs to pre-processed with the following [code](https://www.google.com) to work with YOLOv8 hosted by [Ultralytics YOLOv8](https://github.com/ultralytics/ultralytics). 

Dataset were split into YOLO format using the following [script](https://github.com/jithin8mathew/Python_Scripts_for_Machine_Learning_pre-processing/blob/main/create_YOLO_dataset.py) with 90:10 ratio.

## Training the model 
[YOLOv8l]([https://github.com/ultralytics/ultralytics](https://github.com/ultralytics/assets/releases/download/v0.0.0/yolov8l.pt)https://github.com/ultralytics/assets/releases/download/v0.0.0/yolov8l.pt) from [Ultralytics](https://github.com/ultralytics/ultralytics) was used for the object detect training experimentation. 

For each Deep Learning methods that we test in the study (classification, detection, and segmentation), we perform training and validation with 2 variants of the same dataset each. 
   * AUGMENTED DATASET
   * ORIGINAL DATASET

### OBJECT DETECTION

YOLOv8 PEFORMS AUGMENTATION BY DEFAULT AS SEEN [HERE](https://github.com/ultralytics/ultralytics/blob/main/ultralytics/cfg/default.yaml). AUGMENTATION PARAMETERS IN THE DEFAULT CONFIGURATION NEEDS TO BE SET TO ZERO TO OVERRIDE THE DEFAULT HYPERPARAMETERS. 

**ORIGINAL DATASET**
 - Training: 5877 images
 - Validation: 631 images
**AUGMENTED DATASET**
- Training: 4702(original) + 1175(augmented)
 - Validation: 631 images
   
```python3
from ultralytics import YOLO

model = YOLO('yolov8l.pt')
results = model.train(data='/home/pauloflores/Documents/imageJ_project/dataset/YOLOv8_data_format/data.yaml', 
                      epochs=100, 
                      imgsz=1024, 
                      save=True,
                      batch = 8,
                      # device=[0,1], 
                      project="ImgeJ_Augmentor",
                      name="Non_augmented_OD",

                      # DISABLING DATA AUGMENTATION PARAMETERS
                      hsv_h = 0.0,
                      hsv_s = 0.0,
                      hsv_v = 0.0,
                      degrees = 0.0,
                      translate = 0.0,
                      scale = 0.0,
                      shear = 0.0,
                      perspective = 0.0,
                      flipud = 0.0,
                      fliplr = 0.0,
                      mosaic = 0.0,
                      mixup = 0.0,
                      copy_paste = 0.0,
                     )

```

### INSTANCE SEGMENTATION
**ORIGINAL DATASET**
 - Training: 500 images
 - Validation: 50 images
**AUGMENTED DATASET**
- Training: 400(original) + 100(augmented)
 - Validation: 50 images

### IMAGE CLASSIFICATION
**ORIGINAL DATASET**
 - Training: ABC images
 - Validation: DEF images
**AUGMENTED DATASET**
- Training: ABC(original) + HIJ(augmented)
 - Validation: DEF images


