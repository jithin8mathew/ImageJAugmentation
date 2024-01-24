from ultralytics import YOLO

# Load a pretrained YOLOv8n model
model = YOLO('/home/jithin/Documents/2024/ImageJ_project/models/object_detection/Non_augmented_OD8/weights/best.pt')

# Run inference on 'bus.jpg' with arguments
model.predict(source='/home/jithin/Documents/2024/ImageJ_project/data/object_detection/augmented_data/ROTATION', save=False, imgsz=1024, conf=0.5, save_txt=True)