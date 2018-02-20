package com.giuseppe;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class ManageClients
{
    private static ManageClients manageClients = null;

    private ManageClients()
    {

    }

    public static ManageClients getManageClients()
    {
        if(manageClients == null)
        {
            manageClients = new ManageClients();
        }
        return manageClients;
    }

    public void saveClient()
    {
        Path path = Paths.get("client.txt");
        try(BufferedWriter bw = Files.newBufferedWriter(path))
        {
            List<Client> clientList = Datasource.getDatasource().getClientsList();

            Iterator<Client> iterator = clientList.iterator();
            while(iterator.hasNext())
            {
                Client client = iterator.next();
                bw.write(String.valueOf(client.getId()) + " ");
                bw.write(client.getName() + " ");
                bw.write(client.getSurname() + " ");
                bw.write(String.valueOf(client.getAge()) + "\n");
            }
        }
        catch(IOException e)
        {
            System.err.println("Error: " + e.getMessage());
        }

    }
}
