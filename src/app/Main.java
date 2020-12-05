package app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;

public class Main extends Application {

    public static Font main_font = new Font("Comic Sans", 20);
    private static Connection dbConnection;
    private static final int windows_width = 700, windows_height = 980;
    private static final String import_path = "src\\importdata\\";
    private static final String export_path = "src\\exportdata\\";

    private Stage window;

    // --- Creating the scenes --- //
    private final Scene
            login_scene = login_s(),
            main_scene = welcome_s(),
            export_scene = export_s(),
            export_confirmation_scene = export_success_s(),
            confirmation_scene = success_s();

    public static void main(String[] args) {
        launch();
    }

    private static Connection CreateConnection(String dbURL, String dbName, String dbUsername, String dbPassword) {
        String url = (dbURL + "databaseName=" + dbName + ";user=" + dbUsername + ";password=" + dbPassword + ";");
        Connection dbCon = null;
        try {
            dbCon = DriverManager.getConnection(url);
            System.out.println("Connected Successfully!");
        } catch (SQLException sqlEX) {
            System.out.println("Error establishing the connection to the database!");
            System.out.println("Check VPN connection");
            System.out.println("Application will exit!!!");
            System.exit(-1);
        }
        return dbCon;
    }

    @Override
    public void start(Stage primaryStage) {
        /* -- Login Scene-- */
        // --------------------------------------------------------------------------------- //
        /* --- Those should be given from the Command Line or through the User Interface --- */
        String dbRL = "jdbc:sqlserver://APOLLO.IN.CS.UCY.AC.CY:1433;";
        String dbName = "gproko01";
        String dbUser = "gproko01";
        String dbPassword = "3cp2e7UW";

        /* --- Create the connection with the database --- */
        dbConnection = CreateConnection(dbRL, dbName, dbUser, dbPassword);
        // --------------------------------------------------------------------------------- //

        // Running function
        window = primaryStage;
        window.setTitle("Social Face - Administration Application");
        window.setScene(login_scene);
        window.show();
    }

    private Scene login_s() {
        Button login_btn, exit_btn;
        Label login = new Label("Please log in in order to access the database!");
        login.setFont(main_font);
        VBox login_box = new VBox(5);
        login_box.setAlignment(Pos.CENTER);

        login_btn = new Button("Login");
        login_btn.setOnAction(event -> {
            LoginAlert.displayLogin_alert();
            window.setScene(main_scene);
        });
        exit_btn = new Button("Exit Application");
        exit_btn.setOnAction(event -> {
            System.out.println("Exiting Application");
            System.exit(0);
        });

        login_box.getChildren().addAll(login, login_btn, exit_btn);
        return new Scene(login_box, windows_width, windows_height);
    }

    private Scene welcome_s() {
        Button import_btn, export_btn, delete_btn, exit_btn;
        Label welcome = new Label("Select from available functions:");
        welcome.setFont(main_font);
        VBox welcome_box = new VBox(5);
        welcome_box.setAlignment(Pos.CENTER);

        import_btn = new Button("Import Database");
        import_btn.setOnAction(event -> {
            /* -- Import Database -- */
            createTables();
            importAllData();
            window.setScene(confirmation_scene);
        });

        export_btn = new Button("Export Database");
        export_btn.setOnAction(event -> {
            /* -- Export Database -- */
            window.setScene(export_scene);
        });

        delete_btn = new Button("Delete Database");
        delete_btn.setOnAction(event -> {
            /* -- Delete Database -- */
            boolean admin_answer = DeleteAlert.displayDelete_alert();
            if (admin_answer) {
                dropAllData();
                window.setScene(confirmation_scene);
            } else
                window.setScene(main_scene);
        });

        exit_btn = new Button("Exit Application");
        exit_btn.setOnAction(event -> {
            System.out.println("Exiting Application");
            System.exit(0);
        });

        welcome_box.getChildren().addAll(welcome, import_btn, export_btn, delete_btn, exit_btn);
        return new Scene(welcome_box, windows_width, windows_height);
    }

    private Scene success_s() {
        Button back_btn = new Button("Go back!");
        // Confirmation User Graphical Interface
        Label success = new Label("Function executed successfully!");
        success.setFont(main_font);

        VBox success_box = new VBox(5);
        success_box.setAlignment(Pos.CENTER);

        back_btn.setOnAction(event -> window.setScene(main_scene));

        success_box.getChildren().addAll(success, back_btn);
        return new Scene(success_box, windows_width, windows_height);
    }

