
package testing001;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Testing001 {

    public static void main(String[] args) {
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

                // ✅ Parse JSON response
                JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
                String translatedText = jsonResponse.getAsJsonObject("responseData").get("translatedText").getAsString();

                // ✅ Print only translated text
                System.out.println("Translated Text: " + translatedText);
            } else {
                System.out.println("Error: HTTP " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
