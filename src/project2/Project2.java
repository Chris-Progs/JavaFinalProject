package project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Project2 extends Application {

    
    Stage window;
    static Button addBtn;
    static Button saveBtn;
    static Button loadBtn;
    static Button searchBtn;
    static Button signInBtn;
    static Button playBtn;
    private TextField searchField;
    private String selectedSong;
    private static ObservableList<Song> obList;
    private static TableView<Song> tableView;
    private static LinkedList linkedList = new LinkedList();

    public void addSong(String filePath, String songName) {
        Song newSong = new Song();
        newSong.setFilePath(filePath);
        newSong.setSongName(songName);
        linkedList.addNode(newSong);
    }
    
    public static LinkedList getLinkedList() {
        return linkedList;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setTitle("Advanced Music Player");
        Menu helpMenu = new Menu("_Help");
        MenuItem help = new MenuItem("Help");
        

        help.setOnAction(e -> {
            HelpWindow helpWindow = new HelpWindow();
            helpWindow.display();
            
                });

        
        helpMenu.getItems().add(help);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(helpMenu);
        
        TableColumn<Song, String> songList = new TableColumn("Song List");
        songList.setMinWidth(250);
        songList.setCellValueFactory(new PropertyValueFactory<>("songName")); // was name

        TableColumn<Song, String> filePath = new TableColumn("File Path");
        filePath.setMinWidth(350);
        filePath.setCellValueFactory(new PropertyValueFactory<>("filePath"));

        // Create table view and add columns created above
        tableView = new TableView<>();
        tableView.getColumns().addAll(songList, filePath);

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
            @Override
            public void changed(ObservableValue<? extends Song> ov, Song t, Song t1) {
                int index = tableView.getSelectionModel().getSelectedIndex();
                selectedSong = linkedList.findSongOfIndex(index);
            }
        });

        // Button to open file directory and add a song to LinkedList, also uses merge sort for each new song
        addBtn = new Button("Add Song");
        addBtn.setDisable(true);
        addBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(window);
            if (file != null) {
                addSong(file.getPath(), file.getName());
                linkedList.mergeSort();
                obList = linkedList.linkedToObservable();
                tableView.setItems(obList);
            }
        });

        // Button to create account and sign in - if successful all other functions enabled.
        signInBtn = new Button("Create Account & Sign In");
        signInBtn.setOnAction(e -> {
            SignInWindow signIn = new SignInWindow();
            if (signIn.display()) {

            }
        });

        // Button to open file directory, read the data from .csv file and display it in the table view
        loadBtn = new Button("Load Playlist");
        loadBtn.setDisable(true);
        loadBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(window);
            if (file != null) {
                try {
                    linkedList = CSV.readFromCSV(file);
                    obList = linkedList.linkedToObservable();
                    tableView.setItems(obList);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });

        // Button to save/write data to .csv file in project folder
        saveBtn = new Button("Save Playlist");
        saveBtn.setDisable(true);
        saveBtn.setOnAction(e -> {
            ObservableList<Song> results;
            results = tableView.getItems();
            CSV.writeToCSV(results);
        });

        // Button to play song that is selected from the tableview using Windows Media Player
        playBtn = new Button("Play");
        playBtn.setDisable(true);
        playBtn.setOnAction(e -> {
            String command = "C:\\Program Files\\Windows Media Player\\wmplayer.exe";
            ProcessBuilder builder = new ProcessBuilder(command, selectedSong);
            try {
                builder.start();
            } catch (IOException ee) {
                System.out.println(ee);
            }
        });

        // Button to search for a song taking user input from text box
        searchBtn = new Button("Search");
        searchBtn.setDisable(true);
        searchBtn.setOnAction(e -> {
            Song temp = new Song();
            temp.setSongName(searchField.getText());
            temp = linkedList.binarySearch(temp);
            if (temp != null) {
                int tableIndex = linkedList.findIndexOfSong(temp);
                tableView.getSelectionModel().select(tableIndex);
            }
            else {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText(searchField.getText() + " was not found!");
                a.show();
            }
        });

        Label searchLabel = new Label();
        searchLabel.setText("Search Songs:");
        searchField = new TextField();

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.setSpacing(25);
        hBox.getChildren().addAll(loadBtn, saveBtn, addBtn, signInBtn, playBtn, searchBtn);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(menuBar, tableView, hBox, searchLabel, searchField);
        vBox.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: red;");

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
    }
}
