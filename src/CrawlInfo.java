package src;

import java.util.HashMap;
import java.util.LinkedList;
import java.io.Serializable;
public class CrawlInfo implements Serializable{
    private int intTotalWebpages = 0;
    private LinkedList<String> lstPages = new LinkedList<String>();
    private LinkedList<String> lstQueue = new LinkedList<String>();
    private HashMap<String,Integer> allWordsHashMap = new HashMap<String,Integer>();
    private HashMap<String,Webpage> webpages = new HashMap<String, Webpage>();
    private HashMap<String,Double> idfValues = new HashMap<String,Double>();
    private HashMap<String,LinkedList<String>> incomingLinksHashMap = new HashMap<String, LinkedList<String>>();
    private HashMap<String,LinkedList<String>> outgoingLinksHashMap = new HashMap<String, LinkedList<String>>();

    public CrawlInfo(){}
    public HashMap<String,LinkedList<String>> getIncomingLinksHashMap(){return incomingLinksHashMap;}
    public LinkedList<String> getIncomingLinks(String url){return incomingLinksHashMap.get(url);}
    public HashMap<String,LinkedList<String>> getOutgoingLinksHashMap(){return outgoingLinksHashMap;}
    public LinkedList<String> getOutgoingLinks(String url){return outgoingLinksHashMap.get(url);}
    public HashMap<String,Webpage> getWebpages(){return webpages;}
    public int getTotalWebpages(){return intTotalWebpages;}
    public void setTotalWebpages(int total){intTotalWebpages=total;}
    public LinkedList<String> getLstPages() { return lstPages;}
    public HashMap<String,Double> getIDFValues(){return idfValues;}
    public LinkedList<String> getLstQueue(){return lstQueue;}
    public Webpage getWebpage(String URL){return webpages.get(URL);}
    public HashMap<String,Integer> getAllWordsHashMap(){return allWordsHashMap;}
    public int getAllWord(String word){return allWordsHashMap.get(word);}
    public double getIDFValue(String strWord) {
        if (idfValues.get(strWord) != null) {
            return idfValues.get(strWord);
        }
        return 0;
    }

    public void addWebpage(String URL){webpages.put(URL,new Webpage(URL.substring(URL.lastIndexOf("/") + 1, URL.length() - 5)));}
}