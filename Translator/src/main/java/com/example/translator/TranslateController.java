
package com.example.translator;


import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javafx.event.ActionEvent;


public class TranslateController implements Initializable {

    @FXML
    private TextArea typeText;
    @FXML
    private Button translateBtn;
    @FXML
    private TextArea outputText;
    @FXML
    private ComboBox<String> comboBox01;
    @FXML
    private ComboBox<String> comboBox02;
    
       // List of languages (Name - Code)
    private final List<String> languages = Arrays.asList(
        "English - en", "French - fr", "Spanish - es", "German - de", "Italian - it", "Chinese - zh", 
        "Japanese - ja", "Korean - ko", "Russian - ru", "Hindi - hi", "Arabic - ar","Sinhala - si"
    );

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        comboBox01.getItems().addAll(languages);
        comboBox02.getItems().addAll(languages);
        
        // Set default values
        comboBox01.setValue("English - en"); 
        comboBox02.setValue("French - fr"); 
        // TODO
    }    

    @FXML
    private void translateClick(MouseEvent event) {
        
      try {
          
          String text = typeText.getText(); // Get text from input field

            // Get selected language codes
          String fromLang = comboBox01.getValue().split(" - ")[1]; // Extract language code
          String toLang = comboBox02.getValue().split(" - ")[1];   // Extract language code
          String langPair = fromLang + "|" + toLang;
            
            if (fromLang == null || toLang == null) {
            outputText.setText("Error: Invalid language selection.");
            return;
        }

            // ✅ Encode text and language pair
            String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);
            String encodedLangPair = URLEncoder.encode(langPair, StandardCharsets.UTF_8);

            // ✅ Correct API URL with encoded parameters
            String apiUrl = "https://api.mymemory.translated.net/get?q=" + encodedText + "&langpair=" + encodedLangPair;

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // ✅ Check if the request was successful
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(conn.getInputStream());
                String response = scanner.useDelimiter("\\A").next();
                scanner.close();

//                // ✅ Parse JSON response
//                JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
//                String translatedText = jsonResponse.getAsJsonObject("responseData").get("translatedText").getAsString();

                // ✅ Print only translated text
                System.out.println("Translated Text: " + response);
                 outputText.setText("Error: "+response);
            } else {
                System.out.println("Error: HTTP " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void comboBox1Action(ActionEvent event) {
    }

    @FXML
    private void comboBox2Action(ActionEvent event) {
    }
    
}
