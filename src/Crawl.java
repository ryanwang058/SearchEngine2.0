package src;

import java.io.File;
import java.util.LinkedList;

public class Crawl {

    public Crawl() {

    }

    public void doCrawl(String seedURL) {
        //do a big crawl and get all data
        CrawlInfo crawlInfo = new CrawlInfo();

        crawlInfo.getLstQueue().add(seedURL);

        //while there's still something in the queue
        while(crawlInfo.getLstQueue().size()>0) {
            //each time we go through one link
            String strSubPage = crawlInfo.getLstQueue().get(0);
            //If the links found in page aren't in the queue or in the pages visited, add it to the list

            //add all the outgoing links to a dictionary
            CrawlTools.recordOutgoingLinks(crawlInfo,strSubPage);

            for(String url : crawlInfo.getOutgoingLinks(strSubPage)) {
                //if the incoming link doesn't exist
                if(!crawlInfo.getIncomingLinksHashMap().containsKey(url)){
                    crawlInfo.getIncomingLinksHashMap().put(url,new LinkedList<String>());
                }
                crawlInfo.getIncomingLinks(url).add(strSubPage);
            }
            //since we've gone on that page, let's add it to the pages we've visited
            crawlInfo.getLstPages().add(strSubPage);

            //this function records all the info we need in a webpage;
            CrawlTools.recordWebpageInformation(crawlInfo, strSubPage);
            //we then get rid of the website we've just visited which is at lstQueue[0]
            crawlInfo.getLstQueue().pop();
            //for every link inside the page we're on,
            for(String strLink : crawlInfo.getOutgoingLinks(strSubPage)) {
                //if it's not in the queue already and not a key in dicOutgoingLinks visited, then
                if (!crawlInfo.getOutgoingLinksHashMap().containsKey(strLink) && !crawlInfo.getLstQueue().contains(strLink)) {
                    //add that page to the queue
                    crawlInfo.getLstQueue().add(strLink);
                }
            }

        }
        crawlInfo.setTotalWebpages(crawlInfo.getLstPages().size());
        //Now that we have the pages we've been to, we can do the easy bit of recording IDF values
        CrawlTools.recordIDF(crawlInfo);

        for(String page : crawlInfo.getLstPages()) {
            CrawlTools.recordTFIDF(crawlInfo,crawlInfo.getWebpage(page));
        }

        // record page rank
        CrawlTools.recordPageRank(crawlInfo);

        // save crawl info and return whether successful
        CrawlTools.saveCrawlInfo(crawlInfo);
    }
}
