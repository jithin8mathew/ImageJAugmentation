//package my.packages;

import ij.*;
import ij.process.*;
import ij.CompositeImage;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.frame.*;
import java.util.Random;
import java.awt.Graphics2D;
import ij.plugin.filter.GaussianBlur;
import java.io.*;
import ij.io.OpenDialog;
import ij.gui.GenericDialog;
import java.awt.Font;
import fiji.util.gui.GenericDialogPlus;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.*;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

 
/**
 * <h3> Packages for basic image Augmentation operations using ImageJ - Jithin Mathew</h3>
 * Author: Jithin Mathew
 * Versoin: 1.1
 * ImageJ (FiJi version):   
 */


public class ImageAugmentor implements PlugIn {

	// Define color constants
	Color bk = Color.black;
	Color wi = Color.white;
	Color bgc = new Color(0,75,80); // Custom background color (Teal)
	Color fgc = new Color(255,200,46); // Custom background color (Harvest Gold)
	
	// Plugin run method
	public void run(String arg) {
		
		// Define font styles
		Font font0 = new Font("Arial", Font.BOLD, 16);
		Font font1 = new Font("Arial", Font.BOLD, 14);	
		Font font2 = new Font("Arial", Font.BOLD, 12);	

		ImageIcon icon = new ImageIcon("Path to logo .png");
		ImageIcon icon2 = new ImageIcon("Path to logo .png");
		
		// Create a GenericDialogPlus for user input
		GenericDialogPlus gui = new GenericDialogPlus("Image Augmenter");

		// Customize dialog appearance
		gui.setBackground(bgc);
		gui.setForeground(fgc);
		gui.addImage(icon);
		gui.addToSameRow();
		gui.addMessage("Augment Images for Deep Learning ", font0, Color.white);

		// Add input fields, sliders, and checkboxes for augmentation parameters
		gui.addDirectoryField("Select Input Image directory:", "Path to source image directory /Batch_Input_Images");
		gui.addDirectoryField("Select Save Output directory:", "Path to output image directory / Batch_Output_Images");
		gui.addNumericField("Generate Images:",20); // Number of augemneted image that user desire to generate 
		gui.addMessage("Keep the probability high to augment most of the images", font2, Color.white);
		gui.addMessage("By default the parameters are set for best performance", font2, Color.white);
//		gui.addToSameRow();
		
//		gui.addToSameRow();
//		gui.addMessage("Overall Probability (recommended:80%)", font2, fgc);
		gui.addSlider("Overall Probability (recommended:80%)",0,100,80); // An overall probability
		
		gui.addCheckbox("Random Flip (Horizontal/Vertical)", true); 
		gui.addToSameRow();
		gui.addSlider("Probability",0,100,50);
		
		gui.addCheckbox("Random Scaling", true);
		gui.addToSameRow();
		gui.addSlider("Probability",1,70,5);
		gui.addToSameRow();
		gui.addSlider("Max and Min Scaling",0.1,1.5,1.1);
		
		gui.addCheckbox("Random Rotation", true);
		gui.addToSameRow();
		gui.addSlider("Probability",0,100,50);
		gui.addToSameRow();
		gui.addSlider("Max and Min of rotation",0,360,280);
		
		gui.addCheckbox("Random Noise", true);
		gui.addToSameRow();
		gui.addSlider("Probability",1,100,50);
		gui.addToSameRow();
		gui.addSlider("Noise Factor",1,200,100);

		gui.addCheckbox("Random Exponential Transform", true);
		gui.addToSameRow();
		gui.addSlider("Probability",1,100,50);

		gui.addCheckbox("Random Gamma", true);
		gui.addToSameRow();
		gui.addSlider("Probability",1,100,50);
		gui.addToSameRow();
		gui.addSlider("Gamma value",1,10,2);
		
		gui.addCheckbox("Contrast", true);
		gui.addToSameRow();
		gui.addSlider("Probability",1,100,50);
		gui.addToSameRow();
		gui.addSlider("Scale",1,100,50);
		
		gui.addCheckbox("Brightness", true);
		gui.addToSameRow();
		gui.addSlider("Probability",1,100,50);
		gui.addToSameRow();
		gui.addSlider("Scale",1,100,50);

		gui.addCheckbox("Smooth", true);
		gui.addToSameRow();
		gui.addCheckbox("Sharpen", true);
		gui.addToSameRow();
		gui.addSlider("Smooth and Sharpen P",1,100,50);
		
		gui.addCheckbox("Gaussian Distortion", true);
		gui.addToSameRow();
		gui.addSlider("Probability",1,100,50);
		gui.addToSameRow();
		gui.addSlider("Scale",1,100,1);
		
		String[] imageOutFormat = {"PNG", "JPG", "JPEG", "GIF"};
		gui.addChoice("Image Out Format",imageOutFormat,"PNG"); 
		gui.addImage(icon2);
//		gui.addToSameRow();
		gui.addMessage("Author : Jithin Mathew", font2, Color.white);
		gui.addMessage("version : 1.1", font2, Color.white);
		
		
		gui.showDialog();
		if (gui.wasCanceled()) return; // If cancel button is clicked, the GUI window is closed 

		String FolderPath1 = gui.getNextString();
		String FolderPath2 = gui.getNextString();
		double A_num = gui.getNextNumber();
		double A_prob = gui.getNextNumber();
		
		Boolean flip = gui.getNextBoolean();
		double flip_per = gui.getNextNumber();
		
		Boolean scale = gui.getNextBoolean();
		double scale_per = gui.getNextNumber();
		double scale_minmax = gui.getNextNumber();
		
		Boolean rot = gui.getNextBoolean();
		double rotation_per = gui.getNextNumber();
		double rotation_ang = gui.getNextNumber();
		
		Boolean noise = gui.getNextBoolean();
		double noise_per = gui.getNextNumber();
		double noise_factor = gui.getNextNumber();

		Boolean exptr = gui.getNextBoolean();
		double exptr_per = gui.getNextNumber();

		Boolean gamm = gui.getNextBoolean();
		double gamm_per = gui.getNextNumber();
		double gamm_factor = gui.getNextNumber();
				
		Boolean contra = gui.getNextBoolean();
		double contra_percent = gui.getNextNumber();
		double contra_scale = gui.getNextNumber();
		
		Boolean bri = gui	.getNextBoolean();
		double bri_percent = gui.getNextNumber();
		double bri_scale = gui.getNextNumber();
		
		Boolean smooth = gui.getNextBoolean();
		Boolean sharpen = gui.getNextBoolean();
		double S_and_S_prob = gui.getNextNumber();
		
		Boolean Gdisto = gui.getNextBoolean();
		double gdisto_percent = gui.getNextNumber();
		double gdisto_scale = gui.getNextNumber();
		
		String Oformat = gui.getNextChoice();

		IJ.log("Image directory : "+FolderPath1);
		IJ.log("Output directory : "+FolderPath2);
		IJ.log("Flip : "+flip);
		IJ.log("Flip Probability : "+flip_per);
		IJ.log("Scale : "+scale);
		IJ.log("Scale Probability : "+scale_per);
		IJ.log("Scale by Factor : "+scale_per);
		IJ.log("Rotate : "+rot);
		IJ.log("Rotate Probability : "+rotation_per);
		IJ.log("Rotate Maximum Angle: "+rotation_ang);
		IJ.log("Noise : "+noise);
		IJ.log("Noise Probability : "+noise_per);
		IJ.log("Noise factor: "+noise_factor);
		IJ.log("Exponential : "+exptr);
		IJ.log("Exponential Probability : "+exptr_per);
		IJ.log("Gamma : "+gamm);
		IJ.log("Gamma Probability : "+gamm_per);
		IJ.log("Gamma Factor : "+gamm_factor);
		IJ.log("Contrast : "+contra);
		IJ.log("Contrast Probability : "+contra_percent);
		IJ.log("Contrast Scale : "+contra_scale);
		IJ.log("Brightness : "+bri);
		IJ.log("Brightness Probability : "+bri_percent);
		IJ.log("Brightness Scale : "+bri_scale);
		IJ.log("Smooth : "+smooth);
		IJ.log("Sharpen : "+sharpen);
		IJ.log("Smoothness and Sharpness Probability : "+S_and_S_prob);
		IJ.log("Gaussian Blur : "+Gdisto);
		IJ.log("Gaussian Blur Probability : "+gdisto_percent);
		IJ.log("Gaussian Blur Factor : "+gdisto_scale);
		IJ.log("Output Format : "+Oformat);
		
		String[] list = new File(FolderPath1).list(); // this section of the code reads the files in the folder and save it to a list
		for (int i=0; i<list.length; i++) {			  // Iterate through list and print the filenames
			IJ.log(list[i]);
			}
		IJ.log(""+list.length);

		java.util.Random random = new java.util.Random();

		int aug = 0;
		List<String> boolArray = new ArrayList<String>();
		List<Double> doubleArray = new ArrayList<Double>();
		
		if (flip == true){
			boolArray.add("FLIP");	
			doubleArray.add(flip_per);
			}
		if (scale == true){
			boolArray.add("SCALE");	
			doubleArray.add(scale_per);
			}
		if (rot == true){
			boolArray.add("ROTATION");	
			doubleArray.add(rotation_per);
			}
		if (noise == true){
			boolArray.add("NOISE");	
			doubleArray.add(noise_per);
			}
		if (exptr == true){
			boolArray.add("EXPTR");	
			doubleArray.add(exptr_per);
			}
		if (gamm == true){
			boolArray.add("GAMMA");	
			doubleArray.add(gamm_per);
			}
		if (contra == true){
			boolArray.add("CONTRAST");
			doubleArray.add(contra_percent);	
			}
		if (bri == true){
			boolArray.add("BRIGHTNESS");
			doubleArray.add(bri_percent);	
			}
		if (smooth == true){
			boolArray.add("SMOOTH");
			doubleArray.add(S_and_S_prob);	
			}
		if (sharpen == true){
			boolArray.add("SHARPEN");
			doubleArray.add(S_and_S_prob);	
			}
		if (Gdisto == true){
			boolArray.add("GDISTORTION");
			doubleArray.add(gdisto_percent);	
			}
			
		IJ.log("List of methods chosen : "+boolArray);
		IJ.log("Corresponding probabilities : "+doubleArray);

		int count=0;
		
		for (aug = 0; aug<= A_num; aug++){
			int random_chosenFile = random.nextInt(list.length); 	// generate a random number based on the lenth of the list (Number of images in the input folder)
			IJ.log("Random choise "+list[random_chosenFile]);		// Add to log file 
			
			if (boolArray.size()>0){ 	// Check if any augmentation methods are selected
				count+=1;				// Increment the count of augmented images
				int random_AugMethod = random.nextInt(boolArray.size()); // Randomly select an agmentation method if it was selected by the use using checkbox
				IJ.log(count+" Method choise "+boolArray.get(random_AugMethod));	
				// calling the function to perform Image Augmentation
				displayImage(boolArray.get(
					random_AugMethod), 
					FolderPath1 +"\\"+ list[random_chosenFile], 
					FolderPath2, 
					doubleArray.get(random_AugMethod), 
					count, 
					scale_minmax, 
					rotation_ang, 
					noise_factor, 
					gamm_factor, 
					contra_scale, 
					bri_scale, 
					gdisto_scale, 
					Oformat);  
				}		
			else {
				IJ.log("No Augmentation Method chosen");
				}	
			}
	}
			// FUNCTION TO PERFORM IMAGE AUGMENTATION 
			void displayImage(String method, String image, String  FolderPath2, double prob_ , int count, double scale_minmax, double rotation_ang, double noise_factor, double gamm_factor, double contra_scale, double bri_scale, double gdisto_scale, String OF){
				
				IJ.open(image);				
				ImagePlus imp = IJ.getImage();
				ImageProcessor ip = imp.getProcessor();
				
				java.util.Random ovrl_random = new java.util.Random();
				
				int main_prob = ovrl_random.nextInt(100);

				if (method == "FLIP" & prob_ >= main_prob){
					radomFlip(ip);
					}
					
				if (method == "EXPTR" & prob_ >= main_prob){
					randomExponential(ip);	
					}

				if (method == "SCALE" & prob_ >= main_prob){
//					IJ.log("SCALE "+scale_minmax +" "+ scale_minmax);
					randomScale(ip, scale_minmax, scale_minmax);	
					}

				if (method == "ROTATION" & prob_ >= main_prob){
//					IJ.log("ROTATE "+rotation_ang );
					randomRotate(ip, rotation_ang);	
					}

				if (method == "NOISE" & prob_ >= main_prob){
//					IJ.log("NOISE "+noise_factor );
					randomNoise(ip, noise_factor);	
					}


				if (method == "GAMMA" & prob_ >= main_prob){
//					IJ.log("GAMMA "+gamm_factor );
					randomGamma(ip,gamm_factor);	
					}

				if (method == "CONTRAST" & prob_ >= main_prob){
//					IJ.log("CONTRAST "+contra_scale );
					randomContrast(imp, contra_scale);
					}

				if (method == "BRIGHTNESS" & prob_ >= main_prob){
//					IJ.log("BRIGHTNESS "+bri_scale );
					randomBrightness(imp, bri_scale);
					}


				if (method == "SMOOTH" & prob_ >= main_prob){
					randomSmooth(ip);	
					}

				if (method == "SHARPEN" & prob_ >= main_prob){
					randomSharpen(ip);	
					}

				if (method == "GDISTORTION" & prob_ >= main_prob){
//					IJ.log("GDST "+gdisto_scale );
					gBlur(ip,gdisto_scale);	
					}				

				IJ.saveAs(OF, FolderPath2 +"\\"+ count);
				imp.close();
			
			}


