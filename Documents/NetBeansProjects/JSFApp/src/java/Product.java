
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="obj")
@SessionScoped
public class Product {
    private String name;
    private int qty;
    private double price;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String add() {
        String res = "";
        Connection con = null;
        PreparedStatement pstmt= null;
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root123");
            pstmt = con.prepareStatement("select max(id) from test.Product");
            ResultSet rs = pstmt.executeQuery();
            int maxId = 0;
            if(rs.next()) {
                maxId = rs.getInt(1);
            }
            rs.close();
            pstmt = con.prepareStatement("insert into test.Product values(?,?,?,?)");
            pstmt.setInt(1, ++maxId);
            pstmt.setString(2, name);
            pstmt.setInt(3, qty);
            pstmt.setDouble(4, price);
            int i = pstmt.executeUpdate();
            if(i == 1)
               res = "success";
            else
               res = "failure";
        } catch (Exception e) {
            System.out.println("Something went wrong : "+e.getMessage());
        }finally{
            try {
                con.close();
                pstmt.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return res;
    }
   
}

