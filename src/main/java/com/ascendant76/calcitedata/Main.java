package com.ascendant76.calcitedata;

import org.apache.calcite.util.Sources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Main {

    public static final String AGGREGATION_QUERY = """
            SELECT d.name, COUNT(*)
            FROM emps AS e JOIN depts AS d ON e.deptno = d.deptno
            GROUP BY d.name""";
    public static final String SELECT_QUERY = "select * from EMPS";

    public static void main(String[] args) throws ClassNotFoundException {
        final Properties info = new Properties();
        info.put(
                "model",
                Sources.of(Main.class.getResource("/sales/model.json")).file().getAbsolutePath());

        Class.forName("org.apache.calcite.jdbc.Driver");
        try (
                final Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
                final Statement statement = connection.createStatement();
                final ResultSet resultSet = statement.executeQuery(SELECT_QUERY)) {
            while (resultSet.next()) {
                System.out.print("EMPNO: " + resultSet.getString("EMPNO"));
                System.out.print(", NAME: " + resultSet.getString("NAME"));
                System.out.print(", DEPTNO: " + resultSet.getString("DEPTNO"));
                System.out.print(", GENDER: " + resultSet.getString("GENDER"));
                System.out.print(", CITY: " + resultSet.getString("CITY"));
                System.out.print(", EMPID: " + resultSet.getString("EMPID"));
                System.out.print(", AGE: " + resultSet.getInt("AGE"));
                System.out.print(", SLACKER: " + resultSet.getString("SLACKER"));
                System.out.print(", MANAGER: " + resultSet.getString("MANAGER"));
                System.out.println(", JOINEDAT: " + resultSet.getString("JOINEDAT"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (
                final Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
                final Statement statement = connection.createStatement();
                final ResultSet resultSet = statement.executeQuery(AGGREGATION_QUERY)
        ) {
            while (resultSet.next()) {
                System.out.print("NAME: " + resultSet.getString("NAME"));
                System.out.println(", EXPR$1: " + resultSet.getString("EXPR$1"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
