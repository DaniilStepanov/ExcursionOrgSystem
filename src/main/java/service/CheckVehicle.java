package service;

import businesslogic.userfactory.Vehicle;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 * Created by Danya on 30.05.2017.
 */
public class CheckVehicle {

    public boolean check(Vehicle v) throws Exception{
        String url = "http://xn--80adjurebidw.xn--p1ai/check.php";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("VIN", ""));
        urlParameters.add(new BasicNameValuePair("NOMER_KUZOVA", ""));
        urlParameters.add(new BasicNameValuePair("NOMER_RAMY", ""));
        urlParameters.add(new BasicNameValuePair("REG_ZNAK", URLEncoder.encode(v.getNumbers(), "UTF-8")));
        urlParameters.add(new BasicNameValuePair("DK_NOMER", ""));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println("Line = " + line);
            if(line.contains("error")){
                return false;
            }
        }
        return true;
    }
}
