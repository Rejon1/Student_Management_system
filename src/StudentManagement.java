import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StudentManagement extends JFrame {

    JTextField txtName, txtDepartment, txtCGPA, txtSearch;

    JButton addBtn, updateBtn, deleteBtn, searchBtn, loadBtn;

    JTable table;

    DefaultTableModel model;

    public StudentManagement() {

        setTitle("Student Management System");

        setSize(800, 500);

        setLayout(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel l1 = new JLabel("Name");

        l1.setBounds(20, 20, 100, 30);

        add(l1);

        txtName = new JTextField();

        txtName.setBounds(120, 20, 150, 30);

        add(txtName);

        JLabel l2 = new JLabel("Department");

        l2.setBounds(20, 60, 100, 30);

        add(l2);

        txtDepartment = new JTextField();

        txtDepartment.setBounds(120, 60, 150, 30);

        add(txtDepartment);

        JLabel l3 = new JLabel("CGPA");

        l3.setBounds(20, 100, 100, 30);

        add(l3);

        txtCGPA = new JTextField();

        txtCGPA.setBounds(120, 100, 150, 30);

        add(txtCGPA);

        addBtn = new JButton("Add");

        addBtn.setBounds(20, 160, 100, 30);

        add(addBtn);

        updateBtn = new JButton("Update");

        updateBtn.setBounds(130, 160, 100, 30);

        add(updateBtn);

        deleteBtn = new JButton("Delete");

        deleteBtn.setBounds(240, 160, 100, 30);

        add(deleteBtn);

        txtSearch = new JTextField();

        txtSearch.setBounds(400, 20, 150, 30);

        add(txtSearch);

        searchBtn = new JButton("Search");

        searchBtn.setBounds(570, 20, 100, 30);

        add(searchBtn);

        loadBtn = new JButton("Load Data");

        loadBtn.setBounds(570, 60, 120, 30);

        add(loadBtn);

        model = new DefaultTableModel();

        model.addColumn("ID");

        model.addColumn("Name");

        model.addColumn("Department");

        model.addColumn("CGPA");

        table = new JTable(model);

        JScrollPane sp = new JScrollPane(table);

        sp.setBounds(20, 220, 740, 200);

        add(sp);

        addBtn.addActionListener(e -> addStudent());

        loadBtn.addActionListener(e -> loadData());

        searchBtn.addActionListener(e -> searchStudent());

        deleteBtn.addActionListener(e -> deleteStudent());

        updateBtn.addActionListener(e -> updateStudent());

        setVisible(true);

    }

    void addStudent() {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "insert into students(name,department,cgpa) values(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, txtName.getText());

            ps.setString(2, txtDepartment.getText());

            ps.setDouble(3, Double.parseDouble(txtCGPA.getText()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Student Added");

            loadData();

        } catch (Exception e) {

            System.out.println(e);

        }

    }

    void loadData() {

        try {

            model.setRowCount(0);

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from students");

            while (rs.next()) {

                model.addRow(new Object[]{
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDouble(4)
                });

            }

        } catch (Exception e) {

            System.out.println(e);

        }

    }

    void searchStudent() {

        try {

            model.setRowCount(0);

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "select * from students where id=?");

            ps.setInt(1,
                    Integer.parseInt(txtSearch.getText()));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDouble(4)
                });

            }

        } catch (Exception e) {

            System.out.println(e);

        }

    }

    void deleteStudent() {

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "delete from students where id=?");

            ps.setInt(1,
                    Integer.parseInt(txtSearch.getText()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Deleted");

            loadData();

        } catch (Exception e) {

            System.out.println(e);

        }

    }

    void updateStudent() {

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "update students set name=?,department=?,cgpa=? where id=?");

            ps.setString(1, txtName.getText());

            ps.setString(2, txtDepartment.getText());

            ps.setDouble(3, Double.parseDouble(txtCGPA.getText()));

            ps.setInt(4,
                    Integer.parseInt(txtSearch.getText()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Updated");

            loadData();

        } catch (Exception e) {

            System.out.println(e);

        }

    }

    public static void main(String[] args) {

        new StudentManagement();

    }

}