    private Scene export_s() {
        Button account_btn, album_btn, album_photo_btn, album_video_btn, bookmark_btn, city_btn, comment_album_btn, comment_video_btn, education_btn, event_btn, friend_req_btn, interest_btn, likes_btn, location_btn, log_file_btn, participants_btn, photo_btn, privacy_btn, profile_btn, profile_interest_btn, quotes_btn, video_btn, website_btn, works_btn, all_btn, back_btn;

        Label export = new Label("Please select which table to export:");
        export.setFont(main_font);

        VBox export_box = new VBox(5);
        export_box.setAlignment(Pos.CENTER);


        account_btn = new Button("Export ACCOUNT Table Data");
        account_btn.setOnAction(event -> {
            exportAccountData();
            window.setScene(export_confirmation_scene);
        });


        profile_btn = new Button("Export PROFILE Table Data");
        profile_btn.setOnAction(event -> {
            exportProfileData();
            window.setScene(export_confirmation_scene);
        });


        interest_btn = new Button("Export INTEREST Table Data");
        interest_btn.setOnAction(event -> {
            exportInterestData();
            window.setScene(export_confirmation_scene);
        });


        profile_interest_btn = new Button("Export PROFILE-INTEREST Table Data");
        profile_interest_btn.setOnAction(event -> {
            exportProfileInterestData();
            window.setScene(export_confirmation_scene);
        });


        education_btn = new Button("Export EDUCATION Table Data");
        education_btn.setOnAction(event -> {
            exportEducationData();
            window.setScene(export_confirmation_scene);
        });


        works_btn = new Button("Export WORKS Table Data");
        works_btn.setOnAction(event -> {
            exportWorksData();
            window.setScene(export_confirmation_scene);
        });


        bookmark_btn = new Button("Export BOOKMARK Table Data");
        bookmark_btn.setOnAction(event -> {
            exportBookmarkData();
            window.setScene(export_confirmation_scene);
        });


        quotes_btn = new Button("Export QUOTE Table Data");
        quotes_btn.setOnAction(event -> {
            exportQuotesData();
            window.setScene(export_confirmation_scene);
        });


        website_btn = new Button("Export WEBSITE Table Data");
        website_btn.setOnAction(event -> {
            exportWebsiteData();
            window.setScene(export_confirmation_scene);
        });

        friend_req_btn = new Button("Export FRIEND-REQUEST Table Data");
        friend_req_btn.setOnAction(event -> {
            exportFriendRequestData();
            window.setScene(export_confirmation_scene);
        });


        album_btn = new Button("Export ALBUM Table Data");
        album_btn.setOnAction(event -> {
            exportAlbumData();
            window.setScene(export_confirmation_scene);
        });

        album_photo_btn = new Button("Export ALBUM-PHOTO Table Data");
        album_photo_btn.setOnAction(event -> {
            exportAlbumPhotoData();
            window.setScene(export_confirmation_scene);
        });


        album_video_btn = new Button("Export ALBUM-VIDEO Table Data");
        album_video_btn.setOnAction(event -> {
            exportAlbumVideoData();
            window.setScene(export_confirmation_scene);
        });


        comment_album_btn = new Button("Export COMMENT-ALBUM Table Data");
        comment_album_btn.setOnAction(event -> {
            exportAlbumCommentData();
            window.setScene(export_confirmation_scene);
        });


        photo_btn = new Button("Export PHOTO Table Data");
        photo_btn.setOnAction(event -> {
            exportPhotoData();
            window.setScene(export_confirmation_scene);
        });


        video_btn = new Button("Export VIDEO Table Data");
        video_btn.setOnAction(event -> {
            exportVideoData();
            window.setScene(export_confirmation_scene);
        });

        comment_video_btn = new Button("Export COMMENT-VIDEO Table Data");
        comment_video_btn.setOnAction(event -> {
            exportCommentVideoData();
            window.setScene(export_confirmation_scene);
        });


        likes_btn = new Button("Export LIKES Table Data");
        likes_btn.setOnAction(event -> {
            exportLikesData();
            window.setScene(export_confirmation_scene);
        });


        city_btn = new Button("Export CITY Table Data");
        city_btn.setOnAction(event -> {
            exportCityData();
            window.setScene(export_confirmation_scene);
        });


        location_btn = new Button("Export LOCATION Table Data");
        location_btn.setOnAction(event -> {
            exportLocationData();
            window.setScene(export_confirmation_scene);
        });

        log_file_btn = new Button("Export LOG-FILE Table Data");
        log_file_btn.setOnAction(event -> {
            exportLogFileData();
            window.setScene(export_confirmation_scene);
        });


        event_btn = new Button("Export EVENT Table Data");
        event_btn.setOnAction(event -> {
            exportEventData();
            window.setScene(export_confirmation_scene);
        });


        participants_btn = new Button("Export PARTICIPANTS Table Data");
        participants_btn.setOnAction(event -> {
            exportParticipantsData();
            window.setScene(export_confirmation_scene);
        });


        privacy_btn = new Button("Export PRIVACY Table Data");
        privacy_btn.setOnAction(event -> {
            exportPrivacyData();
            window.setScene(export_confirmation_scene);
        });


        all_btn = new Button("Export All Data");
        all_btn.setOnAction(event -> {
            exportAllData();
            window.setScene(export_confirmation_scene);
        });

        back_btn = new Button("Go back!");
        back_btn.setOnAction(event -> window.setScene(main_scene));


        export_box.getChildren().addAll(export, account_btn, profile_btn, interest_btn, profile_interest_btn, education_btn, works_btn, bookmark_btn, quotes_btn, website_btn, friend_req_btn, album_btn, album_photo_btn, album_video_btn, comment_album_btn, photo_btn, video_btn, comment_video_btn, likes_btn, city_btn, location_btn, log_file_btn, event_btn, participants_btn, privacy_btn, all_btn, back_btn);
        return new Scene(export_box, windows_width, windows_height);
    }

