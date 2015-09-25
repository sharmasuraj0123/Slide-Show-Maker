package ssm.view;

import java.util.Optional;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import ssm.LanguagePropertyType;
import static ssm.LanguagePropertyType.DEFAULT_SLIDE_SHOW_TITLE;
import static ssm.LanguagePropertyType.TOOLTIP_ADD_SLIDE;
import static ssm.LanguagePropertyType.TOOLTIP_EXIT;
import static ssm.LanguagePropertyType.TOOLTIP_LOAD_SLIDE_SHOW;
import static ssm.LanguagePropertyType.TOOLTIP_MOVE_DOWN;
import static ssm.LanguagePropertyType.TOOLTIP_MOVE_UP;
import static ssm.LanguagePropertyType.TOOLTIP_NEW_SLIDE_SHOW;
import static ssm.LanguagePropertyType.TOOLTIP_NEXT_SLIDE;
import static ssm.LanguagePropertyType.TOOLTIP_PREVIOUS_SLIDE;
import static ssm.LanguagePropertyType.TOOLTIP_REMOVE_SLIDE;
import static ssm.LanguagePropertyType.TOOLTIP_SAVE_SLIDE_SHOW;
import static ssm.LanguagePropertyType.TOOLTIP_VIEW_SLIDE_SHOW;
import ssm.StartupConstants;
import static ssm.StartupConstants.CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON;
import static ssm.StartupConstants.CSS_CLASS_SLIDE_SHOW_EDIT_VBOX;
import static ssm.StartupConstants.CSS_CLASS_VERTICAL_TOOLBAR_BUTTON;
import static ssm.StartupConstants.DEFAULT_THUMBNAIL_WIDTH;
import static ssm.StartupConstants.ICON_ADD_SLIDE;
import static ssm.StartupConstants.ICON_EXIT;
import static ssm.StartupConstants.ICON_LOAD_SLIDE_SHOW;
import static ssm.StartupConstants.ICON_MOVE_DOWN;
import static ssm.StartupConstants.ICON_MOVE_UP;
import static ssm.StartupConstants.ICON_NEW_SLIDE_SHOW;
import static ssm.StartupConstants.ICON_NEXT;
import static ssm.StartupConstants.ICON_PREVIOUS;
import static ssm.StartupConstants.ICON_REMOVE_SLIDE;
import static ssm.StartupConstants.ICON_SAVE_SLIDE_SHOW;
import static ssm.StartupConstants.ICON_VIEW_SLIDE_SHOW;
import static ssm.StartupConstants.PATH_ICONS;
import static ssm.StartupConstants.STYLE_SHEET_UI;
import ssm.controller.FileController;
import ssm.controller.SlideShowEditController;
import ssm.model.Slide;
import ssm.model.SlideShowModel;
import ssm.error.ErrorHandler;
import ssm.file.SlideShowFileManager;

/**
 * This class provides the User Interface for this application,
 * providing controls and the entry points for creating, loading, 
 * saving, editing, and viewing slide shows.
 * 
 * @author McKilla Gorilla & Suraj Sharma
 */
public class SlideShowMakerView {

    // THIS IS THE MAIN APPLICATION UI WINDOW AND ITS SCENE GRAPH
    Stage primaryStage;
    Scene primaryScene;
    
   HBox hb = new HBox();

    // THIS PANE ORGANIZES THE BIG PICTURE CONTAINERS FOR THE
    // APPLICATION GUI
    BorderPane ssmPane;

    // THIS IS THE TOP TOOLBAR AND ITS CONTROLS
    FlowPane fileToolbarPane;
    Button newSlideShowButton;
    Button loadSlideShowButton;
    Button saveSlideShowButton;
    Button viewSlideShowButton;
    Button exitButton;
    Button nextSlideButton;
    Button previousSlideButton;
    
    // WORKSPACE
    HBox workspace;
    
    //Used for SlideShow viewing
    FlowPane actionButtons;
    HBox Image;
    BorderPane ViewPane;
    
    // THIS WILL GO IN THE LEFT SIDE OF THE SCREEN
    VBox slideEditToolbar;
    Button addSlideButton;
    Button removeSlideButton;
    Button moveUpButton;
    Button moveDownButton;
    
