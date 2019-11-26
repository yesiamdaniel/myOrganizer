package network;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Weather {
    private String description;
    private String location;
    private int currentTemp;

    public Weather(String location) {
        if (isCityValid(location)) {
            getWeather(location);
        }
    }

    // Some element taken form edx edge sample
    // REQUIRES: internet connection
    // MODIFIES: this
    // EFFECTS: retrives weather from openweathermap api
    public void getWeather(String location)  {
        String theURL = getOpenWeatherURL(location);

        BufferedReader br = getBufferedReader(theURL);
        JSONObject json;

        try {
            json = getDataJson(br);
            description = getWeatherDescription(json);
            this.location = getWeatherLocation(json);
            currentTemp = getWeatherTemp(json);
        } catch (IOException e) {
            System.out.println("ERROR: Json retrieval failed");
        }
    }

    private String getOpenWeatherURL(String city) {
        String appId = "5e7ec0901f192965d4c9d842588e855a";
        return "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + appId
                + "&units=metric";
    }

    private JSONObject getDataJson(BufferedReader br) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
        }

        JSONObject json = null;
        try {
            json = (JSONObject) new JSONParser().parse(sb.toString());
            br.close();
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("ERROR parsing JSON data");
            System.exit(-1);
        }
        return json;
    }

    private BufferedReader getBufferedReader(String theURL) {
        BufferedReader br = null;
        // From edx samples
        try {
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("invalid URL format");
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Weather ERROR");
        }
        return br;
    }

    private String getWeatherDescription(JSONObject json) {
        JSONArray weatherArray = (JSONArray) json.get("weather");
        JSONObject weatherObject = (JSONObject) weatherArray.get(0);
        return  (String) weatherObject.get("description");
    }

    // EFFECTS: returns a formatted string of the weather description
    public String getWeatherDescription() {
        return description;
    }

    private String getWeatherLocation(JSONObject json) {
        return (String) json.get("name");
    }

    // EFFECTS: returns the location in which the weather is being observed
    public String getWeatherLocation() {
        return location;
    }

    private int getWeatherTemp(JSONObject json) {
        JSONObject main = (JSONObject) json.get("main");
        Double temp = (Double) main.get("temp");
        return (int) Math.round(temp);
    }

    // EFFECTS: returns current temp
    public int getCurrentTemp() {
        return currentTemp;
    }

    // EFFECTS: creates a full weather report
    public String createWeatherReport() {
        return "In " + location + ", it is currently " + currentTemp + "°C with " + description;
    }

    // EFFECTS: checks to see if city is valid
    public boolean isCityValid(String city) {
        try {
            String theURL = getOpenWeatherURL(city);
            URL url = new URL(theURL);
            new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
