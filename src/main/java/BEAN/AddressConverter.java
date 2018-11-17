package BEAN;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 *
 * @author Abhishek Somani
 *
 */
public class AddressConverter {

    private static final String URL = "http://maps.googleapis.com/maps/api/js";

    public GoogleResponse convertFromLatLong(String latlongString) throws IOException {
        URL url = new URL(URL + "?latlng="
                + URLEncoder.encode(latlongString, "UTF-8") + "&sensor=false");
        // Open the Connection
        URLConnection conn = url.openConnection();
        InputStream in = conn.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        GoogleResponse response = (GoogleResponse) mapper.readValue(in, GoogleResponse.class);
         System.out.println(response.getResults());
        in.close();
        return response;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("\n");
        GoogleResponse res1 = new AddressConverter().convertFromLatLong("-1.26479450,36.76346340");
       
        if (res1.getStatus().equals("OK")) {
            for (Result result : res1.getResults()) {
                System.out.println("address is :" + result.getFormatted_address());
            }
        } else {
            System.out.println(res1.getStatus());
        }
    }
}

class Result {

    private String formatted_address;
    private boolean partial_match;

    @JsonIgnore
    private Object address_components;
    @JsonIgnore
    private Object types;

    public String getFormatted_address() {
        return formatted_address;

    }

}

class GoogleResponse {

    private Result[] results;
    private String status;

    public Result[] getResults() {
        return results;
    }

    public void setResults(Result[] results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