    // AND THIS WILL GO IN THE CENTER
    ScrollPane slidesEditorScrollPane;
    VBox slidesEditorPane;

     public static TextField textField;
            
    // THIS IS THE SLIDE SHOW WE'RE WORKING WITH
    SlideShowModel slideShow;

    // THIS IS FOR SAVING AND LOADING SLIDE SHOWS
    SlideShowFileManager fileManager;

    // THIS CLASS WILL HANDLE ALL ERRORS FOR THIS PROGRAM
    private ErrorHandler errorHandler;

    // THIS CONTROLLER WILL ROUTE THE PROPER RESPONSES
    // ASSOCIATED WITH THE FILE TOOLBAR
    private FileController fileController;
    
    // THIS CONTROLLER RESPONDS TO SLIDE SHOW EDIT BUTTONS
    private SlideShowEditController editController;

    /**
     * Default constructor, it initializes the GUI for use, but does not yet
     * load all the language-dependent controls, that needs to be done via the
     * startUI method after the user has selected a language.
     */
    public SlideShowMakerView(SlideShowFileManager initFileManager) {
	// FIRST HOLD ONTO THE FILE MANAGER
	fileManager = initFileManager;
	
	// MAKE THE DATA MANAGING MODEL
	slideShow = new SlideShowModel(this);

	// WE'LL USE THIS ERROR HANDLER WHEN SOMETHING GOES WRONG
	errorHandler = new ErrorHandler(this);
    }

    // ACCESSOR METHODS
    public SlideShowModel getSlideShow() {
	return slideShow;
    }

    public Stage getWindow() {
	return primaryStage;
    }

    public ErrorHandler getErrorHandler() {
	return errorHandler;
    }

    /**
     * Initializes the UI controls and gets it rolling.
     * 
     * @param initPrimaryStage The window for this application.
     * 
     * @param windowTitle The title for this window.
     */
    public void startUI(Stage initPrimaryStage, String windowTitle) {
        
         
        
	
// THE TOOLBAR ALONG THE NORTH
	initFileToolbar();

        // INIT THE CENTER WORKSPACE CONTROLS BUT DON'T ADD THEM
	// TO THE WINDOW YET
	initWorkspace();

	// NOW SETUP THE EVENT HANDLERS
	initEventHandlers();

	// AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
	// KEEP THE WINDOW FOR LATER
        primaryStage = initPrimaryStage;
        primaryStage.getIcons().add(new Image("file:./images/icons/view.png"));
        initWindow(windowTitle);
	
    }

    // UI SETUP HELPER METHODS
    private void initWorkspace() {
	// FIRST THE WORKSPACE ITSELF, WHICH WILL CONTAIN TWO REGIONS
	workspace = new HBox();
	
	// THIS WILL GO IN THE LEFT SIDE OF THE SCREEN
	slideEditToolbar = new VBox();
	slideEditToolbar.getStyleClass().add(CSS_CLASS_SLIDE_SHOW_EDIT_VBOX);
	addSlideButton = this.initChildButton(slideEditToolbar,		ICON_ADD_SLIDE,	    TOOLTIP_ADD_SLIDE,	    CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  true);
        removeSlideButton = this.initChildButton(slideEditToolbar, ICON_REMOVE_SLIDE, TOOLTIP_REMOVE_SLIDE, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON, true);
	moveUpButton = this.initChildButton(slideEditToolbar, ICON_MOVE_UP, TOOLTIP_MOVE_UP, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON, true);
        moveDownButton = this.initChildButton(slideEditToolbar, ICON_MOVE_DOWN, TOOLTIP_MOVE_DOWN, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON, true);
        
	// AND THIS WILL GO IN THE CENTER
	slidesEditorPane = new VBox();
	slidesEditorScrollPane = new ScrollPane(slidesEditorPane);
	
	// NOW PUT THESE TWO IN THE WORKSPACE
	workspace.getChildren().add(slideEditToolbar);
	workspace.getChildren().add(slidesEditorScrollPane); 
        workspace.getChildren().add(hb);
    }

