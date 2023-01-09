package src;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class SearchApplication extends Application {
    private SearchModel model;
    public SearchApplication(){

    }

    public void start(Stage primaryStage) {
        Pane aPane = new Pane();
        model = new SearchModel();

        SearchView  view = new SearchView();
        aPane.getChildren().add(view);

        setHandlers(view);

        primaryStage.setTitle("WebScraper 2.0");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(aPane));
        //view.update(model);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    public void setHandlers(SearchView view){
        view.getCrawlButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                String url = view.getURLField().getText();
                model.crawl(url);
            }
        });
        view.getPageRankBoostButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                if(view.getPageRankBoostButton().getStyle().contains("-fx-background-color: red")){
                    view.getPageRankBoostButton().setStyle("-fx-background-color: green; ");
                }else{
                    view.getPageRankBoostButton().setStyle("-fx-background-color: red; ");
                }
            }
        });
        view.getSearchButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                String searchWord = view.getSearchWordField().getText();
                boolean pageRankOn = view.getPageRankBoostButton().getStyle().contains("-fx-background-color: green");
                int x = Integer.parseInt(view.getNumberOfResults().getText());
                model.search(searchWord,pageRankOn,x);
                view.update(model);
            }
        });
    }
}