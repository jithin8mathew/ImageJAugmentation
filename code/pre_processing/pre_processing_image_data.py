#!/usr/bin/env python3
# coding: utf-8

import os
import csv
from tqdm import tqdm

def convert_to_yolo_format(box_string):
    boxes = box_string.split(';')[:-1]  # Split by ';' and remove the last element
    yolo_annotations = []
    for box in boxes:
        x_min, y_min, x_max, y_max = map(int, box.split())
        image_width, image_height = 1024, 1024
        # YOLO format: normalized coordinates (values between 0 and 1)
        x_center = (x_min + x_max) / (2 * image_width)
        y_center = (y_min + y_max) / (2 * image_height)
        width = (x_max - x_min) / image_width
        height = (y_max - y_min) / image_height
        
        yolo_annotation = f"0 {x_center:.6f} {y_center:.6f} {width:.6f} {height:.6f}"
        yolo_annotations.append(yolo_annotation)

    return yolo_annotations

def create_yolo_annotation(image_folder, csv_folder, output_folder):
    # Dictionary to store YOLO annotations for each image
    yolo_dict = {}

    # Iterate through each CSV file
    for csv_filename in os.listdir(csv_folder):
        if csv_filename.endswith(".csv"):
            csv_path = os.path.join(csv_folder, csv_filename)

            # Read CSV file
            with open(csv_path, 'r') as csv_file:
                reader = csv.reader(csv_file)

                # Read header to get column indices
                header = next(reader)

                for row in tqdm(reader):
                    image_name = row[0]
                    boxes_string = row[1]

                    # Convert to YOLO format
                    yolo_annotations = convert_to_yolo_format(boxes_string)

                    # Add YOLO annotations to the dictionary
                    if image_name not in yolo_dict:
                        yolo_dict[image_name] = []
                    yolo_dict[image_name].extend(yolo_annotations)

    # Save YOLO annotations to txt files
    for image_name, annotations in tqdm(yolo_dict.items()):
        image_name = image_name.rstrip(".png")
        output_txt_path = os.path.join(output_folder, f"{image_name}.txt")
        with open(output_txt_path, 'w') as output_txt:
            for yolo_annotation in annotations:
                output_txt.write(yolo_annotation + "\n")

                            
if __name__ == "__main__":
    # Set the paths
    root_folder = "root_folder_path"
    image_folder = root_folder+"gwhd_2021/images"
    csv_folder = root_folder+"gwhd_2021"
    output_folder = root_folder+"gwhd_2021/images"

    # Create output folder if it doesn't exist
    os.makedirs(output_folder, exist_ok=True)

    # Call the function to generate YOLO annotations
    create_yolo_annotation(image_folder, csv_folder, output_folder)


# ## Visualizing output annotation to validate correct labelling

import os
import cv2
import numpy as np

import os
import cv2
import numpy as np

def draw_center_on_image(image_path, label_path, output_dir):
    # Read the image
    image = cv2.imread(image_path)

    # Read YOLO label file
    with open(label_path, 'r') as label_file:
        lines = label_file.readlines()

    # Process each line in the label file
    for line in lines:
        values = list(map(float, line.strip().split()))
        
        if len(values) >= 5:
            _, x_center, y_center, width, height = values[:5]

            # Get image dimensions
            height, width, _ = image.shape

            # Convert YOLO coordinates to pixel coordinates
            x_center *= width
            y_center *= height

            # Draw a circle (you can also use cv2.rectangle or any other drawing method)
            cv2.circle(image, (int(x_center), int(y_center)), radius=12, color=(128, 0, 128), thickness=-1)

    # Save the output image
    output_path = os.path.join(output_dir, os.path.basename(image_path))
    cv2.imwrite(output_path, image)

def process_images(image_folder, label_folder, output_folder):
    # Create output folder if it doesn't exist
    os.makedirs(output_folder, exist_ok=True)

    # Process each image and its corresponding label
    for image_file in os.listdir(image_folder):
        if image_file.endswith(".jpg") or image_file.endswith(".png"):
            image_path = os.path.join(image_folder, image_file)
            label_path = os.path.join(label_folder, os.path.splitext(image_file)[0] + ".png.txt")

            if os.path.exists(label_path):
                draw_center_on_image(image_path, label_path, output_folder)


if __name__ == "__main__":
    # Replace these paths with your actual paths
    image_folder_path = "root_folder_path/sample"
    label_folder_path = "root_folder_path/sample"
    output_folder_path = "root_folder_path/sample/output/"
    
    os.makedirs(output_folder_path, exist_ok=True)

    process_images(image_folder_path, label_folder_path, output_folder_path)


#!/usr/bin/env python
# coding: utf-8
# Created by Jithin Mathew on 02/27/2023

from glob import glob
from tqdm import tqdm
import random as r
from termcolor import colored
import os
from shutil import copy


