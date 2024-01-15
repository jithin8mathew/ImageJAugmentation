import torch
import torch.nn as nn
import torchvision.transforms as transforms
from torchvision import datasets
from torch.utils.data import DataLoader, random_split

# from torchvision.models import vgg16
from torchvision.models import squeezenet1_0

# Define transformation for the input images
transform = transforms.Compose([
    transforms.Resize((256, 256)),
    transforms.ToTensor(),
    transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
])

# Load the dataset from the folder with 8 subfolders
dataset = datasets.ImageFolder(root='/media/jithin/DATA/2024/ImageJ_Augmentation_project/data/image_classification/original_dataset_v2', transform=transform)

# Split the dataset into train and test sets
train_size = int(0.9 * len(dataset))
test_size = len(dataset) - train_size
train_dataset, test_dataset = random_split(dataset, [train_size, test_size])

BATCH_SIZE = 32

# Create data loaders
train_loader = DataLoader(train_dataset, batch_size=BATCH_SIZE, shuffle=True)
test_loader = DataLoader(test_dataset, batch_size=BATCH_SIZE, shuffle=False)

# # Define the VGG16 model
# model = vgg16(pretrained=True)

# SqueezeNet aims to achieve a high level of accuracy with fewer parameters. It can be suitable for scenarios where model size is a critical factor.
model = squeezenet1_0(pretrained=True)

# Modify the output layer for 8 classes
num_features = model.classifier[1].in_channels
model.classifier[1] = nn.Linear(num_features, 8)  # Assuming 8 classes


# Specify device and criterion
device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
criterion = nn.CrossEntropyLoss()
model.to(device)
criterion.to(device)

# Specify optimizer
optimizer = torch.optim.SGD(model.parameters(), lr=0.001, momentum=0.9)

# Training loop
num_epochs = 100

# Create a log file to record metrics
log_file_path = '/media/jithin/DATA/2024/ImageJ_Augmentation_project/models/classification/original_dataset/squeezenet1_0_training_log.txt'
with open(log_file_path, 'w') as log_file:
    log_file.write("Epoch\tTraining Loss\tTraining Accuracy\tValidation Accuracy\n")

# Initialize variables to track the best model
best_accuracy = 0.0
best_epoch = 0

for epoch in range(num_epochs):
    model.train()
    total_correct = 0
    total_samples = 0
    running_loss = 0.0

    for batch_idx, (inputs, labels) in enumerate(train_loader):
        inputs, labels = inputs.to(device), labels.to(device)

        optimizer.zero_grad()
        outputs = model(inputs)
        loss = criterion(outputs, labels)
        loss.backward()
        optimizer.step()

        _, predicted = torch.max(outputs.data, 1)
        total_samples += labels.size(0)
        total_correct += (predicted == labels).sum().item()
        running_loss += loss.item()

    # Calculate training accuracy and average loss for the epoch
    accuracy = total_correct / total_samples
    average_loss = running_loss / len(train_loader)

    # Print and write metrics to the log file
    print(f'Epoch [{epoch+1}/{num_epochs}], Loss: {average_loss:.4f}, Training Accuracy: {accuracy * 100:.2f}%')

    with open(log_file_path, 'a') as log_file:
        log_file.write(f"{epoch+1}\t{average_loss:.4f}\t{accuracy * 100:.2f}\t")

    # Evaluate the model on the validation set
    model.eval()
    correct_val = 0
    total_val = 0
    with torch.no_grad():
        for inputs_val, labels_val in test_loader:
            inputs_val, labels_val = inputs_val.to(device), labels_val.to(device)
            outputs_val = model(inputs_val)
            _, predicted_val = torch.max(outputs_val.data, 1)
            total_val += labels_val.size(0)
            correct_val += (predicted_val == labels_val).sum().item()

    accuracy_val = correct_val / total_val
    print(f'Validation Accuracy: {accuracy_val * 100:.2f}%')

    with open(log_file_path, 'a') as log_file:
        log_file.write(f"{accuracy_val * 100:.2f}\n")

    # Save a checkpoint if the validation accuracy improves
    if accuracy_val > best_accuracy:
        best_accuracy = accuracy_val
        best_epoch = epoch + 1
        best_checkpoint_path = f'/media/jithin/DATA/2024/ImageJ_Augmentation_project/models/classification/original_dataset/squeezenet1_0_best_model_checkpoint.pth'
        torch.save({
            'epoch': epoch+1,
            'model_state_dict': model.state_dict(),
            'optimizer_state_dict': optimizer.state_dict(),
            'loss': average_loss,
            'accuracy': accuracy,
            'validation_accuracy': accuracy_val,
        }, best_checkpoint_path)

# Print the best model information
print(f'Best Model - Epoch: {best_epoch}, Validation Accuracy: {best_accuracy * 100:.2f}%')
