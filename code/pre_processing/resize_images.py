import os
from PIL import Image
from tqdm import tqdm

def crop_and_replace(input_folder, target_size):
    # Iterate through each subfolder and crop images
    for root, _, files in os.walk(input_folder):
        for file in tqdm(files, desc=f'Cropping images in {root}', unit='image'):
            input_path = os.path.join(root, file)

            # Open the image using PIL
            with Image.open(input_path) as img:
                # Calculate cropping box to get a center crop
                width, height = img.size
                left = (width - min(width, height)) // 2
                top = (height - min(width, height)) // 2
                right = (width + min(width, height)) // 2
                bottom = (height + min(width, height)) // 2

                # Crop the image
                cropped_img = img.crop((left, top, right, bottom))

                # Resize the cropped image to the target size
                resized_img = cropped_img.resize(target_size, Image.ANTIALIAS)

                # resize again to 1024
                resized_img = resized_img.resize((1024,1024), Image.ANTIALIAS)

                # Save the resized image, replacing the original
                resized_img.save(input_path)

if __name__ == "__main__":
    # Specify the input folder and target size
    input_folder = '/home/jithin/Documents/2024/ImageJ_project/data/main_dataset_v2'
    target_size = (2268, 2268)

    # Call the crop_and_replace function
    crop_and_replace(input_folder, target_size)

    print("Image cropping and replacement completed.")
