
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
import java.util.Scanner;


public class TranslateController implements Initializable {

    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextArea typeText;
    @FXML
    private Button translateBtn;
    @FXML
    private TextArea outputText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void translateClick(MouseEvent event) {
        
      try {
            String text = "Hello, how are you?"; // Text to translate
            String langPair = "en|fr"; // English to French

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
            } else {
                System.out.println("Error: HTTP " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
