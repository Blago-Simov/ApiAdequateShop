package api;



import helpers.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;


import java.io.IOException;
import java.io.InputStream;
public class GetRequests {
    private static String urlString = "http://restapi.adequateshop.com/api/Users/";

    private static String responseCode;
    private static String responseBody;
    private static  String authMessage;

    public static void main(String[] args) {
    }

        public static  void getUser(String endpoint,String accessToken) throws IOException {
            HttpGet getUsers = new HttpGet(urlString+endpoint);
            getUsers.setHeader("Authorization","Bearer" + accessToken);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse response = httpClient.execute(getUsers);
            responseCode = response.getStatusLine().toString();

            //Parse the response body
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                responseBody = new ResponseReader().convertStreamToString(instream);
                instream.close();
            }
            if (responseCode.contains("200") == true) {
                JsonParser json = new JsonParser();
                authMessage = json.getAuthMessage(responseBody);


            }

        }

      public static String getResponseCode () {
        return responseCode;
      }
       public static String getAuthMessage () {
        return authMessage;
      }


    }
