/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssm.view;

import com.sun.javaws.Main;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import static ssm.StartupConstants.PATH_CSS;
import ssm.model.SlideShowModel;

/**
 *
 * @author Sushi
 */
public class SlideShowWebView extends Stage{
       // THE MAIN UI

    SlideShowMakerView parentView;

    // THE DATA FOR THIS SLIDE SHOW
    SlideShowModel slides; 
     Scene scene; 
        
        
    public SlideShowWebView(SlideShowMakerView initParentView) {
	// KEEP THIS FOR LATER
	parentView = initParentView;

	// GET THE SLIDES
	slides = parentView.getSlideShow();
    }
    
    
  public void startWebPage(){
      setTitle (slides.getTitle());
      scene = new Scene(new Browser(),750,500, Color.web("#666970"));
        setScene(scene);
        scene.getStylesheets().add(PATH_CSS+"BrowserToolbar.css");
        this.showAndWait();
  }  
    
 
    
}

class Browser extends Region {
 
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
     
    public Browser() {
        //apply the styles
        getStyleClass().add("browser");
        
        String path = System.getProperty("user.dir");  
            path.replace("\\\\", "/");  
            path +=  "website.html";  
            webEngine.load("file:///" + path);  
        
        // load the web page
      //  webEngine.load(url);
        //add the web view to the scene
        getChildren().add(browser);
 
    }
    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
 
    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }
 
    @Override protected double computePrefWidth(double height) {
        return 750;
    }
 
    @Override protected double computePrefHeight(double width) {
        return 500;
    }
}
