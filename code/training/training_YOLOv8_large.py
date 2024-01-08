from ultralytics import YOLO

# Load a YOLOv8 model from a pre-trained weights file
model = YOLO('yolov8l.pt')

# Run MODE mode using the custom arguments ARGS (guess TASK)

results = model.train(data='/home/pauloflores/Documents/imageJ_project/dataset/YOLOv8_data_format/data.yaml', 
                      epochs=100, 
                      imgsz=1024, 
                      save=True, 
                      # device=[0,1], 
                      batch = 8,
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

