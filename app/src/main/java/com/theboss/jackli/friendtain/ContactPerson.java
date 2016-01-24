package com.theboss.jackli.friendtain;

/**
 * Created by jackli on 2016-01-23.
 * <p/>
 * Class that represents the data for a phone contact.
 */

public class ContactPerson
{
    private static int ID_COUNTER = 0;

    private int id;
    private String name;
    private String phoneNumber;

    public ContactPerson(String aInName, String aInPhoneNum)
    {
        id = ID_COUNTER++;

        name = aInName;
        phoneNumber = aInPhoneNum;
    }

    public long getId()
    {
        return id;
    }

    public void setId(int aInID)
    {
        id = aInID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String aInName)
    {
        name = aInName;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String aInPhoneNum)
    {
        phoneNumber = aInPhoneNum;
    }

}
