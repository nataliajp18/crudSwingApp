package org.example.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static String url = "jdbc:mysql://localhost:3306/project";
    private static String user = "root";
    private static String password = "root";

    private static Connection myCon;

    public static Connection getInstance() throws SQLException {
        if(myCon == null){
            myCon = DriverManager.getConnection(url,user,password);
        }
        return myCon;
    }

}
