package com.giuseppe;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Client
{
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty surname;
    private SimpleIntegerProperty age;

    public Client(String name, String surname, int age)
    {
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.age = new SimpleIntegerProperty(age);
    }

    public Client(int id, String name, String surname, int age)
    {
        this(name, surname, age);
        this.id = new SimpleIntegerProperty(id);
    }

    public int getId()
    {
        return id.get();
    }

    public SimpleIntegerProperty idProperty()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id.set(id);
    }

    public String getName()
    {
        return name.get();
    }

    public SimpleStringProperty nameProperty()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name.set(name);
    }

    public String getSurname()
    {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname.set(surname);
    }

    public int getAge()
    {
        return age.get();
    }

    public SimpleIntegerProperty ageProperty()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age.set(age);
    }


}
