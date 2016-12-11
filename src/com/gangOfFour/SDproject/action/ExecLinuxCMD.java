package com.gangOfFour.SDproject.action;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;  
import java.io.LineNumberReader;  
       
    /** 
     * 
     * @author lanxuan
     */  
    public class ExecLinuxCMD {  
    	private static String imageName="fan2dog.jpg";
    	private int imageType;

        private static Object exec(String cmd) {  
            try {  
                String[] cmdA = { "/bin/sh", "-c",cmd};  
                Process process = Runtime.getRuntime().exec(cmdA);  
                LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream()));  
                StringBuffer sb = new StringBuffer();  
                String line;  
             while ((line = br.readLine()) != null) {  
                    System.out.println(line);  
                    sb.append(line).append("\n");  
                }  
                return sb.toString();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            return null;  
        }  
        private static void makeShell() throws IOException{
            File file=new File("tmp.sh");
            if(!file.exists())
                file.createNewFile();
            file.delete();
            FileOutputStream out=new FileOutputStream(file,true);        
            
            StringBuffer sb=new StringBuffer();
            sb.append("#!/bin/bash\n. /Users/lanxuan/torch/install/bin/torch-activate\n");
            sb.append("th fast_neural_style.lua -model models/instance_norm/mosaic.t7 -input_image " + imageName + " -output_image out.jpg");
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        }
        public static void main(String[] args){
        	try {
				makeShell();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  	
        }
        public String execute(String[] args) {  
        	try {
				makeShell();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            String pwdString5 = exec("chmod +x ./tmp.sh").toString(); 
            String pwdString = exec("./tmp.sh").toString(); 
            System.out.println(pwdString);  
            System.out.println(pwdString5);
			return "SUCCESS";
        }
		public static String getImageName() {
			return imageName;
		}
		public static void setImageName(String imageName) {
			ExecLinuxCMD.imageName = imageName;
		}

       
    } 