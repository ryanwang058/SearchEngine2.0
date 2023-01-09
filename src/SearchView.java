package src;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class SearchView extends Pane {
    private TextField   searchWordField, URLField,xField;
    private Button  crawlButton, pageRankBoostButton, searchButton;
    private ListView<String>    searchResultList;

    public SearchView() {
        //Create a url label
        Label urlLabel = new Label("Root URL:");
        urlLabel.relocate(10,10);
        urlLabel.setPrefSize(80,30);

        // create the url field
        this.URLField = new TextField();
        this.URLField.relocate(90, 10);
        this.URLField.setPrefSize(390, 30);

        //Create the crawl button
        this.crawlButton = new Button("Crawl");
        this.crawlButton.relocate(10, 50);
        this.crawlButton.setPrefSize(480, 30);

        //Create a search word label
        Label searchWordLabel = new Label("Search Word:");
        searchWordLabel.relocate(10,90);
        searchWordLabel.setPrefSize(80,30);

        // Create and position the "Search" TextField
        this.searchWordField = new TextField();
        this.searchWordField.relocate(90, 90);
        this.searchWordField.setPrefSize(160, 30);

        // Create and position the "Pagerank" Button
        this.pageRankBoostButton = new Button("PageRankBoost");
        this.pageRankBoostButton.relocate(255, 90);
        this.pageRankBoostButton.setPrefSize(235, 30);
        this.pageRankBoostButton.setStyle("-fx-background-color: red; ");

        //Create a x word label
        Label xLabel = new Label("# of Answers:");
        xLabel.relocate(10,130);
        xLabel.setPrefSize(80,30);

        //Create and position the X TextField
        this.xField = new TextField();
        this.xField.relocate(90, 130);
        this.xField.setPrefSize(160, 30);

        // Create and position the "Search" Button
        this.searchButton = new Button("Search");
        this.searchButton.relocate(260, 130);
        this.searchButton.setPrefSize(230, 30);

        // Create and position the "search result" ListView with some search results in it
        this.searchResultList = new ListView<String>();
        this.searchResultList.relocate(10, 170);
        this.searchResultList.setPrefSize(480, 270);

        // Add all the components to the window
        getChildren().addAll(urlLabel,URLField,crawlButton,searchWordLabel,xLabel,xField,searchWordField,pageRankBoostButton,searchButton,searchResultList);
        setPrefSize(500, 420);
    }
    public void update(SearchModel model){
        this.searchResultList.setItems(FXCollections.observableArrayList(model.getFinalResult()));
    }
    public Button getCrawlButton(){ return crawlButton; }
    public TextField getURLField(){ return URLField; }
    public TextField getSearchWordField(){ return searchWordField; }
    public Button getPageRankBoostButton() { return pageRankBoostButton; }
    public Button getSearchButton(){ return searchButton; }
    public TextField getNumberOfResults(){ return xField; }
}