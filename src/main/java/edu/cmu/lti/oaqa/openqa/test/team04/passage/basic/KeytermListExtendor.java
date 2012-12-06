package edu.cmu.lti.oaqa.openqa.test.team04.passage.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cmu.lti.oaqa.framework.data.Keyterm;

public class KeytermListExtendor {
  
  public List<Keyterm> KeytermListExtendor (List<Keyterm> keyterms) throws IOException  {
    
    List<Keyterm> extendedKeyterms = new ArrayList<Keyterm>();
    List<String> extendedKeytermsString = new ArrayList<String>();
    
    for (Keyterm keyterm : keyterms) {
      extendedKeytermsString.add(keyterm.getText());
      
      // check synonyms of keyterm online and return at most top 3
      
      /*String keytermName = keyterm.getText();
      String requestURL = "http://words.bighugelabs.com/api/2/8b07b554c6ac13b40d590164d388395c/"
      + keytermName +"/";
      
      URL url = null;
      url = new URL(requestURL);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
      
      
      int responeCode = connection.getResponseCode();
      if (responeCode == 404) continue;
      
      InputStream in = connection.getInputStream();
      BufferedReader bin = new BufferedReader(new InputStreamReader(in, "GB2312"));
      
      int count = 0;
      String line = bin.readLine();
      while (count < 1 || line != null) {
        Pattern myPattern = Pattern.compile(".*\\|.*\\|(.*)");
        Matcher matcher = myPattern.matcher(line);
        if (matcher.find()) {
          Keyterm synonyms = new Keyterm (matcher.group(1));
          extendedKeyterms.add(synonyms);
        }
        count++;
        line = bin.readLine();
      }
    }*/
      
      URL url = new URL("http://gpsdb.expasy.org/cgi-bin/gpsdb/show");
      URLConnection conn = url.openConnection();
      conn.setDoOutput(true);
      OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

      // write parameters
      writer.write("name=" + keyterm + "&species=&taxo=0&source=HGNC&type=gene");
      writer.flush();
      
   // Get the response
      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      StringBuffer htmlFile = new StringBuffer();
      String line;

      while ((line = reader.readLine()) != null) {
            htmlFile.append(line);
      }
      
      // Output the response
      String curStr = htmlFile.toString();
      int start = 0;
      int end = 0;
      int countExtendedWords = 0;
      while (start != -1 && end != -1 && countExtendedWords<3) {
        start = curStr.indexOf("<td class=\"name\">", end + 3) + 17;
        if (start == 16)
          break;
        end = curStr.indexOf("</td>", start);
        if (!extendedKeytermsString.contains(curStr.substring(start, end)))
          {
            extendedKeytermsString.add(curStr.substring(start, end));
            countExtendedWords++;
          }
       } 
    }
    for (String keytermString : extendedKeytermsString ) {
      Keyterm newKeyterm = new Keyterm (keytermString);
      extendedKeyterms.add(newKeyterm);
    }
    return extendedKeyterms;
  }
}