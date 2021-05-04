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
 * 
 * Author: Jithin Mathew
 * Versoin: 1.0
 * ImageJ (FiJi version): 
 */


public class ImageAugmentor implements PlugIn {

	Color bk = Color.black;
	Color wi = Color.white;
	Color bgc = new Color(71,75,80);
	Color fgc = new Color(200,200,200);
	
	

	public void run(String arg) {

		Font font0 = new Font("Arial", Font.BOLD, 16);
		Font font1 = new Font("Arial", Font.BOLD, 14);	
		Font font2 = new Font("Arial", Font.BOLD, 12);	

		ImageIcon icon = new ImageIcon("F:\\imageJ\\project\\augmentor_.png");
		ImageIcon icon2 = new ImageIcon("F:\\imageJ\\project\\logo_50.png");
		
		GenericDialogPlus gui = new GenericDialogPlus("Image Augmenter");

		gui.setBackground(bgc);
		gui.setForeground(fgc);
		gui.addImage(icon);
		gui.addToSameRow();
//		gui.addMessage("_________Image Augmentor_________", font0, Color.darkGray);
		gui.addMessage("Augment images in X folds for large scale deep learning ", font0, Color.white);

//		gui.addMessage(" ", font1, Color.darkGray);
		gui.addDirectoryField("Select Input Image directory:", "F:\\imageJ\\project\\Batch_Input_Images");
		gui.addDirectoryField("Select Output Annotation directory:", "F:\\imageJ\\project\\Batch_Output_Images");
		gui.addNumericField("Generate Images:",20);
		gui.addMessage("Keep the probability high to augment most of the images", font2, Color.white);
		gui.addMessage("By default the parameters are set for best performance", font2, Color.white);
		gui.addToSameRow();
		gui.addSlider(" ",0,100,80);
		gui.addToSameRow();
		gui.addMessage("Overall Probability (recommended:80%)", font2, fgc);
		
//		gui.addMessage(" ", font1, Color.darkGray);
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
		
//		gui.addCheckbox("Random crop", false);
//		gui.addToSameRow();
//		gui.addSlider("Probability",1,100,10);
		
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
//		gui.addToSameRow();
		gui.addImage(icon2);
		gui.addToSameRow();
		gui.addMessage("__-Jithin Mathew_", font0, Color.white);
		
//		String[] outputFormats = {"YOLO", "Pascal Voc"};
//		gui.addRadioButtonGroup("Format", outputFormats, 5, 1, "YOLO");
//		gui.addImage(icon);

//		Panel panel = new Panel();
//		Button b = new Button("...");
//		panel.add( b);
//		gui.addPanel( panel );
		
//		FileGroup fg = new FileGroup("files");
// 		FileGroupDialog fgd = new FileGroupDialog(fg, false);
//  		gui.addPanel(fgd);
		
		gui.showDialog();
		if (gui.wasCanceled()) return;

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
		
//		Boolean crop = gui.getNextBoolean();
//		double crop_per = gui.getNextNumber();
		
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
//		String Oformat = gui.getNextRadioButton();

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
//		IJ.log(""+Oformat);
		
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
			int random_chosenFile = random.nextInt(list.length); 	// generate a random number based on the lenth of the list
			IJ.log("Random choise "+list[random_chosenFile]);		// chose a random file from the list based on the random number
			
			if (boolArray.size()>0){
				count+=1;
				int random_AugMethod = random.nextInt(boolArray.size());
				IJ.log(count+" Method choise "+boolArray.get(random_AugMethod));	
				displayImage(boolArray.get(random_AugMethod), FolderPath1 +"\\"+ list[random_chosenFile], FolderPath2, doubleArray.get(random_AugMethod), count, scale_minmax, rotation_ang, noise_factor, gamm_factor, contra_scale, bri_scale, gdisto_scale, Oformat);
				}		
			else {
				IJ.log("No Augmentation Method chosen");
				}	
			}
				
//		openFile();
		

