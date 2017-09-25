package Classes;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Hendra on 07/05/2017.
 */
public class ServiceHandler {
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    HttpURLConnection urlConnection;

    public ServiceHandler(){

    }

    public String makeServiceCall(String url, int method){
        return this.makeServiceCall(url,method,null);
    }

    public String makeServiceCall(String url, int method, List<NameValuePair> params){
        StringBuilder result = new StringBuilder();

        try {
            URL urlObj = new URL(url);
            urlConnection = (HttpURLConnection) urlObj.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null){
                result.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }

        return result.toString();

    }

    private class NameValuePair {
    }
}
