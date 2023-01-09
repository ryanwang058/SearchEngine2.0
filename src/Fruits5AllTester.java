package src;

public class Fruits5AllTester {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        SearchModel tester = new SearchModel(); //Instantiate your own ProjectTester instance here
        tester.initialize();
        tester.crawl("https://people.scs.carleton.ca/~davidmckenney/fruits5/N-0.html");
        long end = System.currentTimeMillis();
        System.out.println("crawling: " + Long.toString(end-start));

        start = System.currentTimeMillis();
        Fruits5OutgoingLinksTester.runTest(tester);
        end = System.currentTimeMillis();
        System.out.println("outgoing links: " + Long.toString(end-start));

        start = System.currentTimeMillis();
        Fruits5IncomingLinksTester.runTest(tester);
        end = System.currentTimeMillis();
        System.out.println("incoming links: " + Long.toString(end-start));

        start = System.currentTimeMillis();
        Fruits5PageRanksTester.runTest(tester);
        end = System.currentTimeMillis();
        System.out.println("page rank: " + Long.toString(end-start));

        start = System.currentTimeMillis();
        Fruits5IDFTester.runTest(tester);
        end = System.currentTimeMillis();
        System.out.println("idf: " + Long.toString(end-start));

        start = System.currentTimeMillis();
        Fruits5TFTester.runTest(tester);
        end = System.currentTimeMillis();
        System.out.println("tf: " + Long.toString(end-start));

        start = System.currentTimeMillis();
        Fruits5TFIDFTester.runTest(tester);
        end = System.currentTimeMillis();
        System.out.println("tf-idf: " + Long.toString(end-start));

        start = System.currentTimeMillis();
        Fruits5SearchTester.runTest(tester);
        end = System.currentTimeMillis();
        System.out.println("search: " + Long.toString(end-start));

        System.out.println("Finished running all tests.");
    }
}
