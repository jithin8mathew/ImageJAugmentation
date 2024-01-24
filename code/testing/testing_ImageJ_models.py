from ultralytics import YOLO

model = YOLO('/home/jithin/Documents/2024/ImageJ_project/models/object_detection/Original_OD_01_23_2024/weights/best.pt')  # load a custom model

metrics = model.val(data='data_validation.yaml',
					 imgsz=640, 
					 batch= 4,
					 iou = 0.8,
					 max_det=1000	
					)  # no arguments needed, dataset and settings remembered
metrics.box.map    # map50-95
metrics.box.map50  # map50
metrics.box.map75  # map75


print(metrics)