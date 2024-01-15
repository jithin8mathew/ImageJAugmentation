# import os
# import shutil
# from tqdm import tqdm

# # Read the data from the text file
# with open('/media/jithin/DATA/2024/ImageJ_Augmentation_project/data/object_detection/Augmentation_Log_methods2.txt', 'r') as file:
#     data = file.read()

# data = data.split(r": R")

# # print(data)

# for line in data:
#     if "Method choise" in line:
#         method_choice = line.split(" ")[-1]
#         os.makedirs(method_choice, exist_ok=True)

# # Move images to corresponding folders
# current_method = None
# for line in tqdm(data):
#     if "Method choise" in line:
#         current_method = line.split(" ")[-1]
#     elif "Random choise" in line:
#         image_name = line.split(" ")[-1].split(":")[0]
#         source_path = os.path.join('/media/jithin/DATA/2024/ImageJ_Augmentation_project/data/object_detection/temp_augmented_folder2', image_name)
#         destination_path = os.path.join('/media/jithin/DATA/2024/ImageJ_Augmentation_project/code/pre_processing', current_method, image_name)
#         shutil.move(source_path, destination_path)

# print("Images organized into folders based on Method choise.")

# import os
# import shutil
# from tqdm import tqdm

# # Replace these folder paths with your actual folder paths
# folder1_path = '/media/jithin/DATA/2024/ImageJ_Augmentation_project/data/object_detection/temp_augmented_folder'
# folder2_path = '/media/jithin/DATA/2024/ImageJ_Augmentation_project/data/object_detection/temp_augmented_folder2'

# # Read the data from the text file
# with open('/media/jithin/DATA/2024/ImageJ_Augmentation_project/data/object_detection/Augmentation_Log_methods.txt', 'r') as file:
#     data = file.readlines()

# # Create folders for each Method choise
# for line in data:
#     if "Method choise" in line:
#         method_choice = line.split(" ")[-1].strip()
#         os.makedirs(method_choice, exist_ok=True)

# # Move images to corresponding folders
# print(len(data))
# for line in tqdm(data):
#     if "Method choise" in line:
#         current_method = line.split(" ")[-1].strip()
#         image_name = line.split(":")[0].strip()

#         # Check in Folder1
#         source_path_folder1 = os.path.join(folder1_path, image_name)
#         destination_path_folder1 = os.path.join(current_method, image_name)
        
#         # Check in Folder2 if not found in Folder1
#         if not os.path.exists(source_path_folder1):
#             source_path_folder2 = os.path.join(folder2_path, image_name)
#             destination_path_folder2 = os.path.join(current_method, image_name)
            
#             # Move to Folder2 if found
#             if os.path.exists(source_path_folder2):
#                 shutil.move(source_path_folder2, destination_path_folder2)
#         else:
#             # Move to Folder1
#             shutil.move(source_path_folder1, destination_path_folder1)

# print("Images organized into folders based on Method choise.")


# # # Create folders for each Method choise
# # for line in data:
# #     if "Method choise" in line:
# #         method_choice = line.split(" ")[-1].strip()
# #         os.makedirs(method_choice, exist_ok=True)

# # # Move images to corresponding folders
# # current_method = None
# # counter = 0
# # for line in tqdm(data):
# #     counter+=1
# #     if "Method choise" in line:
# #         current_method = line.split(" ")[-1].strip()        
# #     elif "Random choise" in line:
# #         image_name = line.split(" ")[-1].strip()
# #         source_path = os.path.join('/media/jithin/DATA/2024/ImageJ_Augmentation_project/data/object_detection/temp_augmented_folder2',image_name)
# #         destination_path = os.path.join('/media/jithin/DATA/2024/ImageJ_Augmentation_project/code/pre_processing', current_method, image_name)
# #         shutil.move(source_path, destination_path)

# # print("Images organized into folders based on Method choise.")


