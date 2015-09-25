package ssm.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.effect.SepiaTone;
import properties_manager.PropertiesManager;
import ssm.LanguagePropertyType;
import ssm.view.SlideShowMakerView;

/**
 * This class manages all the data associated with a slideshow.
 * 
 * @author McKilla Gorilla & Suraj Sharma
 */
public class SlideShowModel {
    SlideShowMakerView ui;
    String title;
    ObservableList<Slide> slides;
    Slide selectedSlide;
    
    public SlideShowModel(SlideShowMakerView initUI) {
	ui = initUI;
	slides = FXCollections.observableArrayList();
	reset();	
    }

    // ACCESSOR METHODS
    public boolean isSlideSelected() {
	return selectedSlide != null;
    }
    
    public ObservableList<Slide> getSlides() {
	return slides;
    }
    
    public Slide getSelectedSlide() {
	return selectedSlide;
    }

    public String getTitle() { 
	return title; 
    }
    
    // MUTATOR METHODS
    public void setSelectedSlide(Slide initSelectedSlide) {
	selectedSlide = initSelectedSlide;
    }
    
    public void setTitle(String initTitle) { 
	title = initTitle; 
    }
    
    public Slide getNext(){
        if(slides.indexOf(selectedSlide) != slides.size()-1)
        selectedSlide= slides.get(slides.indexOf(selectedSlide)+1);
        return selectedSlide;
    }
    
    public Slide getPrev(){
        if(slides.indexOf(selectedSlide) !=0)
        selectedSlide = slides.get(slides.indexOf(selectedSlide)-1);
        return selectedSlide;
    }

    // SERVICE METHODS
    
    /**
     * Resets the slide show to have no slides and a default title.
     */
    public void reset() {
	slides.clear();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	title = props.getProperty(LanguagePropertyType.DEFAULT_SLIDE_SHOW_TITLE);
	selectedSlide = null;
    }

    /**
     * Adds a slide to the slide show with the parameter settings.
     * @param initImageFileName File name of the slide image to add.
     * @param initImagePath File path for the slide image to add.
     */
    public void addSlide(   String initImageFileName,
			    String initImagePath , String initCaption) {
	Slide slideToAdd = new Slide(initImageFileName, initImagePath , initCaption);
	slides.add(slideToAdd);
	selectedSlide = slideToAdd;
	ui.reloadSlideShowPane(this);
    }
    
    public void removeSlide( Slide selectedSlide) {
	slides.remove(selectedSlide);
	ui.reloadSlideShowPane(this);
    }
    
    public void moveUp(Slide selectedSlide){
        if(selectedSlide ==slides.get(0))
        return;
        
        
        int index = slides.indexOf(selectedSlide);
       Slide temp =  slides.remove(index);
       slides.add(--index,temp);
        
        ui.reloadSlideShowPane(this);
    }
    
    public void moveDown(Slide selectedSlide){
        
        if(selectedSlide ==slides.get(slides.size()-1))
        return;
        
        int index = slides.indexOf(selectedSlide);
       Slide temp =  slides.remove(index);
       slides.add(++index,temp);
        
        ui.reloadSlideShowPane(this);
    }
    
    
    
}