    private void initEventHandlers() {
	// FIRST THE FILE CONTROLS
	fileController = new FileController(this, fileManager);
	newSlideShowButton.setOnAction(e -> {
	    fileController.handleNewSlideShowRequest();
	});
	loadSlideShowButton.setOnAction(e -> {
	    fileController.handleLoadSlideShowRequest();
	});
	saveSlideShowButton.setOnAction(e -> {
	    fileController.handleSaveSlideShowRequest();
	});
	exitButton.setOnAction(e -> {
	    fileController.handleExitRequest();
	});
        
        viewSlideShowButton.setOnAction(e -> {
	    fileController.handleSlideShowViewRequest();
	});
	
	// THEN THE SLIDE SHOW EDIT CONTROLS
	editController = new SlideShowEditController(this);
	addSlideButton.setOnAction(e -> {
	    editController.processAddSlideRequest();
	});
        removeSlideButton.setOnAction(e -> {
	    editController.processRemoveSlideRequest();
	});
        moveUpButton.setOnAction(e -> {
	    editController.processMoveUpSlideRequest();
	});
        moveDownButton.setOnAction(e -> {
	    editController.processMoveDownSlideRequest();
	});
        
    }

    public void viewingActions(){
        
        previousSlideButton.setOnAction(e -> {
	    displaySlide(slideShow.getPrev());
	});
         nextSlideButton.setOnAction(e -> {
	    displaySlide(slideShow.getNext());
	});
        
    }
    
    
    /**
     * This function initializes all the buttons in the toolbar at the top of
     * the application window. These are related to file management.
     */
    private void initFileToolbar() {
	fileToolbarPane = new FlowPane();

        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
	// START AS ENABLED (false), WHILE OTHERS DISABLED (true)
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	newSlideShowButton = initChildButton(fileToolbarPane, ICON_NEW_SLIDE_SHOW,	TOOLTIP_NEW_SLIDE_SHOW,	    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
	loadSlideShowButton = initChildButton(fileToolbarPane, ICON_LOAD_SLIDE_SHOW,	TOOLTIP_LOAD_SLIDE_SHOW,    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
	saveSlideShowButton = initChildButton(fileToolbarPane, ICON_SAVE_SLIDE_SHOW,	TOOLTIP_SAVE_SLIDE_SHOW,    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, true);
	viewSlideShowButton = initChildButton(fileToolbarPane, ICON_VIEW_SLIDE_SHOW,	TOOLTIP_VIEW_SLIDE_SHOW,    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, true);
	exitButton = initChildButton(fileToolbarPane, ICON_EXIT, TOOLTIP_EXIT, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
    }
    
    public void viewButtons(){
        actionButtons = new FlowPane();
        
        previousSlideButton = initChildButton(actionButtons, ICON_PREVIOUS,TOOLTIP_PREVIOUS_SLIDE,    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        nextSlideButton = initChildButton(actionButtons, ICON_NEXT,TOOLTIP_NEXT_SLIDE,    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);  
        
        viewingActions();
    }

    private void initWindow(String windowTitle) {
	// SET THE WINDOW TITLE
	primaryStage.setTitle(windowTitle);

	// GET THE SIZE OF THE SCREEN
	Screen screen = Screen.getPrimary();
	Rectangle2D bounds = screen.getVisualBounds();

	// AND USE IT TO SIZE THE WINDOW
	primaryStage.setX(bounds.getMinX());
	primaryStage.setY(bounds.getMinY());
	primaryStage.setWidth(bounds.getWidth());
	primaryStage.setHeight(bounds.getHeight());
      

        // SETUP THE UI, NOTE WE'LL ADD THE WORKSPACE LATER
	ssmPane = new BorderPane();
	ssmPane.setTop(fileToolbarPane);	
	primaryScene = new Scene(ssmPane);
        
          Label label1 = new Label("Name:");
        textField = new TextField ();
        
       //if(slideShow.getTitle()!= null)
            textField.setText(slideShow.getTitle() + "");
            
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            slideShow.setTitle(newValue);
        });
        
        hb.getChildren().addAll(label1, textField);
        hb.setSpacing(10);
	
        // NOW TIE THE SCENE TO THE WINDOW, SELECT THE STYLESHEET
	// WE'LL USE TO STYLIZE OUR GUI CONTROLS, AND OPEN THE WINDOW
	primaryScene.getStylesheets().add(STYLE_SHEET_UI);
	primaryStage.setScene(primaryScene);
	primaryStage.show();
    }
    
    public void slideShowView(){
        
        Stage slideShowView = new Stage();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

	// AND USE IT TO SIZE THE WINDOW
	slideShowView.setX(1000);
	slideShowView.setY(1000);
	slideShowView.setWidth(1000);
	slideShowView.setHeight(500);
        slideShowView.setTitle(slideShow.getTitle());
        
          
            
        
        ViewPane = new BorderPane(); 
        viewButtons();
        displaySlide(slideShow.getSelectedSlide());
        
        
       
       
         slideShowView.setScene(new Scene(ViewPane));
        slideShowView.show();
    }
    
    public void displaySlide(Slide display){
        ViewPane.getChildren().clear();
        
        Image = new HBox();
       
        double scaledWidth = 1000;
         SlideEditView SlideToDisplay = new SlideEditView (display , this);
            
            double perc = scaledWidth / 1000;
	    double scaledHeight = 500 * perc;
	    SlideToDisplay.imageSelectionView.setFitWidth(scaledWidth);
	    SlideToDisplay.imageSelectionView.setFitHeight(scaledHeight);
            
        Image.getChildren().add(SlideToDisplay); 
        ViewPane.setCenter(Image);
       ViewPane.setTop(actionButtons);
        
    }
    
    /**
     * This helps initialize buttons in a toolbar, constructing a custom button
     * with a customly provided icon and tooltip, adding it to the provided
     * toolbar pane, and then returning it.
     */
    public Button initChildButton(
	    Pane toolbar, 
	    String iconFileName, 
	    LanguagePropertyType tooltip, 
	    String cssClass,
	    boolean disabled) {
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	String imagePath = "file:" + PATH_ICONS + iconFileName;
	Image buttonImage = new Image(imagePath);
	Button button = new Button();
	button.getStyleClass().add(cssClass);
	button.setDisable(disabled);
	button.setGraphic(new ImageView(buttonImage));
	Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip.toString()));
	button.setTooltip(buttonTooltip);
	toolbar.getChildren().add(button);
	return button;
    }
    
