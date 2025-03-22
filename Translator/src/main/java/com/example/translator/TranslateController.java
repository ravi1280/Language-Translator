
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


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
    
    private final List<String> languages = Arrays.asList(
            "English - en", "French - fr", "Spanish - es", "German - de", "Italian - it", "Chinese - zh",
            "Japanese - ja", "Korean - ko", "Russian - ru", "Hindi - hi", "Arabic - ar", "Sinhala - si"
    );

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        comboBox01.getItems().addAll(languages);
        comboBox02.getItems().addAll(languages);

        comboBox01.setValue("English - en");
        comboBox02.setValue("French - fr");

    }

    @FXML
    private void translateClick(MouseEvent event) {

        try {

            String text = typeText.getText();

            String fromLang = comboBox01.getValue().split(" - ")[1];
            String toLang = comboBox02.getValue().split(" - ")[1];
            String langPair = fromLang + "|" + toLang;

            if (fromLang == null || toLang == null) {
                outputText.setText("Error: Invalid language selection.");
                return;
            }

            String encodedText = URLEncoder.encode(text, "UTF-8");
            String encodedLangPair = URLEncoder.encode(langPair, StandardCharsets.UTF_8);

            String apiUrl = "https://api.mymemory.translated.net/get?q=" + encodedText + "&langpair=" + encodedLangPair;

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(conn.getInputStream());
                String response = scanner.useDelimiter("\\A").next();
                scanner.close();

                System.out.println("Translated Text: " + response);

                JsonObject jsonResponse = JsonParser.parseString(new String(response.getBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
                String translatedText = jsonResponse.getAsJsonObject("responseData").get("translatedText").getAsString();

                outputText.setText(translatedText);

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
