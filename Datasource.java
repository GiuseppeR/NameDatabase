package com.giuseppe;

import javafx.collections.FXCollections;

import java.sql.*;
import java.util.List;

public class Datasource
{
    private static Datasource datasource = null;

    private static final String DB_NAME = "address.db";
    private static final String CONNECTION_STRING =
            "jdbc:sqlite:D:\\IntelliJProjects\\AddressDatabase\\src\\com\\giuseppe\\" + DB_NAME;

    private static final String TABLE_CONTACTS = "contacts";
    private static final String COLUMN_CONTACTS_ID = "_id";
    private static final String COLUMN_CONTACTS_NAME = "name";
    private static final String COLUMN_CONTACTS_SURNAME = "surname";
    private static final String COLUMN_CONTACTS_AGE = "age";
    private static final int COLUMN_INDEX_CONTACTS_ID = 1;
    private static final int COLUMN_INDEX_CONTACTS_NAME = 2;
    private static final int COLUMN_INDEX_CONTACTS_SURNAME = 3;
    private static final int COLUMN_INDEX_CONTACTS_AGE = 4;

    private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS +
            " (" + COLUMN_CONTACTS_ID + " INTEGER PRIMARY KEY, " + COLUMN_CONTACTS_NAME + " TEXT NOT NULL, " +
            COLUMN_CONTACTS_SURNAME + " TEXT NOT NULL, " + COLUMN_CONTACTS_AGE + " INTEGER NOT NULL)";

    Connection conn;

    private Datasource()
    {

    }

    public static Datasource getDatasource()
    {
        if(datasource == null)
        {
            datasource = new Datasource();
            return datasource;
        }
        else
        {
            return datasource;
        }
    }

    public boolean open()
    {
        try
        {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        }
        catch(SQLException e)
        {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean create()
    {
        try(Statement statement = conn.createStatement())
        {
            statement.execute(CREATE_TABLE_CONTACTS);
            return true;
        }
        catch(SQLException e)
        {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public void close()
    {
        try
        {
            conn.close();
        }
        catch(SQLException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean addContact(String name, String surname, int age)
    {
        String sql = "INSERT INTO " + TABLE_CONTACTS + " (" +
                     COLUMN_CONTACTS_NAME + ", " +
                     COLUMN_CONTACTS_SURNAME + ", " +
                     COLUMN_CONTACTS_AGE + ") VALUES (\"" +
                     name + "\", \"" + surname + "\", " + age + ")";

        try(Statement statement = conn.createStatement())
        {
            statement.execute(sql);
            return true;
        }
        catch(SQLException e)
        {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public void deleteContacts(int id)
    {
        String sql = "DELETE FROM " + TABLE_CONTACTS +
                     " WHERE " + COLUMN_CONTACTS_ID + " = " + id;

        try(Statement statement = conn.createStatement())
        {
            statement.execute(sql);
        }
        catch(SQLException e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }


    public List<Client> getClientsList()
    {
        List<Client> clientList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM " + TABLE_CONTACTS;
        int id;
        String name;
        String surname;
        int age;

        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(sql))
        {
            while(results.next())
            {
                id = results.getInt(COLUMN_INDEX_CONTACTS_ID);
                name = results.getString(COLUMN_INDEX_CONTACTS_NAME);
                surname = results.getString(COLUMN_INDEX_CONTACTS_SURNAME);
                age = results.getInt(COLUMN_INDEX_CONTACTS_AGE);

                Client client = new Client(id, name, surname, age);
                clientList.add(client);
            }

            return clientList;
        }
        catch(SQLException e)
        {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

}
