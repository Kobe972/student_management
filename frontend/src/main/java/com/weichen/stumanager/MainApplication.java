package com.weichen.stumanager;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.*;


public class MainApplication extends Application {
    private final String GET_USERNAME_URL = "http://47.108.160.172:7001/getUsername";
    private final String COURSE_LIST_URL = "http://47.108.160.172:7001/courseList?num=5";

    private String username;
    private Course[] courses;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set up the layout of the main window
        BorderPane root = new BorderPane();
        root.setTop(createNavigationBar());
        root.setCenter(createCourseList());

        // Set the title and scene of the primary stage
        primaryStage.setTitle("微臣托福教育在线平台");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private HBox createNavigationBar() {
        // Set up the navigation bar
        HBox navigationBar = new HBox();
        navigationBar.setPadding(new Insets(10));
        navigationBar.setAlignment(Pos.CENTER_LEFT);
        navigationBar.setStyle("-fx-background-color: #336699;");

        // Set up the buttons in the navigation bar
        Button homeButton = new Button("首页");
        Button registerButton = new Button("注册");
        Button resourceButton = new Button("资料共享");
        Button testButton = new Button("听力1500词测试");
        Button loginButton = new Button("登录");
        Button logoutButton = new Button("登出");
        loginButton.setVisible(false);
        logoutButton.setVisible(false);

        // Set up the label in the navigation bar
        Label label = new Label("微臣托福教育在线平台");
        label.setStyle("-fx-text-fill: white;");

        // Set up the actions for the buttons
        homeButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Home Button");
            alert.setHeaderText(null);
            alert.setContentText("更多功能请使用浏览器网页端");
            alert.showAndWait();
            try {
                openInBrowser("http://47.108.160.172:7001/");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

        registerButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Register Button");
            alert.setHeaderText(null);
            alert.setContentText("更多功能请使用浏览器网页端");
            alert.showAndWait();
            try {
                openInBrowser("http://47.108.160.172:7001/register");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

        resourceButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Resource Button");
            alert.setHeaderText(null);
            alert.setContentText("更多功能请使用浏览器网页端");
            alert.showAndWait();
            try {
                openInBrowser("http://47.108.160.172:7001/resources");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

        testButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Test Button");
            alert.setHeaderText(null);
            alert.setContentText("更多功能请使用浏览器网页端");
            alert.showAndWait();
            try {
                openInBrowser("http://47.108.160.172:7001/sheetChoice");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

        loginButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Button");
            alert.setHeaderText(null);
            alert.setContentText("更多功能请使用浏览器网页端");
            alert.showAndWait();
            try {
                openInBrowser("http://47.108.160.172:7001/login");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

        logoutButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Logout Button");
            alert.setHeaderText(null);
            alert.setContentText("Please go to the browser");
            alert.showAndWait();
            try {
                openInBrowser("http://47.108.160.172:7001/logout");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

        // Add the elements to the navigation bar
        navigationBar.getChildren().addAll(homeButton, registerButton, resourceButton, testButton, label, loginButton, logoutButton);

        // Check the login status and show the appropriate buttons
        String username = getUsername();
        if (username.isEmpty()) {
            loginButton.setVisible(true);
        } else {
            logoutButton.setVisible(true);
        }

        return navigationBar;
    }

    private VBox createCourseList() {
        // Set up the layout for the course list
        VBox courseList = new VBox();
        courseList.setPadding(new Insets(10));
        courseList.setSpacing(8);
        courseList.setAlignment(Pos.TOP_LEFT);

        // Set up the label for the course list
        Label label = new Label("课程推荐");
        label.setStyle("-fx-font-size: 20;");
        courseList.getChildren().add(label);

        // Get the list of courses from the server
        courses = getCourseList();

        // Add the courses to the course list
        for (Course course : courses) {
            HBox courseBox = new HBox();
            courseBox.setPadding(new Insets(10));
            courseBox.setSpacing(8);
            courseBox.setAlignment(Pos.TOP_LEFT);
            ImageView coverView = new ImageView(course.coverURL);
            coverView.setFitWidth(100);
            coverView.setPreserveRatio(true);

            VBox infoBox = new VBox();
            infoBox.setSpacing(8);
            Label titleLabel = new Label(course.title);
            titleLabel.setStyle("-fx-font-size: 18;");
            Label descriptionLabel = new Label(course.description);

            infoBox.getChildren().addAll(titleLabel, descriptionLabel);

            courseBox.getChildren().addAll(coverView, infoBox);
            courseList.getChildren().add(courseBox);

            // Set up the action for clicking on a course
            courseBox.setOnMouseClicked(event -> {
                // Open the sign up URL in the browser
                try {
                    openInBrowser(course.signUpURL);
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            });
        }

        return courseList;
    }

    private String getUsername() {
        try {
            URL url = new URL(GET_USERNAME_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            Scanner scanner = new Scanner(connection.getInputStream());
            if (scanner.hasNextLine()) {
                // The server returned a non-empty string
                return scanner.nextLine();
            } else {
                // The server returned an empty string
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }



    private Course[] getCourseList() {
        // Send a GET request to the server to get the course list
        try {
            URL url = new URL(COURSE_LIST_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            Scanner scanner = new Scanner(connection.getInputStream());
            String response = scanner.nextLine();

            // Parse the response from the server into an array of courses
            return parseCourseListResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
            return new Course[0];
        }
    }


    private Course[] parseCourseListResponse(String response) {
        String patternString = "\"(coverURL|title|description|signUpURL)\":\"([^\"]*)\"";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(response);
        List<Course> courses = new ArrayList<>();
        Course course = new Course();
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
            if (key.equals("coverURL")) {
                course.coverURL = "http://47.108.160.172:7001"+value;
            } else if (key.equals("title")) {
                course.title = value;
            } else if (key.equals("description")) {
                course.description = value;
            } else if (key.equals("signUpURL")) {
                course.signUpURL = value;
            }
            if (key.equals("signUpURL")) {
                courses.add(course);
                course = new Course();
            }
        }
        return courses.toArray(new Course[0]);
    }

    private void openInBrowser(String url) throws IOException, URISyntaxException {
        // Open the given URL in the user's default browser
        Desktop.getDesktop().browse(new URI(url));
    }
}

class Course {
    public String coverURL;
    public String title;
    public String description;
    public String signUpURL;
}