# # Function to read image names from a text file
# def read_image_names(file_path):
#     with open(file_path, 'r') as file:
#         return {line.split(":")[0].strip() for line in file}

# # Function to remove redundant lines from the second file
# def remove_redundant_lines(file1, file2):
#     image_names_set = read_image_names(file1)

#     with open(file2, 'r') as input_file:
#         lines = input_file.readlines()

#     with open(file2, 'w') as output_file:
#         for line in lines:
#             image_name = line.split(":")[0].strip()
#             if image_name not in image_names_set:
#                 output_file.write(line)

# # Replace these file paths with your actual file paths
# file1_path = '/media/jithin/DATA/2024/ImageJ_Augmentation_project/data/object_detection/Augmentation_Log_methods.txt'
# file2_path = '/media/jithin/DATA/2024/ImageJ_Augmentation_project/data/object_detection/Augmentation_Log_methods2.txt'

# remove_redundant_lines(file1_path, file2_path)
# print("Redundant lines removed from the second file.")


# import os
# import shutil

# def copy_annotation_files(main_folder, annotation_folder, output_folder):
#     # Iterate through subfolders in the main folder
#     for subfolder in os.listdir(main_folder):
#         subfolder_path = os.path.join(main_folder, subfolder)
        
#         # Check if the item is a directory and not a file
#         if os.path.isdir(subfolder_path):
#             # Iterate through files in the subfolder
#             for filename in os.listdir(subfolder_path):
#                 # Check if the file is a PNG image
#                 if filename.lower().endswith(".png"):
#                     image_name, _ = os.path.splitext(filename)
                    
#                     # Construct the source and destination paths for the annotation file
#                     annotation_source = os.path.join(annotation_folder, f"{image_name}.txt")
#                     annotation_destination = os.path.join(subfolder_path, f"{image_name}.txt")
                    
#                     # Check if the corresponding annotation file exists
#                     if os.path.exists(annotation_source):
#                         # Copy the annotation file to the subfolder
#                         shutil.copy(annotation_source, annotation_destination)
#                         print(f"Copied {annotation_source} to {annotation_destination}")

# # Example usage:
# main_folder_path = "/media/jithin/DATA/2024/ImageJ_Augmentation_project/data/object_detection/augmented_data"
# annotation_folder_path = "/media/jithin/DATA/2024/ImageJ_Augmentation_project/data/object_detection/YOLOv8_dataset_version2/labels/train"
# output_folder_path = "/media/jithin/DATA/2024/ImageJ_Augmentation_project/data/object_detection/augmented_data"

# copy_annotation_files(main_folder_path, annotation_folder_path, output_folder_path)



import os
import shutil

def copy_annotation_files(main_folder, annotation_folder, output_folder):
    # Check if the main folder exists
    if not os.path.exists(main_folder):
        print(f"Main folder '{main_folder}' does not exist.")
        return

    # Check if the output folder exists, create it if not
    if not os.path.exists(output_folder):
        os.makedirs(output_folder)

    # Iterate through files in the main folder
    for filename in os.listdir(main_folder):
        # Check if the file is a PNG image
        if filename.lower().endswith(".png"):
            image_name, _ = os.path.splitext(filename)

            # Construct the source and destination paths for the annotation file
            annotation_source = os.path.join(annotation_folder, f"{image_name}.txt")
            annotation_destination = os.path.join(output_folder, f"{image_name}.txt")

            # Check if the corresponding annotation file exists
            if os.path.exists(annotation_source):
                # Copy the annotation file to the output folder
                shutil.copy(annotation_source, annotation_destination)
                print(f"Copied {annotation_source} to {annotation_destination}")

# Example usage:
main_folder_path = "/path/to/main_folder"
annotation_folder_path = "/path/to/annotation_folder"
output_folder_path = "/path/to/output_folder"

copy_annotation_files(main_folder_path, annotation_folder_path, output_folder_path)
