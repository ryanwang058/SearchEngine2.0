package src;
import java.io.*;
import java.util.*;

public class CrawlTools {
    //this function will write down the info into a file
    //O(n) time due to functions called that are O(n)
    public static void recordWebpageInformation(CrawlInfo crawlInfo, String strSubPage) {
        //add url to our webpage
        crawlInfo.addWebpage(strSubPage);

        //create an array that will store the lines of html
        String[] lstLines = {};
        try {
            //break down the lines
            lstLines = WebRequester.readURL(strSubPage).strip().split("\n");
        } catch (IOException e) {
            System.out.println("error reading webpage");
        }

        //we'll read the whole thing
        countWord(crawlInfo.getWebpage(strSubPage),lstLines);

        //update big hashmap
        recordWordCount(crawlInfo,crawlInfo.getWebpage(strSubPage));

        recordTotalWordCount(crawlInfo.getWebpage(strSubPage));

        //store the term frequency
        recordTF(crawlInfo.getWebpage(strSubPage));
    }

    //This generally updates a dictionary with the number of words inside
    //O(n^2) time
    public static void countWord(Webpage webpage, String[] lstLines) {
        boolean blnParse = false;
        //for every line in the html page
        for (String strLine : lstLines) {
            //If it starts with "<p>", then we know it has paragraphs
            if (strLine.strip().endsWith("<p>") || strLine.strip().startsWith("</p>")) {
                blnParse = !(blnParse);
            }
            //if it's time to parse the paragraph, then...
            if (blnParse && !strLine.strip().endsWith("<p>")) {
                //if it's not in the dictionary, make it 1
                if (!webpage.getWordCountHashMap().containsKey(strLine)) {
                    webpage.addWordCount(strLine,0);
                }
                //then add 1
                webpage.getWordCountHashMap().replace(strLine,webpage.getWordCount(strLine)+1);
            }
        }
    }
    public static void recordWordCount(CrawlInfo crawlInfo,Webpage webpage) {
        //for every key value(word) inside the dicWords dictionary
        for(String strWord : webpage.getWordCountHashMap().keySet()){
            //if the word isn't in the big hashmap already, add it to the hashmap
            if (!crawlInfo.getAllWordsHashMap().containsKey(strWord)) {
                crawlInfo.getAllWordsHashMap().put(strWord,1);
            } else {
                crawlInfo.getAllWordsHashMap().replace(strWord,crawlInfo.getAllWordsHashMap().get(strWord)+1);
            }
        }
    }

    //This function creates a file for every word term frequency
    //O(n) time
    public static void recordTF(Webpage webpage) {
        //find the total
        int intTotal = webpage.getTotal();
        //for every word in dicWords
        for (String strWord : webpage.getWordCountHashMap().keySet()) {
            webpage.addWordTF(strWord,webpage.getWordCount(strWord) / (double)intTotal);
        }
    }

    public static void recordTotalWordCount(Webpage webpage){
        //initialize the total
        int intTotal = 0;

        //for every word in dicWords
        for (int value : webpage.getWordCountHashMap().values()) {
            //add the number of times a word appears into intTotal
            intTotal += value;
        }
        //then update
        webpage.setTotal(intTotal);
    }

    //O(n) time
    public static void recordIDF(CrawlInfo crawlInfo) {
        for (String strWord : crawlInfo.getAllWordsHashMap().keySet()) {
            crawlInfo.getIDFValues().put(strWord,(Math.log((double)(crawlInfo.getTotalWebpages()) / (1 + crawlInfo.getAllWordsHashMap().get(strWord))) / Math.log(2)));
        }
    }

    public static void recordTFIDF(CrawlInfo crawlInfo,Webpage webpage) {
        // Write tfidf to TFIDF hashmap
        for (String strWord : webpage.getWordCountHashMap().keySet()) {
            webpage.addWordTFIDF(strWord,Math.log(1 + webpage.getWordTF(strWord)) / Math.log(2) * crawlInfo.getIDFValue(strWord));
        }
    }

