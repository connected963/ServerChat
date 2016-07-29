/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

/**
 *
 * @author root
 */
public class IoHelper {
    
    public static boolean isImage(String fileName){
        boolean isImg = false;
        String[] splitName = fileName.split(".");
        String fileExtension = splitName[splitName.length -1];
 
        if(fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("jpg")
           || fileExtension.equalsIgnoreCase("jpeg") || fileExtension.equalsIgnoreCase("gif")
           || fileExtension.equalsIgnoreCase("raw")){
            isImg = true;
        }
        
        return isImg;
    }
    
}
