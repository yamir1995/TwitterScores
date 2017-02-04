import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.json.*;

/**
 * Class twitter
 * 
 * This class serves to retrieve tweets from the Twitter API
 * 
 * @author Rohan
 *
 */

public class twitter {
    private static String bearerToken;
    public static void main(String[] args) {
        try {
            bearerToken = requestBearerToken();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    
    private static String encodeKeys(String consumerKey, String consumerSecret) {
        try {
            String encodedConsumerKey = URLEncoder.encode(consumerKey, "UTF-8");
            String encodedConsumerSecret = URLEncoder.encode(consumerSecret, "UTF-8");
            String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
            byte[] encodedBytes = Base64.encodeBase64(fullKey.getBytes());
            return new String(encodedBytes);
        } catch (UnsupportedEncodingException e) {
            return new String();
        } catch (Exception e) {
            return null;
        }
    }

    private static String requestBearerToken() throws IOException {
        String endPointUrl = "https://api.twitter.com/oauth2/token";
        HttpsURLConnection connection = null;
        String encodedCredentials = encodeKeys("MtBMZh5BkB0jU99MEOwEYzHhB", "IenckXrT5LydifjNsPOvGnL6Bog2dUqxdrtFrpA2nJY0TCVMtc");
        try {
            URL url = new URL(endPointUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Host", "api.twitter.com");
            connection.setRequestProperty("User-Agent", "Your Program Name");
            connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            connection.setRequestProperty("Content-Length", "29");
            connection.setUseCaches(false);
            writeRequest(connection, "grant_type=client_credentials");
            // Parse the JSON response into a JSON mapped object to fetch fields
            // from.
            JSONObject obj =  new JSONObject(readResponse(connection));
            if (obj != null) {
                String tokenType = (String) obj.get("token_type");
                String token = (String) obj.get("access_token");
                return ((tokenType.equals("bearer")) && (token != null)) ? token : "";
            }
        } catch (MalformedURLException e) {
            throw new IOException("Invalid endpoint URL specified.", e);
        } catch (JSONException e) {
        	e.printStackTrace();
        	 return new String();
		} finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
		return bearerToken;
    }
    
    /**
     * This method takes in the url to connect to and retrieves tweets from it. It accumulates each
     * set of tweets from a country into a single string an returns it.
     * @param endPointUrl
     * @return
     * @throws IOException
     */
    public static String fetchTimelineTweet(String endPointUrl) throws IOException {
            HttpsURLConnection connection = null;
            try {
                URL url = new URL(endPointUrl); 
                connection = (HttpsURLConnection) url.openConnection();           
                connection.setDoOutput(true);
                connection.setDoInput(true); 
                connection.setRequestMethod("GET"); 
                connection.setRequestProperty("Host", "api.twitter.com");
                connection.setRequestProperty("User-Agent", "Your Program Name");
                connection.setRequestProperty("Authorization", "Bearer " + bearerToken);

                connection.setUseCaches(false);
                // Parse the JSON response into a JSON mapped object to fetch fields from.
                
               JSONObject obj = new JSONObject(readResponse(connection));
               {
                    JSONArray statuses = obj.getJSONArray("statuses");
                    String accumulator = "";
                    for (int i = 0; i < statuses.length(); i++)
                    {
                        JSONObject o = statuses.getJSONObject(i);
                        String text = o.getString("text");
                        accumulator = accumulator + text.trim();
                    }
                    return accumulator;
               }
            }
            catch (MalformedURLException e) {
                throw new IOException("Invalid endpoint URL specified.", e);
            } catch (JSONException e) {
				
				return new String();
			}
            finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }


    // Writes a request to a connection
    private static boolean writeRequest(HttpsURLConnection connection, String textBody) {
        try {
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
                    connection.getOutputStream()));
            wr.write(textBody);
            wr.flush();
            wr.close();
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    // Reads a response for a given connection and returns it as a string.
    private static String readResponse(HttpsURLConnection connection) {
        try {
            StringBuilder str = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                str.append(line + System.getProperty("line.separator"));
            }
            return str.toString();
        } catch (IOException e) {
            return new String();
        }
    }
    
    

}
