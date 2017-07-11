package br.com.caelum.pm73.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MinhaConexao {

	public static void main(String[] args) {
		try {
			// Class.forName("org.hsqldb.jdbc.JDBCDriver");
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Connection c = DriverManager.getConnection("jdbc:hsqldb:mem:MyDB", "SA", "");
			Statement stmt = c.createStatement();
			stmt.execute("CREATE TABLE USUARIO (id INT PRIMARY KEY, nome VARCHAR(64), senha VARCHAR(64), email VARCHAR(64))");
			
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM PUBLIC.USUARIO");
			while (rs.next()) {
				System.out.println(rs.getInt(0));
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getDouble(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