def create_outputFolder_structure(output_folder):
    print(colored("creating YOLO train directory structure", "yellow"))
    if os.path.exists(output_folder+"/"+"images"):
        pass
    else:
        os.mkdir(output_folder+"/"+"images")
        os.mkdir(output_folder+"/"+"images"+"/"+"train")
        os.mkdir(output_folder+"/"+"images"+"/"+"val")
        
    if os.path.exists(output_folder+"/"+"labels"):
        pass
    else:
        os.mkdir(output_folder+"/"+"labels")
        os.mkdir(output_folder+"/"+"labels"+"/"+"train")
        os.mkdir(output_folder+"/"+"labels"+"/"+"val")
    print(colored("Directories created", "green"))

def remove_mismatch(images, labels, input_folder):
    images_ = set([os.path.basename(_).rstrip('.png') for _ in images])
    labels_ = set([os.path.basename(_).rstrip('.txt') for _ in labels])

    if images_ - labels_ is not None:
        temp = images_ - labels_
        for _ in temp:
            os.remove(input_folder+"/"+_+".png") #change the image file extension
            print(_+".png files removed")
    if labels_ - images_ is not None:
        temp = labels_ - images_
        for _ in temp:
            os.remove(input_folder+"/"+_+".txt") #change the xml file extension
            print(_+".txt files removed")
            
def validate_dataset(output_folder):
    train_images = glob(output_folder+"/"+"images/train/"+"*.png")
    train_labels = glob(output_folder+"/"+"labels/train/"+"*.txt")
    
    val_images = glob(output_folder+"/"+"images/val/"+"*.png")
    val_labels = glob(output_folder+"/"+"labels/val/"+"*.txt")
    
    if len(train_images) & len(train_labels) & len(val_images) & len(val_labels) > 0:
        if len(train_images) == len(train_labels):
            print(colored("Training data is validated :"+str(len(train_images)),"green"))
        else:
            print(colored("Training data is corrupt","red"))
        if len(val_images) == len(val_labels):
            print(colored("Validation data is validated :" +str(len(val_images)),"green"))
        else:
            print(colored("Validation data is corrupt","red"))
    else:
        print(colored(r"Data missing from one of the folders [train images:"+ str(len(train_image)+ "train labels :"+ str(len(train_labels))+ "val images :"+  len(val_images) + "val labels :"+  len(val_labels)), "red"))
    

# reading the files from folder
input_folder = r"input_folder"
output_folder = r"output_folder"

os.makedirs(output_folder, exist_ok=True)

# set the train test ratio here
ratio = 0.9 
    
def generate_dataset(input_folder, output_folder, ratio):
    #default ration for splitting data into training and testing will be 90:10
    
    # read imgaes from the input folder 
    images = glob(input_folder+"/"+"*.png") 
    # read labels from the input folder 
    labels = glob(input_folder+"/"+"*.txt")
    
    # create a temp list ot hold the images/label names to avoid copying the same data twice
    temp_list = []
    val_list = []
    train_sample_no = round((len(images)*(ratio*100))/100)

    if len(images) == len(labels): 
        if len(images) & len(labels) > 0:
            print(colored("creating dataset with 90:10 ratio...","yellow"))
            print(colored("Validating directory structure","yellow"))
            create_outputFolder_structure(output_folder)
            val_list = images
            print(colored(str(train_sample_no) + " random images and labels picked for training set","yellow"))
            for _ in tqdm(range(train_sample_no)):
                src = r.choice(images)
                val_list.remove(src)
                if os.path.exists(src) & os.path.exists(os.path.dirname(src)+"/"+os.path.basename(src).rstrip('.png')+".txt"):
                    copy(src, os.path.join(output_folder+"/"+"images/train/"+os.path.basename(src)))
                    copy(os.path.dirname(src)+"/"+os.path.basename(src).rstrip('.png')+".txt", os.path.join(output_folder+"/"+"labels/train/"+os.path.basename(src).rstrip('.png')+'.txt'))
            print(colored("Generating Val dataset with remaining "+str(len(images)-round((len(images)*(ratio*100))/100)),"green"))
            print(colored(str(len(val_list)) + " images and labels picked for validation set","yellow"))
            for _ in tqdm(val_list):
                if os.path.exists(_) & os.path.exists(os.path.dirname(_)+"/"+os.path.basename(_).rstrip('.png')+".txt"):
                    copy(_, os.path.join(output_folder+"/"+"images/val/"+os.path.basename(_)+'.png'))
                    copy(os.path.dirname(_)+"/"+os.path.basename(_).rstrip('.png')+".txt", os.path.join(output_folder+"/"+"labels/val/"+os.path.basename(_).rstrip('.png')+'.txt'))
            print(colored(r"data generation complete \n validating generated dataset","green"))
            validate_dataset(output_folder)
        else:
            print(colored(r"no images or labels found","red"))
    elif len(images) != len(labels):
        print(colored("images and labels mismatch found, \n make sure the no images and no of labels match + [images:" + str(len(images))+ " labels :"+ str(len(labels)) + r"]", "red"))
        clean_folder = input(r"remove mismatching files and proceed? [Y\N]")
        if clean_folder.lower() == "y":
            remove_mismatch(images, labels, input_folder)
            print(colored("repeating the dataset generation...","green"))
            generate_dataset(input_folder, output_folder, ratio)
        else:
            pass
    else:
        print(colored(r"no images or labels found","red"))
        print(len(images), len(labels))

generate_dataset(input_folder, output_folder, ratio)



