package src;

public class FruitsTinyAllTester {
    public static void main(String[] args) throws Exception {
        SearchModel tester = new SearchModel(); //Instantiate your own ProjectTester instance here
        tester.initialize();
        tester.crawl("https://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-0.html");

        FruitsTinyOutgoingLinksTester.runTest(tester);
        FruitsTinyIncomingLinksTester.runTest(tester);
        FruitsTinyPageRanksTester.runTest(tester);
        FruitsTinyIDFTester.runTest(tester);
        FruitsTinyTFTester.runTest(tester);
        FruitsTinyTFIDFTester.runTest(tester);
        FruitsTinySearchTester.runTest(tester);
        System.out.println("Finished running all tests.");
    }
}
