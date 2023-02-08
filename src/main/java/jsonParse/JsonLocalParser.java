package jsonParse;

import org.json.JSONObject;

import java.io.*;

public class JsonLocalParser {

    File archivo = new File("src/main/java/jsonParse/products.json");
    FileReader reader;
    BufferedReader bufer;

    {
        try {
            reader =  new FileReader(archivo);
            bufer = new BufferedReader(reader);

            JSONObject json = new JSONObject(bufer.readLine());

            for (int i = 0; i < json.getJSONArray("products").length() ; i++) {
                System.out.println(json.getJSONArray("products").getJSONObject(i).get("title"));

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