    /**
     * Updates the enabled/disabled status of all toolbar
     * buttons.
     * 
     * @param saved 
     */
    public void updateToolbarControls(boolean saved) {
	// FIRST MAKE SURE THE WORKSPACE IS THERE
	ssmPane.setCenter(workspace);
	// NEXT ENABLE/DISABLE BUTTONS AS NEEDED IN THE FILE TOOLBAR
	saveSlideShowButton.setDisable(saved);
	viewSlideShowButton.setDisable(false);
	
	// AND THE SLIDESHOW EDIT TOOLBAR
	addSlideButton.setDisable(false);
        removeSlideButton.setDisable(false);
        moveUpButton.setDisable(false);
        moveDownButton.setDisable(false);
    }

    /**
     * Uses the slide show data to reload all the components for
     * slide editing.
     * 
     * @param slideShowToLoad SLide show being reloaded.
     */
    public void reloadSlideShowPane(SlideShowModel slideShowToLoad) {
	slidesEditorPane.getChildren().clear();
        int index =0;
        
        if(slideShowToLoad.getSelectedSlide()==null){
             removeSlideButton.setDisable(true);
        moveUpButton.setDisable(true);
        moveDownButton.setDisable(true);
        viewSlideShowButton.setDisable(true);
        }
        else{
            removeSlideButton.setDisable(false);
        moveUpButton.setDisable(false);
        moveDownButton.setDisable(false);
        viewSlideShowButton.setDisable(false);
        }
                
        
	for (Slide slide : slideShowToLoad.getSlides()) {
	    SlideEditView slideEditor = new SlideEditView(slide, this);
	    slidesEditorPane.getChildren().add(slideEditor);
            if(slideShowToLoad.getSlides().get(index) ==slideShowToLoad.getSelectedSlide()){
                SepiaTone deselect = new SepiaTone();
                        deselect.setLevel(0.7);
               slideEditor.setEffect(deselect);
	}
            index++;
        }
 }
    
    
    
    
    
    
    
    
    
    
    
}
