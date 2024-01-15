import os

def calculate_augmentation_ratio(input_folder):
    folder_tree = {}
    # Iterate through each subfolder and crop images
    for root, sub_folders, files in os.walk(input_folder):
        for sub_folder in sub_folders:
            sub_folder_path = os.path.join(root, sub_folder)
            num_files = len(os.listdir(sub_folder_path))
            folder_tree[sub_folder] = num_files

    return folder_tree

def image_count_estimation(total_original_images):
    percentage_list = [20,30,40,50]
    image_count_for_percentage = [((total_original_images * _ )/100) for _ in percentage_list]
    return image_count_for_percentage


result = calculate_augmentation_ratio('/media/jithin/DATA/2024/ImageJ_Augmentation_project/data/image_classification/original_dataset_v2')
print("Total original images : ", sum(result.values()))
no_images = image_count_estimation(sum(result.values()))
print("Images to be augmented for each percentage : ", no_images)
no_images_per_class = zip(result.keys(),[_/len(result.keys()) for _ in no_images])
print("No of images per class :", dict(no_images_per_class))