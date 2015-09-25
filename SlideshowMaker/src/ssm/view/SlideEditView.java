package ssm.view;

import java.io.File;
import java.net.URL;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import ssm.LanguagePropertyType;
import static ssm.StartupConstants.CSS_CLASS_SLIDE_EDIT_VIEW;
import static ssm.StartupConstants.DEFAULT_THUMBNAIL_WIDTH;
import ssm.controller.ImageSelectionController;
import ssm.model.Slide;
import static ssm.file.SlideShowFileManager.SLASH;
import ssm.model.SlideShowModel;

/**
 * This UI component has the controls for editing a single slide
 * in a slide show, including controls for selected the slide image
 * and changing its caption.
 * 
 * @author McKilla Gorilla & Suraj Sharma
 */
public class SlideEditView extends HBox {
    
     // An instance variable to take care of previously selected class
    public static int unselect=0;
    
    
    // SLIDE THIS COMPONENT EDITS
    Slide slide;
    
    // DISPLAYS THE IMAGE FOR THIS SLIDE
    ImageView imageSelectionView;
    
    // CONTROLS FOR EDITING THE CAPTION
    VBox captionVBox;
    Label captionLabel;
    TextField captionTextField;
    
    // PROVIDES RESPONSES FOR IMAGE SELECTION
    ImageSelectionController imageController;
    SlideShowModel selection ;

    //Updating the UI
    SlideShowMakerView ui;

    /**
     * THis constructor initializes the full UI for this component, using
     * the initSlide data for initializing values./
     * 
     * @param initSlide The slide to be edited by this component.
     */
    public SlideEditView(Slide initSlide , SlideShowMakerView initUi) {
	// FIRST SELECT THE CSS STYLE CLASS FOR THIS CONTAINER
	this.getStyleClass().add(CSS_CLASS_SLIDE_EDIT_VIEW);
	 ui = initUi;
        
         
         
       
	// KEEP THE SLIDE FOR LATER
	slide = initSlide;
	
	// MAKE SURE WE ARE DISPLAYING THE PROPER IMAGE
	imageSelectionView = new ImageView();
	updateSlideImage();

	// SETUP THE CAPTION CONTROLS
	captionVBox = new VBox();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	captionLabel = new Label(props.getProperty(LanguagePropertyType.LABEL_CAPTION));
	captionTextField = new TextField();
        if(!slide.getCaption().equals(null)) 
              captionTextField.setText(slide.getCaption());
        captionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                slide.setCaption(newValue);
});
       
	captionVBox.getChildren().add(captionLabel);
	captionVBox.getChildren().add(captionTextField);
        
	// LAY EVERYTHING OUT INSIDE THIS COMPONENT
	getChildren().add(imageSelectionView);
	getChildren().add(captionVBox);

	// SETUP THE EVENT HANDLERS
	imageController = new ImageSelectionController();
	imageSelectionView.setOnMousePressed(e -> {
             //selection.setSelectedSlide(initSlide);
	    imageController.processSelectImage(slide, this);
            slide.setImageFileName(slide.getImageFileName());
            slide.setImagePath(slide.getImagePath());
            slide.setCaption(slide.getCaption());
            
	});
     
        this.setOnMouseClicked( e->{
            

             if(initSlide.equals(ui.getSlideShow().getSelectedSlide()))
                 ui.getSlideShow().setSelectedSlide(null);    
            else    
           ui.getSlideShow().setSelectedSlide(initSlide);
             
        ui.reloadSlideShowPane(ui.getSlideShow());
               
    });
        
    }

    public SlideShowMakerView getUi() {
        return ui;
    }

    public void setUi(SlideShowMakerView ui) {
        this.ui = ui;
    }
    
    /**
     * This function gets the image for the slide and uses it to
     * update the image displayed.
     */
    public void updateSlideImage() {
	String imagePath = slide.getImagePath() + SLASH + slide.getImageFileName();
	File file = new File(imagePath);
	try {
	    // GET AND SET THE IMAGE
	    URL fileURL = file.toURI().toURL();
	    Image slideImage = new Image(fileURL.toExternalForm());
	    imageSelectionView.setImage(slideImage);
	    
	    // AND RESIZE IT
	    double scaledWidth = DEFAULT_THUMBNAIL_WIDTH;
	    double perc = scaledWidth / slideImage.getWidth();
	    double scaledHeight = slideImage.getHeight() * perc;
	    imageSelectionView.setFitWidth(scaledWidth);
	    imageSelectionView.setFitHeight(scaledHeight);
	} catch (Exception e) {
	    // @todo - use Error handler to respond to missing image
	}
    }    
}