		/**
		 * <h4> Optional</h4>
		 * @param Boolean condition : Set to True if output to be displayed 
		 * Setting display image to can add memory inetensive and time consuming operation 
		 * It is recommened to set the parameter to false for faster operations
		 */
	}

			void displayImage(String method, String image, String  FolderPath2, double prob_ , int count, double scale_minmax, double rotation_ang, double noise_factor, double gamm_factor, double contra_scale, double bri_scale, double gdisto_scale, String OF){
				/**
				* By default Clown.jpg image is chosen
				* @param IJ Image: Chagne the image name from the list of imageJ samples to work on it
				* Comment of methods to prevent it from runnig
				*/
				
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

				// contrast 
				//brightness

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

//				randomLogarithmic(ip);
//				addSalt(ip);
//				imp.updateAndDraw();
				IJ.saveAs(OF, FolderPath2 +"\\"+ count);
//				imp.changes = true;
				imp.close();
			
			}

//				public void openFile() {
//			
//					String curDir = OpenDialog.getDefaultDirectory();
//					IJ.log("\nCurrent directory = " + curDir);
//					String lastDir = OpenDialog.getLastDirectory();
//					IJ.log("Last directory = " + curDir);
//			
//					String currentDirectory = (OpenDialog.getLastDirectory() == null) ? 
//						 OpenDialog.getDefaultDirectory() : OpenDialog.getLastDirectory();
//			
//					IJ.log("Now the current directory = " + currentDirectory);		 
//					String fname = "gems_rainbow.jpg";
//					IJ.log("File with path = " + currentDirectory+fname);
//			
//					ImagePlus img1 = IJ.openImage(currentDirectory+fname);
//					img1.show();
//				}

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
//				IJ.log("value"+val1);
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


			

//			void applyGaussian(ImageProcessor ipfg, double sigma){
//				/**
//				 * This function applies gaussian blur to image	for augmenting iamges with blurness 
//				 * @param int sigma: Sigma value according to which Gaussian blur will be applied 
//				 * Experimental method
//				 */
//
//				GaussianBlur gb = new GaussianBlur();
//				double radius = sigma;
//				gb.blurGaussian(ipfg, radius, radius, 0.02);
//				
//				ImagePlus imxy = new ImagePlus("Sobel filter", ipfg);
//				imxy.updateAndDraw();
//						
//				}
			
//			void randomLogarithmic(ImageProcessor ipf){
//				ipf.log();
//				}
//			
			void addSalt(ImageProcessor ipf){

				/**
				* Function ot add Salt and pepper noise to image using basic image operations
				* The default noise colors are black and white 
				* Further improvements and colors will be added in the future versions
				* @param int percentage: Percentage of noise to be added
				*/
		
				Random randomGenerator = new Random();
				
				int w = ipf.getWidth();
				int h = ipf.getHeight();
		
				int infill=0;
				int percent =0;
				
				int randomInt = randomGenerator.nextInt(2);
				ipf.setColor(bk);
				infill = 10 + (int)(Math.random() * ((60 - 10) + 1)); // random percentage between 10 and 90
				percent = (int)(infill * (w*h) / 100);			   // number of pixels to be filled with dots to achieve salt and pepper noise
						
				for (int i =0; i<=percent; i++){
						int u = 0 + (int)(Math.random() * ((w - 0) + 1));  // get random x, y coordinates 
						int v = 0 + (int)(Math.random() * ((h - 0) + 1));
						ipf.setColor(wi);
						ipf.setLineWidth(1);
						ipf.drawDot(u,v);
						}

//				ImagePlus imsp = new ImagePlus("salt pepper", ipf);
//				imsp.updateAndDraw();
						
				IJ.log("Percentage of noise on right:"+infill);
				IJ.log("Number of pixels to be altered based on percentage on side1: "+percent);
				
			}

}