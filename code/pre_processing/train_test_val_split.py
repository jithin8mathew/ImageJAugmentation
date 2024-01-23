import os
import random
import shutil
from tqdm import tqdm

def split_dataset(source_folder, destination_folder, split_ratios=(0.8, 0.1, 0.1)):
    count = 0
    
    image_folder = os.path.join(destination_folder, 'images')
    label_folder = os.path.join(destination_folder, 'labels')

    # Create necessary folders
    for folder in [image_folder, label_folder]:
        for subset in ['train', 'test', 'val']:
            subset_path = os.path.join(folder, subset)
            os.makedirs(subset_path, exist_ok=True)

    # Collect images and labels
    images = [f for f in os.listdir(source_folder) if f.endswith('.png')]
    random.shuffle(images)

    num_images = len(images)
    train_size = int(split_ratios[0] * num_images)
    test_size = int(split_ratios[1] * num_images)

    train_images = images[:train_size]
    test_images = images[train_size:train_size + test_size]
    val_images = images[train_size + test_size:]

    # Copy images and labels to corresponding folders with tqdm progress bar
    for subset, subset_images in zip(['train', 'test', 'val'], [train_images, test_images, val_images]):
        for image in tqdm(subset_images, desc=f"Copying {subset} images and labels"):
            image_path = os.path.join(source_folder, image)
            label_path = os.path.join(source_folder, image.replace('.png', '.txt'))

            dest_image_path = os.path.join(image_folder, subset, image)
            dest_label_path = os.path.join(label_folder, subset, image.replace('.png', '.txt'))

            try:
                shutil.copy(image_path, dest_image_path)
                shutil.copy(label_path, dest_label_path)
            except Exception:
                count+=1
    print(count)

# Example usage
source_folder_A = ''
destination_folder = ''

split_dataset(source_folder_A, destination_folder)
