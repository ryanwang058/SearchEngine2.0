package src;

import java.util.HashMap;
import java.util.LinkedList;

public class SearchTools {
    public static HashMap<String, double[]> calculateQryInfo(CrawlInfo crawlInfo, String[] words) {
        // mapQry is used to store query words info
        //       word : [count, tf-idf of this word in the query]
        // e.g., apple : [5,0.6]
        HashMap<String, double[]> mapQry = new HashMap<>();
        int numOfWords = words.length;

        for (String word : words) {
            if (mapQry.containsKey(word)) {
                mapQry.get(word)[0] += 1;
            } else {
                mapQry.put(word, new double[]{1, 1});
            }
        }

        // calculate tf-idf for each word and store it to the first entry of its value
        for (String word : mapQry.keySet()) {
            double tf = mapQry.get(word)[0] / numOfWords;
            // tfidf = log2(1+tf) * itf
            mapQry.get(word)[1] = (Math.log(1+tf) / Math.log(2)) * crawlInfo.getIDFValue(word);
        }

        return mapQry;
    }

    public static LinkedList<Webpage> calculateSimilarityScore(CrawlInfo crawlInfo, HashMap<String, double[]> queryInfo, LinkedList<Webpage> pages, boolean boost) {
        // for each search word in dict, calculate similarity score and store it to the list (dictionary list that tracks url, title and score)
        for (Webpage page : pages) {
            double numerator = 0;
            double denominator = 0;
            double sumSqrDocTFIDF = 0;
            double sumSqrQryTFIDF = 0;

            for (String word : queryInfo.keySet()) {
                double docTFIDF = page.getWordTFIDF(word);
                double qryTFIDF = queryInfo.get(word)[1]; // could be improved

                // calculate the numerator
                numerator += docTFIDF * qryTFIDF;

                // calculate the denominator factors
                sumSqrDocTFIDF += Math.pow(docTFIDF, 2);
                sumSqrQryTFIDF += Math.pow(qryTFIDF, 2);
            }

            // calculate the denominator
            denominator = Math.sqrt(sumSqrDocTFIDF * sumSqrQryTFIDF);

            // calculate the final result
            if (denominator == 0) {
                page.setScore(0);
            } else {
                double score = numerator / denominator;
                if (boost) {
                    // score multiplied by page rank
                    score *= page.getPageRank();
                }
                page.setScore(score);
            }
        }
        return pages;
    }

    public static LinkedList<Webpage> sortPages(LinkedList<Webpage> pages) {
        return SortTools.quickSort(pages);
    }

}
