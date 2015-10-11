/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssm.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import static ssm.StartupConstants.PATH_SLIDE_SHOWS;
import static ssm.StartupConstants.PATH_SLIDE_SHOW_IMAGES;
import static ssm.file.SlideShowFileManager.JSON_CAPTION;
import static ssm.file.SlideShowFileManager.JSON_EXT;
import static ssm.file.SlideShowFileManager.JSON_IMAGE_FILE_NAME;
import static ssm.file.SlideShowFileManager.JSON_IMAGE_PATH;
import static ssm.file.SlideShowFileManager.JSON_SLIDES;
import static ssm.file.SlideShowFileManager.JSON_TITLE;
import static ssm.file.SlideShowFileManager.SLASH;
import ssm.model.Slide;
import ssm.model.SlideShowModel;
import ssm.view.SlideShowMakerView;

/**
 *
 * @author Suraj Sharma
 */
public class WebsiteGeneration {

    // THE MAIN UI
    SlideShowMakerView ui;

    // THE DATA FOR THIS SLIDE SHOW
    SlideShowModel slideshow;

    public static String SLIDESHOW_TITLE = "slideshow";
    
    
    public WebsiteGeneration(SlideShowMakerView parentView) throws IOException {

        ui = parentView;
        slideshow = ui.getSlideShow();
        
       String path = "sites/"+ slideshow.getTitle();
        Files.createDirectories(Paths.get(path + "/img"));
        Files.createDirectories(Paths.get(path + "/css"));
        Files.createDirectories(Paths.get(path + "/js"));

        saveSlideShow(slideshow);
                
        
        // Generating a javascript file  
       
         try{   
            
            InputStream inStream_js = null;
            OutputStream outStream_js = null;
    	    File original_javascript =new File("./Slideshow.js");
    	    File new_javascript =new File(Paths.get(path) + "/js/Slideshow.js");
    		
    	    inStream_js = new FileInputStream(original_javascript);
    	    outStream_js = new FileOutputStream(new_javascript);
        	
    	    byte[] buffer = new byte[1024];
    		
    	    int length;
    	    //copy the file content in bytes 
    	    while ((length = inStream_js.read(buffer)) > 0){
    	  
    	    	outStream_js.write(buffer, 0, length);
    	 
    	    }
    	 
    	    inStream_js.close();
    	    outStream_js.close();
    	    
  
    	    
    	    System.out.println("File is copied successful!");
    	    
    	}catch(IOException e){
    	    e.printStackTrace();
        } 
        

        
        // For Copying the images
        
        InputStream inStream = null;
	OutputStream outStream = null;
    	
            for(int i =0 ; i< slideshow.getSlides().size();i++){
              
             try{   
            
            
    	    File afile =new File(slideshow.getSlides().get(i).getImagePath()+
                    slideshow.getSlides().get(i).getImageFileName());
    	    File bfile =new File(Paths.get(path) + "/img/"+
                   slideshow.getSlides().get(i).getImageFileName());
    		
    	    inStream = new FileInputStream(afile);
    	    outStream = new FileOutputStream(bfile);
        	
    	    byte[] buffer = new byte[1024];
    		
    	    int length;
    	    //copy the file content in bytes 
    	    while ((length = inStream.read(buffer)) > 0){
    	  
    	    	outStream.write(buffer, 0, length);
    	 
    	    }
    	 
    	    inStream.close();
    	    outStream.close();
    	    
  
    	    
    	    System.out.println("File is copied successful!");
    	    
    	}catch(IOException e){
    	    e.printStackTrace();
        }      
        }
            
         
            
            
          // For Creating a blank html page
           
            try{   
            
            InputStream inStream_html = null;
            OutputStream outStream_html = null;
    	    File original_html =new File("./index.html");
    	    File new_html =new File(Paths.get(path) + "/index.html");
    		
    	    inStream_html = new FileInputStream(original_html);
    	    outStream_html= new FileOutputStream(new_html);
        	
    	    byte[] buffer = new byte[1024];
    		
    	    int length;
    	    //copy the file content in bytes 
    	    while ((length = inStream_html.read(buffer)) > 0){
    	  
    	    	outStream_html.write(buffer, 0, length);
    	 
    	    }
    	 
    	    inStream_html.close();
    	    outStream_html.close();
    	   
    	    System.out.println("File is copied successful!");
    	    
    	}catch(IOException e){
    	    e.printStackTrace();
        } 
            
            
         // For creating a new CSS file
            
              try{   
            
            InputStream inStream_css = null;
            OutputStream outStream_css = null;
    	    File original_css =new File("./slideshow_style.css");
    	    File new_css =new File(Paths.get(path) + "/css/slideshow_style.css");
    		
    	    inStream_css = new FileInputStream(original_css);
    	    outStream_css= new FileOutputStream(new_css);
        	
    	    byte[] buffer = new byte[1024];
    		
    	    int length;
    	    //copy the file content in bytes 
    	    while ((length = inStream_css.read(buffer)) > 0){
    	  
    	    	outStream_css.write(buffer, 0, length);
    	 
    	    }
    	 
    	    inStream_css.close();
    	    outStream_css.close();
    	   
    	    System.out.println("File is copied successful!");
    	    
    	}catch(IOException e){
    	    e.printStackTrace();
        } 
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
       
        
    }
    
    public void saveSlideShow(SlideShowModel slideShowToSave) throws IOException {
	StringWriter sw = new StringWriter();

	// BUILD THE SLIDES ARRAY
	JsonArray slidesJsonArray = makeSlidesJsonArray(slideShowToSave.getSlides());

	// NOW BUILD THE COURSE USING EVERYTHING WE'VE ALREADY MADE
	JsonObject slideShowJsonObject = Json.createObjectBuilder()
		.add(JSON_SLIDES, slidesJsonArray)
		.build();

	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);

	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(slideShowJsonObject);
	jsonWriter.close();

	// INIT THE WRITER
	String jsonFilePath = "sites/"+ slideshow.getTitle() +"/js/" + SLIDESHOW_TITLE + JSON_EXT;
	OutputStream os = new FileOutputStream(jsonFilePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(slideShowJsonObject);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(jsonFilePath);
	pw.write(prettyPrinted);
	pw.close();
	System.out.println(prettyPrinted);
    }
    
    private JsonArray makeSlidesJsonArray(List<Slide> slides) {
	JsonArrayBuilder jsb = Json.createArrayBuilder();
	for (Slide slide : slides) {
	    JsonObject jso = makeSlideJsonObject(slide);
	    jsb.add(jso);
	}
	JsonArray jA = jsb.build();
	return jA;
    }
    
    private JsonObject makeSlideJsonObject(Slide slide) {
	JsonObject jso = Json.createObjectBuilder()
		.add(JSON_IMAGE_FILE_NAME, slide.getImageFileName())
		.add(JSON_IMAGE_PATH,"sites/"+ slideshow.getTitle() + "/img/"+
                   slide.getImageFileName())
		.add(JSON_CAPTION, slide.getCaption())
		.build();
	return jso;
    }

}
