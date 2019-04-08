import java.sql.*;

public class CreateThingDB
{
    public CreateThingDB()
    {
        try
        {
            // Create a named constant for the URL.
            // NOTE: This value is specific for Java DB.
            final String DB_URL = "jdbc:derby:ThingDB;create=true";

            // Create a connection to the database.
            Connection conn = DriverManager.getConnection(DB_URL);

            // If the DB already exists, drop the tables.
            dropTables(conn);

            // Build the Thing table.
            buildThingTable(conn);

            // Build the Cart table.
            buildCartTable(conn);

            // Close the connection.
            conn.close();
        } catch (Exception e)
        {
            System.out.println("Error Creating the Thing Table");
            System.out.println(e.getMessage());
        }
    }
    /**
     * The dropTables method drops any existing
     * in case the database already exists.
     */
    public static void dropTables(Connection conn)
    {
        System.out.println("Checking for existing tables.");
        try
        {
            // Get a Statement object.
            Statement stmt = conn.createStatement();
            try
            {
                // Drop the Cart table.
                stmt.execute("DROP TABLE Cart");
                System.out.println("Cart table dropped.");
            } catch (SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }
            try
            {
                // Drop the Coffee table.
                stmt.execute("DROP TABLE Thing");
                System.out.println("Coffee table dropped.");
            } catch (SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }
        } catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    /**
     * The buildCoffeeTable method creates the
     * Coffee table and adds some rows to it.
     */
    public static void buildThingTable(Connection conn)
    {
        try
        {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE Thing (" +
                    "Description CHAR(25), " +
                    "ProdNum CHAR(10) NOT NULL PRIMARY KEY, " +
                    "Price DOUBLE " +
                    ")");

            // Insert row #1.
            stmt.execute("INSERT INTO Thing VALUES ( " +
                    "'Thing 1', " +
                    "'00-001', " +
                    "1.00 )");

            // Insert row #2.
            stmt.execute("INSERT INTO Thing VALUES ( " +
                "'Thing 2', " +
                "'00-002', " +
                "2.00 )");

            // Insert row #3.
            stmt.execute("INSERT INTO Thing VALUES ( " +
                    "'Thing 3', " +
                    "'00-004', " +
                    "3.00 )");

            System.out.println("Thing table created.");
        } catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    /**
     * The build Cart Table method creates the
     * Cart table and adds some rows to it.
     */
    public static void buildCartTable(Connection conn)
    {
        try
        {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE Cart" +
                    "( CartNumber CHAR(10) NOT NULL PRIMARY KEY, " +
                    "  ProdNum CHAR(10) NOT NULL FOREIGN KEY," +
                    "  Price DOUBLE NOT NULL FOREIGN KEY" +
                    "  Quantitiy INT" +
                    "  Address CHAR(25)," +
                    "  City CHAR(12)," +
                    "  State CHAR(2)," +
                    "  Zip CHAR(5) )");

            // Add some rows to the new table.

            stmt.executeUpdate("INSERT INTO Cart VALUES" +
                    "('001','00-002','2.00','2', 'Somewhere Drive', '1 Main Street'," +
                    " 'Somewhere', 'WI', '55555')");

            System.out.println("Cart table created.");
        } catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}