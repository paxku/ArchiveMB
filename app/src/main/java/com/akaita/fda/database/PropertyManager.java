package com.akaita.fda.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mikel on 20/05/2015.
 */
public class PropertyManager {
    public static final String LAST_MODIFIED_DATE = "lastModified";

    public static long getLastModifiedDate(){
        final int NO_VALUE = -1;
        try {
            return getProperty(LAST_MODIFIED_DATE)==null?NO_VALUE:Long.parseLong(getProperty(LAST_MODIFIED_DATE).value);
        } catch (SQLException e) {
            e.printStackTrace();
            return NO_VALUE;
        }
    }

    public static void setLastModifiedDate(long date) throws SQLException {
        Property property = new Property(LAST_MODIFIED_DATE, String.valueOf(date));
        Dao<Property, String> propertyDao = DaoFactory.getInstance().getPropertyDao();
        propertyDao.createIfNotExists(property);
    }

    private static PreparedQuery<Property> propertyPreparedQuery = null;
    private static Property getProperty(String propertyName) throws SQLException {
        if (propertyPreparedQuery == null) {
            propertyPreparedQuery = createPropertyPreparedQuery();
        }
        propertyPreparedQuery.setArgumentHolderValue(0, propertyName);
        Dao<Property, String> propertyDao = DaoFactory.getInstance().getPropertyDao();
        List<Property> propertyList = propertyDao.query(propertyPreparedQuery);
        return propertyList.isEmpty()?null:propertyList.get(0);
    }

    private static PreparedQuery<Property> createPropertyPreparedQuery() throws SQLException {
        Dao<Property, String> propertyDao = DaoFactory.getInstance().getPropertyDao();
        // build our inner query for UserPost objects
        QueryBuilder<Property, String> propertyQb = propertyDao.queryBuilder();
        // just select the post-id field
        SelectArg propertySelectArg = new SelectArg();
        // you could also just pass in user1 here
        propertyQb.where().eq(Property.ID_FIELD_NAME, propertySelectArg);
        return propertyQb.prepare();
    }

}