    private Scene export_success_s() {
        Button back_btn = new Button("Go back!");
        // Confirmation User Graphical Interface
        Label success = new Label("Data exported successfully!");
        success.setFont(main_font);

        VBox success_box = new VBox(5);
        success_box.setAlignment(Pos.CENTER);

        back_btn.setOnAction(event -> window.setScene(export_scene));

        success_box.getChildren().addAll(success, back_btn);
        return new Scene(success_box, windows_width, windows_height);
    }

    private static void createTables() {
        String sp = "{call upsCreateTables}";
        CallableStatement cs;
        try {
            cs = dbConnection.prepareCall(sp);
            cs.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static void dropAllData() {
        String sp = "{call upsDropTables}";
        CallableStatement cs;
        try {
            cs = dbConnection.prepareCall(sp);
            cs.executeUpdate();
            cs.executeUpdate();
            System.out.println("All tables deleted successfully!");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    private static void importAccountData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "ACCOUNT.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    String username = split[0];
                    String password = split[1];
                    Statement insert = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    insert.executeUpdate("INSERT INTO [dbo].ACCOUNT(username,password) VALUES('" + username + "','" + password + "')");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table ACCOUNT done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importCityData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "CITY.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    Statement insert = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    insert.executeUpdate("INSERT INTO [dbo].CITY(city)" +
                            "VALUES('" + strCurrentLine + "')");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table CITY done.");
            objReader.close();
        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importLocationData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "LOCATION.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    byte sn = Byte.parseByte(split[1]);
                    short pc = Short.parseShort(split[2]);
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate("INSERT INTO [dbo].LOCATION(street_name,street_number,postal_code)" +
                            "VALUES ('" + split[0] + "'," + sn + "," + pc + ")");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table LOCATION done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importPrivacyData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "PRIVACY.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");

                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate("INSERT INTO [dbo].PRIVACY(privacy)" +
                            "VALUES ('" + split[0] + "')");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table PRIVACY done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importProfileData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "PROFILE.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    int ai = Integer.parseInt(split[0]);
                    int ht = Integer.parseInt(split[6]);
                    int l = Integer.parseInt(split[7]);
                    int bit = Integer.parseInt(split[8]);
                    int v = Integer.parseInt(split[9]);

                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate(
                            "INSERT INTO [dbo].PROFILE(first_name,last_name,link,birthdate,email,hometown,location,gender,verified,account_id)"
                                    + " VALUES ('" + split[1] + "','" + split[2] + "','" + split[3] + "','" + split[4] + "','" + split[5] + "'," + ht + "," + l + "," + bit + "," + v + "," + ai + ")");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table PROFILE done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importAlbumData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "ALBUM.txt"));
            String strCurrentLine;
            objReader.readLine();


            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    int user_id = Integer.parseInt(split[0]);
                    boolean flag = false;
                    if (split[3].contains("null"))
                        flag = true;

