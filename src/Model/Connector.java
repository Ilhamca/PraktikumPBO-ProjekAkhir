/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static Util.ColorSout.*;

/**
 *
 * @author Iam
 */
public class Connector {

    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    private static final String jdbc_driver = "com.mysql.cj.jdbc.Driver";
    private static final String nama_db = "ppbo_projekakhir";
    private static final String url_db = "jdbc:mysql://localhost:3306/" + nama_db;
    private static final String username_db = "root";
    private static final String password_db = "";

    static Connection conn;
    
    // Mencoba menghubungkan program kita dengan ke database MySQL.
    public static Connection Connect() {
        try {
            // 1. Register driver yang akan dipakai
            Class.forName(jdbc_driver);

            // 2. Buat koneksi ke database
            conn = DriverManager.getConnection(url_db, username_db, password_db);

            // 3. Menampilkan pesan "Connection Success" jika berhasil terhubung ke database.
            System.out.println(BLUE + "MySQL Connected!" + RESET);
        } catch (ClassNotFoundException | SQLException exception) {
            // Menampilkan pesan error ketika MySQL gagal terhubung.
            System.out.println(RED + "Connection Failed: " + exception.getLocalizedMessage() + RESET);
        }
        return conn;
    }
}
