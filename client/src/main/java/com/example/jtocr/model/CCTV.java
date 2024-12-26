package com.example.jtocr.model;

import com.example.jtocr.connection.ClassConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CCTV {
    private static int CCTVNb;

    public static int getCCTVNb(int index) {
        CCTVNb = index + 1;
        return CCTVNb;
    }

    public static int getCCTVNb() {
        return CCTVNb;
    }
}