                    byte privacy = Byte.parseByte(split[5]);
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    if (flag) {
                        stmt.executeUpdate(
                                "INSERT INTO [dbo].ALBUM(user_id,name,description,link,privacy)" +
                                        "VALUES (" + user_id + ",'" + split[1] + "','" + split[2] + "','" + split[4] + "'," + privacy + ")");
                    } else {
                        stmt.executeUpdate(
                                "INSERT INTO [dbo].ALBUM(user_id,name,description,location,link,privacy)" +
                                        "VALUES (" + user_id + ",'" + split[1] + "','" + split[2] + "'," + Integer.parseInt(split[3]) + ",'" + split[4] + "'," + privacy + ")");
                    }
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table ALBUM done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importPhotoData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "PHOTO.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    int uid = Integer.parseInt(split[0]);
                    short height = Short.parseShort(split[2]);
                    short width = Short.parseShort(split[3]);
                    byte p = Byte.parseByte(split[5]);

                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    if (split.length == 8)
                        stmt.executeUpdate(
                                "INSERT INTO [dbo].PHOTO(user_id,directory,height,width,link, privacy, album_id)" +
                                        "VALUES (" + uid + ",'" + split[1] + "'," + height + "," + width + ",'" + split[4] + "'," + p + "," + Integer.parseInt(split[6]) + ")");
                    else
                        stmt.executeUpdate(
                                "INSERT INTO [dbo].PHOTO(user_id,directory,height,width,link, privacy)" +
                                        "VALUES (" + uid + ",'" + split[1] + "'," + height + "," + width + ",'" + split[4] + "'," + p + ")");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table PHOTO done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importVideoData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "VIDEO.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    int uid = Integer.parseInt(split[0]);
                    boolean flag = false;
                    if (split[3].contains("null"))
                        flag = true;
                    byte p = Byte.parseByte(split[4]);
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    if (flag) {
                        if (split.length == 6)
                            stmt.executeUpdate(
                                    "INSERT INTO [dbo].VIDEO(user_id,message,description,privacy,album_id)" +
                                            "VALUES (" + uid + ",'" + split[1] + "','" + split[2] + "'," + p + ",'" + split[5] + "')");
                        else
                            stmt.executeUpdate(
                                    "INSERT INTO [dbo].VIDEO(user_id,message,description,privacy)" +
                                            "VALUES (" + uid + ",'" + split[1] + "','" + split[2] + "'," + p + ")");
                    } else {
                        if (split.length == 6)
                            stmt.executeUpdate(
                                    "INSERT INTO [dbo].VIDEO(user_id,message,description,length,privacy,album_id)" +
                                            "VALUES (" + uid + ",'" + split[1] + "','" + split[2] + "'," + Integer.parseInt(split[3]) + "," + p + ",'" + split[5] + "')");
                        else
                            stmt.executeUpdate(
                                    "INSERT INTO [dbo].VIDEO(user_id,message,description,length,privacy)" +
                                            "VALUES (" + uid + ",'" + split[1] + "','" + split[2] + "'," + Integer.parseInt(split[3]) + "," + p + ")");

                    }
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table VIDEO done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importQuotesData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "QUOTES.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    int uid = Integer.parseInt(split[0]);

                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate(
                            "INSERT INTO [dbo].QUOTES(user_id,quote)" +
                                    "VALUES (" + uid + ",'" + split[1] + "')");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table QUOTES done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importWebsiteData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "WEBSITE.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");

                    int uid = Integer.parseInt(split[0]);


                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate(
                            "INSERT INTO [dbo].WEBSITE(user_id,website)" +
                                    "VALUES (" + uid + ",'" + split[1] + "')");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table WEBSITE done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importWorksData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "WORKS.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    int uid = Integer.parseInt(split[0]);

                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate(
                            "INSERT INTO [dbo].WORKS(user_id,work)" +
                                    "VALUES (" + uid + ",'" + split[1] + "')");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table WORKS done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importEducationData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "EDUCATION.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    int uid = Integer.parseInt(split[0]);

                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate(
                            "INSERT INTO [dbo].EDUCATION(user_id,education)" +
                                    "VALUES (" + uid + ",'" + split[1] + "')");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table EDUCATION done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importInterestData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "INTEREST.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    if (strCurrentLine.contains("'"))
                        continue;
                    stmt.executeUpdate(
                            "INSERT INTO [dbo].INTEREST(interest)" +
                                    "VALUES ('" + strCurrentLine + "')");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table INTEREST done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importProfileInterestData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "PROFILE_INTEREST.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    int uid = Integer.parseInt(split[0]);
                    int iid = Integer.parseInt(split[1]);

                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate(
                            "INSERT INTO [dbo].PROFILE_INTEREST(user_id,interest_id)" +
                                    "VALUES (" + uid + "," + iid + ")");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table PROFILE_INTEREST done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importLikesData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "LIKES.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    int uid = Integer.parseInt(split[0]);
                    int pid = Integer.parseInt(split[1]);

                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate(
                            "INSERT INTO [dbo].LIKES(user_id,photo_id)" +
                                    "VALUES (" + uid + "," + pid + ")");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table LIKES done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importFriendRequestData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "FRIEND_REQUEST.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    int u1id = Integer.parseInt(split[0]);
                    int u2id = Integer.parseInt(split[1]);
                    int p = Integer.parseInt(split[2]);
                    int i = Integer.parseInt(split[3]);

                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate(
                            "INSERT INTO [dbo].FRIEND_REQUEST(user1_id,user2_id,pending,ignore)" + "" +
                                    "VALUES (" + u1id + "," + u2id + "," + p + "," + i + ")");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table FRIEND_REQUEST done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importBookmarkData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "BOOKMARK.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    int uid = Integer.parseInt(split[0]);
                    byte p = Byte.parseByte(split[6]);

                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate(
                            "INSERT INTO [dbo].BOOKMARK(user_id,link,name,caption,description,message,privacy)" +
                                    "VALUES (" + uid + ",'" + split[1] + "','" + split[2] + "','" + split[3] + "','" + split[4] + "','" + split[5] + "'," + p + ")");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table BOOKMARK done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importAlbumCommentData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "ALBUM_COMMENT.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    int user_id = Integer.parseInt(split[0]);
                    int album = Integer.parseInt(split[2]);
                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate(
                            "INSERT INTO [dbo].COMMENT_ALBUM(user_id,comment,album_id)" +
                                    "VALUES (" + user_id + ",'" + split[1] + "'," + album + ")");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table ALBUM_COMMENT done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importCommentVideoData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "COMMENT_VIDEO.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    int user_id = Integer.parseInt(split[0]);
                    int video = Integer.parseInt(split[2]);

                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate(
                            "INSERT INTO [dbo].COMMENT_VIDEO(user_id,comment,video_id)" +
                                    "VALUES (" + user_id + ",'" + split[1] + "'," + video + ")");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table COMMENT_VIDEO done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importEventData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "EVENT.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");

                    int owner = Integer.parseInt(split[0]);
                    java.sql.Timestamp start = java.sql.Timestamp.valueOf(split[3]);
                    java.sql.Timestamp end = java.sql.Timestamp.valueOf(split[4]);
                    int location = Integer.parseInt(split[5]);
                    int city = Integer.parseInt(split[6]);
                    byte privacy = Byte.parseByte(split[7]);

                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate(
                            "INSERT INTO [dbo].EVENT(owner,name,description,start_time,end_time,location,venue,privacy)" +
                                    "VALUES (" + owner + ",'" + split[1] + "','" + split[2] + "','" +
                                    start + "','" + end + "'," + location + "," + city + "," + privacy + ")");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table EVENT done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importParticipantsData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "PARTICIPANTS.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    int uid = Integer.parseInt(split[0]);
                    int evid = Integer.parseInt(split[1]);

                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate(
                            "INSERT INTO [dbo].PARTICIPANTS(user_id,event_id)" +
                                    "VALUES (" + uid + "," + evid + ")");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table PARTICIPANTS done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importAlbumVideoData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "ALBUM_VIDEO.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    int alb_id = Integer.parseInt(split[0]);
                    int vid_id = Integer.parseInt(split[1]);

                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate(
                            "INSERT INTO [dbo].ALBUM_VIDEO(album_id,video_id)" +
                                    "VALUES (" + alb_id + "," + vid_id + ")");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table ALBUM_VIDEO done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importAlbumPhotoData() {
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(import_path + "ALBUM_PHOTO.txt"));
            String strCurrentLine;
            objReader.readLine();

            while ((strCurrentLine = objReader.readLine()) != null) {
                try {
                    String[] split = strCurrentLine.split("\t");
                    int alb_id = Integer.parseInt(split[0]);
                    int pht_id = Integer.parseInt(split[1]);

                    Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt.executeUpdate(
                            "INSERT INTO [dbo].ALBUM_PHOTO(album_id,photo_id)" +
                                    "VALUES (" + alb_id + "," + pht_id + ")");
                } catch (SQLException sqlException) {
                    System.out.println("Error INSERTING ROW: " + strCurrentLine);
                }
            }
            System.out.println("Table ALBUM_PHOTO done.");
            objReader.close();

        } catch (IOException ioException) {
            System.out.println("Failed opening the file");
        }
    }

    private static void importAllData() {
        importAccountData();
        importCityData();
        importLocationData();
        importPrivacyData();
        importProfileData();
        importAlbumData();
        importPhotoData();
        importVideoData();
        importQuotesData();
        importWebsiteData();
        importWorksData();
        importEducationData();
        importLikesData();
        importInterestData();
        importProfileInterestData();
        importFriendRequestData();
        importAlbumVideoData();
        importAlbumPhotoData();
        importAlbumCommentData();
        importCommentVideoData();
        importBookmarkData();
        importEventData();
        importParticipantsData();

        System.out.println("All data imported successfully!");
    }

    private static void exportAccountData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "ACCOUNT.txt"));
            String strAttributes = "USERNAME\tPASSWORD";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].ACCOUNT");

            while (result.next()) {
                String username = result.getString("username");
                String password = result.getString("password");
                objWriter.write(username + "\t" + password);
                objWriter.newLine();
            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportAlbumData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "ALBUM.txt"));
            String strAttributes = "USER_ID\tNAME\tDESCRIPTION\tLOCATION\tLINK\tPRIVACY";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].ALBUM");

            while (result.next()) {
                String user_id = result.getString("user_id");
                String name = result.getString("name");
                String description = result.getString("description");
                String location = result.getString("location");
                String link = result.getString("link");
                String privacy = result.getString("privacy");

                objWriter.write(user_id + "\t" + name + "\t" + description + "\t" + location + "\t" + link + "\t" + privacy);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportAlbumCommentData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "ALBUM_COMMENT.txt"));
            String strAttributes = "USER_ID\tCOMMENT\tALBUM_ID";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].COMMENT_ALBUM");
            while (result.next()) {
                String user_id = result.getString("user_id");
                String comment = result.getString("comment");
                String album_id = result.getString("album_id");

                objWriter.write(user_id + "\t" + comment + "\t" + album_id);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportAlbumPhotoData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "ALBUM_PHOTO.txt"));
            String strAttributes = "ALBUM_ID\tPHOTO_ID";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].ALBUM_PHOTO");

            while (result.next()) {
                String album_id = result.getString("album_id");
                String photo_id = result.getString("photo_id");

                objWriter.write(album_id + "\t" + photo_id);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportAlbumVideoData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "ALBUM_VIDEO.txt"));
            String strAttributes = "ALBUM_ID\tPHOTO_ID";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].ALBUM_VIDEO");

            while (result.next()) {
                String album_id = result.getString("album_id");
                String video_id = result.getString("video_id");

                objWriter.write(album_id + "\t" + video_id);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportBookmarkData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "BOOKMARK.txt"));
            String strAttributes = "USER_ID\tLINK\tNAME\tCAPTION\tDESCRIPTION\tMESSAGE\tPRIVACY";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].BOOKMARK");

            while (result.next()) {
                String user_id = result.getString("user_id");
                String link = result.getString("link");
                String name = result.getString("name");
                String caption = result.getString("caption");
                String description = result.getString("description");
                String message = result.getString("message");
                String privacy = result.getString("privacy");

                objWriter.write(user_id + "\t" + link + "\t" + name + "\t" + caption + "\t" + description + "\t" + message + "\t" + privacy);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportCityData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "CITY.txt"));
            String strAttributes = "CITY";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].CITY");

            while (result.next()) {
                String city = result.getString("city");

                objWriter.write(city);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportCommentVideoData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "COMMENT_VIDEO.txt"));
            String strAttributes = "USER_ID\tCOMMENT\tVIDEO_ID";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].COMMENT_VIDEO");

            while (result.next()) {
                String user_id = result.getString("user_id");
                String comment = result.getString("comment");
                String video_id = result.getString("video_id");

                objWriter.write(user_id + "\t" + comment + "\t" + video_id);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportEducationData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "EDUCATION.txt"));
            String strAttributes = "USER_ID\tEDUCATION";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].EDUCATION");

            while (result.next()) {
                String user_id = result.getString("user_id");
                String education = result.getString("education");

                objWriter.write(user_id + "\t" + education);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportEventData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "EVENT.txt"));
            String strAttributes = "OWNER\tNAME\tDESCRIPTION\tSTART_TIME\tEND_TIME\tLOCATION\tVENUE\tPRIVACY";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].EVENT");

            while (result.next()) {
                String owner = result.getString("owner");
                String name = result.getString("name");
                String description = result.getString("description");
                String start_time = result.getTimestamp("start_time").toString().substring(0, 19);
                String end_time = result.getTimestamp("end_time").toString().substring(0, 19);
                String location = result.getString("location");
                String venue = result.getString("venue");
                String privacy = result.getString("privacy");

                objWriter.write(owner + "\t" + name + "\t" + description + "\t" + start_time + "\t" + end_time + "\t" + location + "\t" + venue + "\t" + privacy);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportFriendRequestData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "FRIEND_REQUEST.txt"));
            String strAttributes = "USER1_ID\tUSER2_ID\tPENDING\tIGNORE";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].FRIEND_REQUEST");

            while (result.next()) {
                String user1_id = result.getString("user1_id");
                String user2_id = result.getString("user2_id");
                String pending = result.getString("pending");
                String ignore = result.getString("ignore");

                objWriter.write(user1_id + "\t" + user2_id + "\t" + pending + "\t" + ignore);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportInterestData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "INTEREST.txt"));
            String strAttributes = "INTEREST";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].INTEREST");

            while (result.next()) {
                String interest = result.getString("interest");

                objWriter.write(interest);
                objWriter.newLine();
            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportLikesData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "LIKES.txt"));
            String strAttributes = "USER_ID\tPHOTO_ID";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].LIKES");

            while (result.next()) {
                String user_id = result.getString("user_id");
                String photo_id = result.getString("photo_id");

                objWriter.write(user_id + "\t" + photo_id);
                objWriter.newLine();
            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportLocationData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "LOCATION.txt"));
            String strAttributes = "STREET_NAME\tSTREET_NUMBER\tPOSTAL_CODE";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].LOCATION");

            while (result.next()) {
                String street_name = result.getString("street_name");
                String street_number = result.getString("street_number");
                String postal_code = result.getString("postal_code");

                objWriter.write(street_name + "\t" + street_number + "\t" + postal_code);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportLogFileData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "LOG_FILE.txt"));
            String strAttributes = "USER_ID\tOBJECT\tOBJECT_ID\tACTION";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].LOG_FILE");

            while (result.next()) {
                String profile_id = result.getString("profile_id");
                String object = result.getString("object");
                String object_id = result.getString("object_id");
                String action = result.getString("action");

                objWriter.write(profile_id + "\t" + object + "\t" + object_id + "\t" + action);
                objWriter.newLine();
            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportParticipantsData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "PARTICIPANTS.txt"));
            String strAttributes = "USER_ID\tEVENT_ID";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].PARTICIPANTS");

            while (result.next()) {
                String user_id = result.getString("user_id");
                String event_id = result.getString("event_id");

                objWriter.write(user_id + "\t" + event_id);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportPhotoData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "PHOTO.txt"));
            String strAttributes = "USER_ID\tDIRECTORY\tHEIGHT\tWIDTH\tLINK\tPRIVACY";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].PHOTO");

            while (result.next()) {
                String user_id = result.getString("user_id");
                String directory = result.getString("directory");
                String height = result.getString("height");
                String width = result.getString("width");
                String link = result.getString("link");
                String privacy = result.getString("privacy");

                objWriter.write(user_id + "\t" + directory + "\t" + height + "\t" + width + "\t" + link + "\t" + privacy);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportPrivacyData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "PRIVACY.txt"));
            String strAttributes = "PRIVACY";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].PRIVACY");

            while (result.next()) {
                String privacy = result.getString("privacy");
                objWriter.write(privacy);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportProfileData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "PROFILE.txt"));
            String strAttributes = "ACCOUNT_ID\tFIRST_NAME\tLAST_NAME\tLINK\tBIRTHDAY\tEMAIL\tHOMETOWN\tLOCATION\tGENDER\tVERIFIED";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].PROFILE");


            while (result.next()) {
                String account_id = result.getString("account_id");
                String first_name = result.getString("first_name");
                String last_name = result.getString("last_name");
                String link = result.getString("link");
                String birthdate = result.getString("birthdate");
                String email = result.getString("email");
                String hometown = result.getString("hometown");
                String location = result.getString("location");
                String gender = result.getString("gender");
                String verified = result.getString("verified");

                objWriter.write(account_id + "\t" + first_name + "\t" + last_name + "\t" + link + "\t" + birthdate + "\t" + email + "\t" + hometown + "\t" + location + "\t" + gender + "\t" + verified);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportProfileInterestData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "PROFILE_INTEREST.txt"));
            String strAttributes = "USER_ID\tINTEREST";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].PROFILE_INTEREST");

            while (result.next()) {
                String user_id = result.getString("user_id");
                String interest_id = result.getString("interest_id");

                objWriter.write(user_id + "\t" + interest_id);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportQuotesData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "QUOTES.txt"));
            String strAttributes = "USER_ID\tQUOTE";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].QUOTES");

            while (result.next()) {
                String user_id = result.getString("user_id");
                String quote = result.getString("quote");

                objWriter.write(user_id + "\t" + quote);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportVideoData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "VIDEO.txt"));
            String strAttributes = "USER_ID\tMESSAGE\tDESCRIPTION\tLENGTH\tPRIVACY";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].VIDEO");

            while (result.next()) {
                String user_id = result.getString("user_id");
                String message = result.getString("message");
                String description = result.getString("description");
                String length = result.getString("length");
                String privacy = result.getString("privacy");

                objWriter.write(user_id + "\t" + message + "\t" + description + "\t" + length + "\t" + privacy);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportWebsiteData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "WEBSITE.txt"));
            String strAttributes = "USER_ID\tWEBSITE";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].WEBSITE");

            while (result.next()) {
                String user_id = result.getString("user_id");
                String website = result.getString("website");

                objWriter.write(user_id + "\t" + website);
                objWriter.newLine();

            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void exportWorksData() {
        try {
            BufferedWriter objWriter = new BufferedWriter(new FileWriter(export_path + "WORKS.txt"));
            String strAttributes = "USER_ID\tWORK";
            objWriter.write(strAttributes);
            objWriter.newLine();
            Statement select = dbConnection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM [dbo].WORKS");

            while (result.next()) {
                String user_id = result.getString("user_id");
                String work = result.getString("work");

                objWriter.write(user_id + "\t" + work);
                objWriter.newLine();
            }

            objWriter.flush();
            objWriter.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void exportAllData() {
        exportAccountData();
        exportAlbumData();
        exportAlbumCommentData();
        exportAlbumPhotoData();
        exportAlbumVideoData();
        exportBookmarkData();
        exportCityData();
        exportCommentVideoData();
        exportEducationData();
        exportEventData();
        exportFriendRequestData();
        exportInterestData();
        exportLikesData();
        exportLocationData();
        exportLogFileData();
        exportParticipantsData();
        exportPhotoData();
        exportPrivacyData();
        exportProfileData();
        exportProfileInterestData();
        exportQuotesData();
        exportVideoData();
        exportWebsiteData();
        exportWorksData();
        System.out.println("All data exported successfully!");
    }
}
