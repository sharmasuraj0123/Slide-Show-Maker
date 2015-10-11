package ssm.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static ssm.StartupConstants.CSS_CLASS_COMBOBOX;
import static ssm.StartupConstants.CSS_CLASS_VBOX;
import static ssm.StartupConstants.ENGLISH_LANG;
import static ssm.StartupConstants.FINNISH_LANG;
import static ssm.StartupConstants.LABEL_LANGUAGE_SELECTION_PROMPT;
import static ssm.StartupConstants.OK_BUTTON_TEXT;
import static ssm.StartupConstants.STYLE_SHEET_UI;

/**
 *
 * @author McKillaGorilla
 */
public class LanguageSelectionDialog extends Stage {
    VBox vBox;
    Label languagePromptLabel;
    ComboBox languageComboBox;
    Button okButton;
    String selectedLanguage = ENGLISH_LANG;
    
    public LanguageSelectionDialog() {
	languagePromptLabel = new Label(LABEL_LANGUAGE_SELECTION_PROMPT);
	
	// INIT THE LANGUAGE CHOICES
	ObservableList<String> languageChoices = FXCollections.observableArrayList();
	languageChoices.add(ENGLISH_LANG);
	languageChoices.add(FINNISH_LANG);
	languageComboBox = new ComboBox(languageChoices);
        languageComboBox.getStyleClass().add(CSS_CLASS_COMBOBOX);
        
	languageComboBox.getSelectionModel().select(ENGLISH_LANG);
	okButton = new Button(OK_BUTTON_TEXT);
	
	vBox = new VBox();
        vBox.getStyleClass().add(CSS_CLASS_VBOX);
	vBox.getChildren().add(languagePromptLabel);
	vBox.getChildren().add(languageComboBox);
	vBox.getChildren().add(okButton);
	
	okButton.setOnAction(e -> {
	    selectedLanguage = languageComboBox.getSelectionModel().getSelectedItem().toString();
	    this.hide();
	});
	
	// NOW SET THE SCENE IN THIS WINDOW
	Scene scene = new Scene(vBox);
        scene.getStylesheets().add(STYLE_SHEET_UI);
	setScene(scene);
    }
    
    public String getSelectedLanguage() {
	return selectedLanguage;
    }
}