			void radomFlip(ImageProcessor ipf){
					ipf.flipHorizontal();
//					ipf.flipVertical();
				}
				
			void randomScale(ImageProcessor ipf, double x, double y){
				ipf.scale(x, y);
				}

			void randomRotate(ImageProcessor ipr, double rotation_ang){
				java.util.Random random = new java.util.Random();
				double val1 = random.nextInt((int)rotation_ang - 0 + 1) + 0;
				ipr.rotate(val1);
				}

			void randomNoise(ImageProcessor ipf, double y){
				java.util.Random random = new java.util.Random();
				double val = random.nextInt((int)y - 0 + 1) + 0;
				ipf.noise(val);
				}

			void randomExponential(ImageProcessor ipf){
				ipf.exp();
				}

			void randomGamma(ImageProcessor ipf, double sigma){
				java.util.Random random = new java.util.Random();
				double val = random.nextInt((int)sigma - 0 + 1) + 0;
				ipf.gamma(val);
				}

			void randomContrast(ImagePlus impf, double contra_scale){
				java.util.Random random = new java.util.Random();
				double ID = random.nextInt(1 - 0 + 1) + 0;
				if (ID == 0.0){
						double val = random.nextInt((int)((126*contra_scale)/100) - 0 + 1) + 0;
						impf.setDisplayRange(0+val, 255-val);
						IJ.run(impf, "Apply LUT", "");
						impf.changes = false;
					}
				if (ID == 1.0){
						double val = random.nextInt((int)((800*contra_scale)/100) - 0 + 1) + 0;
						impf.setDisplayRange(0-val, 255+val);
						IJ.run(impf, "Apply LUT", "");	
						impf.changes = false;
					}
				
				}

			void randomBrightness(ImagePlus impf, double bri_scale){
				java.util.Random random = new java.util.Random();
				double ID = random.nextInt(1 - 0 + 1) + 0;
				if (ID == 0.0){
						double val = random.nextInt((int)((126*bri_scale)/100) - 0 + 1) + 0;
						impf.setDisplayRange(0+val, 255+val);
						IJ.run(impf, "Apply LUT", "");	
						impf.changes = false;
					}
				if (ID == 1.0){
						double val = random.nextInt((int)((125*bri_scale)/100) - 0 + 1) + 0;
						impf.setDisplayRange(0-val, 255-val);
						IJ.run(impf, "Apply LUT", "");	
						impf.changes = false;
					}
				}

			void randomSmooth(ImageProcessor ipf){
				ipf.smooth();
				}
	
			void randomSharpen(ImageProcessor ipf){
				ipf.sharpen();
				}

			void gBlur(ImageProcessor ipf, double sigma){ // sigma value must be between 0 and 100
				java.util.Random random = new java.util.Random();
				double val = random.nextInt((int)sigma - 0 + 1) + 0;
				ipf.blurGaussian(val);
				}

}
