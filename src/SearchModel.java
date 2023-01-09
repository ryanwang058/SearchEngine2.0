package src;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SearchModel implements ProjectTester{
    CrawlInfo crawlInfo;
    private String[] finalResult;

    public SearchModel() {
        crawlInfo = new CrawlInfo();
        initialize();
    }

    public String[] getFinalResult() {
        return finalResult;
    }

    public void initialize() {
        File file = new File("crawlInfo.dat");
        if (file.exists()) {
            //load binary info into CrawlInfo object
            this.crawlInfo = CrawlTools.readCrawlInfo();
        }
    }

    public void crawl(String seedURL) {
        //make a crawler
        Crawl crawler = new Crawl();
        //make crawler do a crawl
        crawler.doCrawl(seedURL);
        //update searchMode's info
        initialize();
    }

    public LinkedList<String> getOutgoingLinks(String url) {
        if (crawlInfo!=null) {
            return crawlInfo.getOutgoingLinks(url);
        }
        return null;
    }

    public LinkedList<String> getIncomingLinks(String url) {
        // read CrawlInfo Object to get incoming links
        if (crawlInfo!=null) {
            return crawlInfo.getOutgoingLinks(url);
        }
        return null;
    }

    public double getPageRank(String url) {
        // read CrawlInfo Object to get page rank
        if (crawlInfo == null || crawlInfo.getWebpage(url) == null) {
            return -1;
        }
        return crawlInfo.getWebpage(url).getPageRank();
    }

    public double getIDF(String word) {
        if (crawlInfo == null || crawlInfo.getIDFValues() == null) {
            return 0;
        }
        return crawlInfo.getIDFValue(word);
    }

    public double getTF(String url, String word) {
        if (crawlInfo == null || crawlInfo.getWebpage(url) == null || crawlInfo.getWebpage(url).getWordTFHashMap() == null || !crawlInfo.getWebpage(url).getWordTFHashMap().containsKey(word)) {
            return 0;
        }
        return crawlInfo.getWebpage(url).getWordTF(word);
    }

    public double getTFIDF(String url, String word) {
        if (crawlInfo == null || crawlInfo.getWebpage(url) == null || crawlInfo.getWebpage(url).getWordTFIDFHashMap() == null || !crawlInfo.getWebpage(url).getWordTFIDFHashMap().containsKey(word)) {
            return 0;
        }
        return crawlInfo.getWebpage(url).getWordTFIDF(word);
    }

    public List<SearchResult> search(String query, boolean boost, int X){
        // add webpages
        LinkedList<Webpage> webpages = new LinkedList<Webpage>(crawlInfo.getWebpages().values());

        // turn query into words and obtain query info
        String[] searchWords = query.trim().split(" ");
        HashMap<String, double[]> queryInfo = SearchTools.calculateQryInfo(crawlInfo, searchWords);

        // calculate similarity score and sort the list
        webpages = SearchTools.sortPages(SearchTools.calculateSimilarityScore(crawlInfo, queryInfo, webpages, boost));

        // return top X values
        LinkedList<SearchResult> searchResultList = new LinkedList<SearchResult>();
        finalResult = new String[X];
        for (int i=0; i<X; i++) {
            searchResultList.add(webpages.get(i));
            finalResult[i]= webpages.get(i).getTitle() + " with search score of [" + webpages.get(i).getScore() + "]";
        }
        return searchResultList;
    }

}