    public static void recordOutgoingLinks(CrawlInfo crawlInfo,String url) {
        String[] lstLines = {};
        try {
            //break down the lines
            lstLines = WebRequester.readURL(url).strip().split("\n");
        } catch (IOException e) {
            System.out.println("error reading webpage");
        }

        // 2 variables to keep track of the beginning and end of a link
        String strLink="";
        int intStart = 0;
        int intEnd = 0;

        String strBeginning = url.substring(0, url.lastIndexOf("/") + 1);
        LinkedList<String> lstLinks = new LinkedList<String>();
        crawlInfo.getOutgoingLinksHashMap().put(url,new LinkedList<String>());
        //we'll read the whole thing
        for (String strLine : lstLines) {
            //If it starts with "<a href", then we know it has a link to another pages
            if (strLine.startsWith("<a href")) {
                //find the index of the first "
                intStart = strLine.indexOf('"');
                //find the index of the last "
                intEnd = strLine.lastIndexOf('"');
                //everything in between the " " will be the link
                strLink = strLine.substring(intStart + 1, intEnd);

                //If it's a relative link(it starts with "./", we'll add the absolute start onto the name of it
                //Instead of "./N-0.html
                if (strLink.startsWith("./")) {
                    //It will be https://scs.carleton.N-0.html now I think
                    strLink = strBeginning + strLink.substring(2, strLink.length());
                }
                //if the hashmap for this doesn't exist already make it
                crawlInfo.getOutgoingLinks(url).add(strLink);
            }
        }
    }

    public static void recordPageRank(CrawlInfo crawlInfo) {
        if (crawlInfo == null) {
            return;
        }

        HashMap<String, LinkedList<String>> outgoingLinks = crawlInfo.getOutgoingLinksHashMap();

        double alpha = 0.1;
        double euclideanDistThreshold = 0.0001;

        HashMap<Integer,String> dict = new HashMap<Integer, String>();
        HashMap<String, Integer> reversedDict = new HashMap<String, Integer>();

        // fill dict and reversedDict
        int count = 0;
        for (String page: crawlInfo.getLstPages()) {
            dict.put(count, page);
            reversedDict.put(page, count);
            count++;
        }

        // create adjacency matrix
        double[] row = new double[dict.size()];
        double[][] adjacencyMatrix = new double[dict.size()][dict.size()];

        for (int id: dict.keySet()) {
            for (String page: reversedDict.keySet()) {
                if (outgoingLinks.get(dict.get(id)).contains(page)) {
                    row[reversedDict.get(page)] = 1;
                } else {
                    row[reversedDict.get(page)] = 0;
                }
            }
            adjacencyMatrix[id] = row;
            row = new double[dict.size()];
        }

        // scaled adjacency matrix after adding alpha/N to each entry
        count = 0;
        for (int i=0; i<adjacencyMatrix.length; i++) {
            for (int j=0; j<adjacencyMatrix[0].length; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    count++;
                }
            }
            if (count == 0) {
               for (int j=0; j<adjacencyMatrix[0].length; j++) {
                   adjacencyMatrix[i][j] = (1.0 / adjacencyMatrix.length) * (1 - alpha) + (alpha / adjacencyMatrix.length);
               }
            } else {
                for (int j=0; j<adjacencyMatrix[0].length; j++) {
                    adjacencyMatrix[i][j] = (adjacencyMatrix[i][j] / count) * (1 - alpha) + (alpha / adjacencyMatrix.length);
                }
            }
            count = 0;
        }

        // power iteration with adjacency matrix
        // initialize iterating vector
        double[][] iteratingVector = new double[1][adjacencyMatrix.length];
        for (int i = 0; i < adjacencyMatrix[0].length; i++) {
            iteratingVector[0][i] = 1.0 / adjacencyMatrix[0].length;
        }

        // use modules of MathTools to calculate
        double[][] finalPageRankVector = src.MathTools.multMatrix(iteratingVector, adjacencyMatrix);
        double dist = src.MathTools.euclideanDist(finalPageRankVector, iteratingVector);

        // iterate until dist < euclideanDistThreshold when we find the stable final page rank vector
        while (dist >= euclideanDistThreshold) {
            iteratingVector = finalPageRankVector;
            finalPageRankVector = src.MathTools.multMatrix(iteratingVector, adjacencyMatrix);
            dist = src.MathTools.euclideanDist(finalPageRankVector, iteratingVector);
        }

        // save pagerank data to the corresponding webpage object
        for (String page: reversedDict.keySet()) {
            crawlInfo.getWebpage(page).setPageRank(finalPageRankVector[0][reversedDict.get(page)]);
        }

    }

    public static void saveCrawlInfo(CrawlInfo crawlInfo) {
        try {
            FileOutputStream fileOut = new FileOutputStream("crawlInfo.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(crawlInfo);
            out.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Cannot open file for writing");
        } catch (IOException e) {
            System.out.println("Error: Cannot write to file");
        }
    }

    public static CrawlInfo readCrawlInfo() {
        try {
            FileInputStream fileIn = new FileInputStream("crawlInfo.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            CrawlInfo crawlInfo = (CrawlInfo) in.readObject();
            in.close();
            fileIn.close();
            return crawlInfo;
        } catch (IOException e) {
            System.out.println("Error: Unable to read crawl info");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: CrawlInfo is null");
        }
        return null;
    }
}
