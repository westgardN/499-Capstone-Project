package edu.metrostate.ics499.prim.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class AzureService {
	URL url;
	HttpURLConnection con;
	
	public AzureService() {
		
	}
	
	/**
	 * Sends data to Azure to be processed.
	 * 
	 * @param The URL Address the HTTP request should be sent to.
	 * @param The String to be sent.
	 */
	/**
	 * @param urlAddress
	 * @param toSend
	 */
	public void sendData(String urlAddress, String key, String toSend){
        DataOutputStream out = null;

        try {
			url = new URL(urlAddress);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			
			con.setRequestProperty("Ocp-Apim-Subscription-Key", key);
			
			con.setDoOutput(true);
		    con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			out = new DataOutputStream(con.getOutputStream());

			//out.writeUTF(toSend);
            byte[] encoded_text = toSend.getBytes(Charset.forName("UTF-8"));
			out.write(encoded_text, 0, encoded_text.length);
			out.flush();
			out.close();

		} catch (IOException e) {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                }
            }
			if (con != null) {
			    con.disconnect();
            }
		}
		
	}

	/**
	 * Retrieves the processed data from azure.
	 * 
	 * @return A String of the Processed data in JSON format.
	 */
	public String retrieveData(){
		String returnString = null;
        BufferedReader in = null;

        if (con == null) {
            return "";
        }

        try {
			in = new BufferedReader(
					  new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
				
			returnString = content.toString();
			in.close();
			con.disconnect();
		} catch (IOException e) {
			if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if (con != null) {
			    con.disconnect();
            }
		}
		
		return returnString;
	}
}
