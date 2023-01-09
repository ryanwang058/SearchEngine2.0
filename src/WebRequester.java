package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class WebRequester {
    public static String readURL(String url) throws MalformedURLException, IOException {
        URL page = new URL(url);
        StringBuilder response = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(page.openStream()));
        String currentLine = reader.readLine();
        while(currentLine != null){
            response.append(currentLine + "\n");
            currentLine = reader.readLine();
        }
        return response.toString();
    }

    /*
    Below is an example of how you can read a URL with this class.
    If exceptions are thrown during your crawl, you should handle them gracefully.
    For example, add the URL to the queue again and retry later.
     */
    public static void main(String[] args){
        String allHTML = "";
        try {
            allHTML = (WebRequester.readURL("https://oirp.carleton.ca/databook/2021/courses/tables/table-CE1-1_hpt.htm"));
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        String[] lines = allHTML.strip().split("\n");
        for(int i=0;i<lines.length;i++){
            System.out.println(lines[i]);
            if(lines[i].endsWith("<p>")) {
                System.out.println("ayo?");
            }else if(lines[i].startsWith("</p>")){
                System.out.println("ameno");
            }
        }
    }
}
