package jsonParse;

import org.checkerframework.common.reflection.qual.GetMethod;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class JsonOnlineParser {


    private URL link;
    private final String url = "https://dummyjson.com/products";
    private HttpURLConnection connection;
    private BufferedReader reader;
    private JSONObject json;
    private final StringBuilder builder = new StringBuilder();




    {
        try {

            toJson();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void toJson () throws IOException {

        link = new URL(url);
        connection = (HttpURLConnection) link.openConnection();
        reader = new BufferedReader( new InputStreamReader(connection.getInputStream()));
        json = new JSONObject(reader.readLine());


    }
    public JSONObject getJson () {

        return json;
    }



}

