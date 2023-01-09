package src;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.Serializable;
public class Webpage implements Serializable, SearchResult {
    private String title;
    private int intTotal;
    private double pageRank;
    private double score;
    private HashMap<String,Integer> wordCount = new HashMap<String,Integer>();
    private HashMap<String,Double> wordTF = new HashMap<String,Double>();
    private HashMap<String,Double> wordTFIDF = new HashMap<String,Double>();
    public Webpage(String title){ this.title = title;}

    /*
    Returns the title of the page this search result is for.
    */
    public String getTitle() {
        return title;
    }
    /*
    Returns the search score for the page this search result is for.
     */
    public double getScore() {
        return score;
    }
    public void setScore(double score){this.score = score;}

    public int getTotal(){return intTotal;}
    public void setTotal(int total){intTotal=total;}
    public void setPageRank(double pageRank){this.pageRank=pageRank;}
    public double getPageRank(){ return pageRank;}

    public int getWordCount(String strWord){return wordCount.get(strWord);}
    public HashMap<String,Integer> getWordCountHashMap(){return wordCount;}
    public double getWordTF(String strWord){return wordTF.get(strWord);}
    public HashMap<String,Double> getWordTFHashMap(){return wordTF;}
    public double getWordTFIDF(String strWord) {
        if (wordTFIDF.get(strWord) == null) {
            return 0;
        }
        return wordTFIDF.get(strWord);
    }
    public HashMap<String,Double> getWordTFIDFHashMap(){return wordTFIDF;}
    public void addWordCount(String strWord,int intCount){wordCount.put(strWord,intCount);}
    public void addWordTF(String strWord,double dblTF){wordTF.put(strWord,dblTF);}
    public void addWordTFIDF(String strWord, double dblTFIDF){wordTFIDF.put(strWord,dblTFIDF);}
}
