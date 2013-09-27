package uk.co.kyleharrison.jobseeker.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.kyleharrison.jobseeker.interfaces.ConnectorInterface;
import uk.co.kyleharrison.jobseeker.interfaces.JobInterface;

public class URLRequestJSONUtil {

	private StreamReaderJSONUtil JSR;
	private JSONObject json = null;
	private JSONArray JSArray = null;
	private ConnectorInterface ConInf;
	private JobInterface JobInf;

	public URLRequestJSONUtil() {
		this.JSR = new StreamReaderJSONUtil();
		this.JSArray = new JSONArray();
	}
	
	public URLRequestJSONUtil(ConnectorInterface _ConInf, JobInterface _JobInf) {
		this.JSR = new StreamReaderJSONUtil();
		this.JSArray = new JSONArray();
		this.ConInf = _ConInf;
		this.JobInf = _JobInf;
	}

	public JSONObject Search(String URL) {
		try {
			try{
				System.out.println("ConInf.getTargetURL()"+this.ConInf.getTargetURL());
			}catch(NullPointerException e){
				e.getStackTrace();
			}

			this.json = JSR.readJsonFromUrl(URL);
			this.JSArray = this.json.getJSONArray("results");

			System.out.println(this.JSArray.get(0).toString());
			System.out.println(this.json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this.json;
	}
	
	public JSONObject SearchYaga(String URL) {
		try {
			try{
				System.out.println("ConInf.getTargetURL()"+this.ConInf.getTargetURL());
			}catch(NullPointerException e){
				e.getStackTrace();
			}

			try{
				JSONArray json2 = new JSONArray(JSR.readJsonFromUrl(URL));
				System.out.println(json2.toString());
			}catch(Exception e){
				e.printStackTrace();
			}

			System.out.println(this.JSArray.get(0).toString());
			System.out.println(this.json.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this.json;
	}
	
	public JSONArray getJSONArrayFromUrl(String url) {
        InputStream is = null;
        JSONArray jObj = null;
        String json = "";
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                //json += line;
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            //Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONArray(json);
        } catch (JSONException e) {
           // Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return jObj;
    }
   
	public void mapToPojo() {
		try {
			JobInf = new ObjectMapper().readValue(JSArray.get(1).toString(),
					JobInf.getClass());
			System.out.println(JobInf.getJobTitle());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public JSONArray getJSArray() {
		return JSArray;
	}

}
