package jhosna25wh5a0511;

import java.sql.*;
import java.util.Scanner;

public class EmployeeCRUD {

    // ---- DATABASE CONNECTION (CORRECTED) ----
    static final String URL =
            "jdbc:mysql://localhost:3306/employee?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASSWORD = "jhosna@16";   // put your mysql password if different

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            // Load MySQL JDBC Driver (VERY IMPORTANT)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create connection
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to Database Successfully!");

            while (true) {
                System.out.println("\n1. Insert");
                System.out.println("2. View");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");

                int ch = sc.nextInt();

                switch (ch) {

                    // -------- INSERT --------
                    case 1:
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Salary: ");
                        double sal = sc.nextDouble();

                        PreparedStatement ps1 =
                                con.prepareStatement("INSERT INTO emp VALUES (?, ?, ?)");

                        ps1.setInt(1, id);
                        ps1.setString(2, name);
                        ps1.setDouble(3, sal);
                        ps1.executeUpdate();

                        System.out.println("✔ Record Inserted Successfully!");
                        break;

                    // -------- VIEW --------
                    case 2:
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM emp");

                        System.out.println("\nID\tName\tSalary");
                        System.out.println("--------------------------");

                        while (rs.next()) {
                            System.out.println(
                                    rs.getInt("id") + "\t" +
                                    rs.getString("name") + "\t" +
                                    rs.getDouble("salary"));
                        }
                        break;

                    // -------- UPDATE --------
                    case 3:
                        System.out.print("Enter ID to Update: ");
                        int uid = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter New Name: ");
                        String uname = sc.nextLine();

                        System.out.print("Enter New Salary: ");
                        double usal = sc.nextDouble();

                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE emp SET name=?, salary=? WHERE id=?");

                        ps2.setString(1, uname);
                        ps2.setDouble(2, usal);
                        ps2.setInt(3, uid);

                        int rows = ps2.executeUpdate();

                        if (rows > 0)
                            System.out.println("✔ Record Updated!");
                        else
                            System.out.println("❌ ID Not Found!");
                        break;

                    // -------- DELETE --------
                    case 4:
                        System.out.print("Enter ID to Delete: ");
                        int did = sc.nextInt();

                        PreparedStatement ps3 =
                                con.prepareStatement("DELETE FROM emp WHERE id=?");

                        ps3.setInt(1, did);

                        int d = ps3.executeUpdate();

                        if (d > 0)
                            System.out.println("✔ Record Deleted!");
                        else
                            System.out.println("❌ ID Not Found!");
                        break;

                    // -------- EXIT --------
                    case 5:
                        con.close();
                        System.out.println("Connection Closed.");
                        System.exit(0);

                    default:
                        System.out.println("Invalid Choice!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}