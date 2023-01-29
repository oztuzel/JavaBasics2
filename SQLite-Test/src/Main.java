import java.sql.*;

public class Main {
    public static final String DB_NAME = "test.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:/Users/ahmetoztuzel/Documents/JavaProjects/SQLite-Test/" + DB_NAME;
    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";


    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING); Statement statement = conn.createStatement()) {

            statement.execute("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + " (" + COLUMN_NAME + " TEXT, " + COLUMN_PHONE + " INTEGER, "+ COLUMN_EMAIL + " TEXT" + " )");

            insertContact(statement, "Tim", 6543, "tim@email");
            insertContact(statement, "Ahmet", 1233, "ahmet@email");
            insertContact(statement, "Osman", 462343, "osman@email");

            statement.execute("UPDATE " + TABLE_CONTACTS + " SET " + COLUMN_PHONE + "=5566789"+ " WHERE " + COLUMN_NAME + "='Ahmet'");

            statement.execute("DELETE FROM " + TABLE_CONTACTS + " WHERE " + COLUMN_NAME + "='Tim'");

            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_CONTACTS);
            while(results.next()){
                System.out.println(results.getString(COLUMN_NAME) + " " +
                                    results.getInt(COLUMN_PHONE) + " " +
                                    results.getString(COLUMN_EMAIL));
            }
            results.close();

            //statement.close();
            //conn.close();
            // try with resources automatically closed these

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insertContact(Statement statement, String name , int phone, String email) throws SQLException {
        statement.execute("INSERT INTO " + TABLE_CONTACTS + " (" + COLUMN_NAME + ", " + COLUMN_PHONE+ ", " + COLUMN_EMAIL + " ) " + "VALUES('" + name + "', " + phone + ", '" +email + "')" );
    }


}












