import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/*
  InventoryGUI (Swing) - connects to AdminRole and EmployeeRole classes.
  Requires existing AdminRole, EmployeeRole, EmployeeUser, Product, CustomerProduct classes.
*/

public class InventoryGUI {
    private final AdminRole admin;
    private final EmployeeRole employee;

    private final JFrame frame;
    private final JTabbedPane tabbedPane;

    // Admin components
    private final JTextField empIdField = new JTextField(10);
    private final JTextField empNameField = new JTextField(12);
    private final JTextField empEmailField = new JTextField(12);
    private final JTextField empAddressField = new JTextField(12);
    private final JTextField empPhoneField = new JTextField(12);
    private final DefaultTableModel empTableModel = new DefaultTableModel(new String[]{"ID","Name","Email","Address","Phone"},0);
    private final JTable empTable = new JTable(empTableModel);

    // Employee components
    private final JTextField prodIdField = new JTextField(10);
    private final JTextField prodNameField = new JTextField(12);
    private final JTextField prodManuField = new JTextField(12);
    private final JTextField prodSuppField = new JTextField(12);
    private final JTextField prodQtyField = new JTextField(6);
    private final JTextField prodPriceField = new JTextField(8);
    private final DefaultTableModel prodTableModel = new DefaultTableModel(new String[]{"ID","Name","Manufacturer","Supplier","Qty","Price"},0);
    private final JTable prodTable = new JTable(prodTableModel);

    // Purchase/Return components
    private final JTextField purchaseSSN = new JTextField(12);
    private final JTextField purchaseProdID = new JTextField(10);
    private final JTextField purchaseDate = new JTextField(10); // dd-MM-yyyy
    private final JTextField returnSSN = new JTextField(12);
    private final JTextField returnProdID = new JTextField(10);
    private final JTextField returnPurchaseDate = new JTextField(10);
    private final JTextField returnDate = new JTextField(10);
    private final JTextField paymentSSN = new JTextField(12);
    private final JTextField paymentPurchaseDate = new JTextField(10);

    private final DefaultTableModel purchasesModel = new DefaultTableModel(new String[]{"CustomerSSN","ProductID","PurchaseDate","Paid"},0);
    private final JTable purchasesTable = new JTable(purchasesModel);

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public InventoryGUI() {
        admin = new AdminRole();
        employee = new EmployeeRole();

        frame = new JFrame("Inventory Management System (Admin & Employee)");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(900, 650);
        frame.setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        tabbedPane.add("Admin", createAdminPanel());
        tabbedPane.add("Employee", createEmployeePanel());

        frame.add(tabbedPane);
        addWindowCloseHandler();

        refreshAllTables();

        frame.setVisible(true);
    }

    private JPanel createAdminPanel(){
        JPanel panel = new JPanel(new BorderLayout(8,8));
        panel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

        // Top -> Add Employee Form
        JPanel top = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.gridx = 0; c.gridy = 0; top.add(new JLabel("Employee ID (E####):"), c);
        c.gridx = 1; top.add(empIdField, c);
        c.gridx = 0; c.gridy++; top.add(new JLabel("Name:"), c);
        c.gridx = 1; top.add(empNameField, c);
        c.gridx = 0; c.gridy++; top.add(new JLabel("Email:"), c);
        c.gridx = 1; top.add(empEmailField, c);
        c.gridx = 0; c.gridy++; top.add(new JLabel("Address:"), c);
        c.gridx = 1; top.add(empAddressField, c);
        c.gridx = 0; c.gridy++; top.add(new JLabel("Phone (01XXXXXXXXX):"), c);
        c.gridx = 1; top.add(empPhoneField, c);

        JButton addEmpBtn = new JButton("Add Employee");
        addEmpBtn.addActionListener(e -> addEmployeeAction());
        c.gridx=0; c.gridy++; c.gridwidth=2; top.add(addEmpBtn,c);

        JButton logoutBtn = new JButton("Logout (Save)");
        logoutBtn.addActionListener(e -> {
            admin.logout();
            JOptionPane.showMessageDialog(frame, "Admin data saved.");
            refreshAllTables();
        });
        c.gridx=0; c.gridy++; top.add(logoutBtn,c);

        panel.add(top, BorderLayout.NORTH);

        // Center -> Employee Table + Remove
        JPanel center = new JPanel(new BorderLayout(6,6));
        empTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp = new JScrollPane(empTable);
        center.add(sp, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton refreshBtn = new JButton("Refresh List");
        refreshBtn.addActionListener(e -> refreshEmployeesTable());
        JButton removeBtn = new JButton("Remove Selected");
        removeBtn.addActionListener(e -> removeSelectedEmployee());
        bottom.add(refreshBtn);
        bottom.add(removeBtn);
        center.add(bottom, BorderLayout.SOUTH);

        panel.add(center, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createEmployeePanel(){
        JPanel panel = new JPanel(new BorderLayout(8,8));
        panel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

        // Top -> Add Product form
        JPanel addP = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3,3,3,3);
        c.gridx=0; c.gridy=0; addP.add(new JLabel("Product ID:"), c); c.gridx=1; addP.add(prodIdField,c);
        c.gridx=0; c.gridy++; addP.add(new JLabel("Product Name:"), c); c.gridx=1; addP.add(prodNameField,c);
        c.gridx=0; c.gridy++; addP.add(new JLabel("Manufacturer:"), c); c.gridx=1; addP.add(prodManuField,c);
        c.gridx=0; c.gridy++; addP.add(new JLabel("Supplier:"), c); c.gridx=1; addP.add(prodSuppField,c);
        c.gridx=0; c.gridy++; addP.add(new JLabel("Quantity:"), c); c.gridx=1; addP.add(prodQtyField,c);
        c.gridx=0; c.gridy++; addP.add(new JLabel("Price:"), c); c.gridx=1; addP.add(prodPriceField,c);

        JButton addProdBtn = new JButton("Add Product");
        addProdBtn.addActionListener(e -> addProductAction());
        c.gridx=0; c.gridy++; c.gridwidth=2; addP.add(addProdBtn,c);

        JButton prodLogout = new JButton("Logout (Save)");
        prodLogout.addActionListener(e -> {
            employee.logout();
            JOptionPane.showMessageDialog(frame, "Employee data saved.");
            refreshAllTables();
        });
        c.gridy++; addP.add(prodLogout,c);

        panel.add(addP, BorderLayout.NORTH);

        // Center -> Products table and Purchases table in split pane
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JScrollPane spProd = new JScrollPane(prodTable);
        prodTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        split.setTopComponent(spProd);

        // Purchases table and actions
        JPanel purchasePanel = new JPanel(new BorderLayout(6,6));
        JScrollPane spPurchases = new JScrollPane(purchasesTable);
        purchasePanel.add(spPurchases, BorderLayout.CENTER);

        JPanel purchaseActions = new JPanel(new GridBagLayout());
        GridBagConstraints p = new GridBagConstraints();
        p.insets = new Insets(3,3,3,3);
        p.gridx=0; p.gridy=0; purchaseActions.add(new JLabel("Purchase - CustomerSSN:"),p); p.gridx=1; purchaseActions.add(purchaseSSN,p);
        p.gridx=2; purchaseActions.add(new JLabel("Product ID:"),p); p.gridx=3; purchaseActions.add(purchaseProdID,p);
        p.gridx=4; purchaseActions.add(new JLabel("Date dd-MM-yyyy:"),p); p.gridx=5; purchaseActions.add(purchaseDate,p);

        p.gridx=0; p.gridy++; JButton purchaseBtn = new JButton("Make Purchase");
        purchaseBtn.addActionListener(e -> makePurchaseAction());
        purchaseActions.add(purchaseBtn, p);

        // Return inputs
        p.gridx=0; p.gridy++; purchaseActions.add(new JLabel("Return - SSN:"),p); p.gridx=1; purchaseActions.add(returnSSN,p);
        p.gridx=2; purchaseActions.add(new JLabel("ProdID:"),p); p.gridx=3; purchaseActions.add(returnProdID,p);
        p.gridx=4; purchaseActions.add(new JLabel("Purchase Date:"),p); p.gridx=5; purchaseActions.add(returnPurchaseDate,p);
        p.gridx=6; purchaseActions.add(new JLabel("Return Date:"),p); p.gridx=7; purchaseActions.add(returnDate,p);
        p.gridx=0; p.gridy++; JButton returnBtn = new JButton("Return Product");
        returnBtn.addActionListener(e -> returnProductAction());
        purchaseActions.add(returnBtn,p);

        // Apply payment
        p.gridx=0; p.gridy++; purchaseActions.add(new JLabel("Apply Payment - SSN:"),p); p.gridx=1; purchaseActions.add(paymentSSN,p);
        p.gridx=2; purchaseActions.add(new JLabel("Purchase Date:"),p); p.gridx=3; purchaseActions.add(paymentPurchaseDate,p);
        p.gridx=4; JButton applyPaymentBtn = new JButton("Apply Payment");
        applyPaymentBtn.addActionListener(e -> applyPaymentAction());
        purchaseActions.add(applyPaymentBtn,p);

        purchasePanel.add(purchaseActions, BorderLayout.SOUTH);

        split.setBottomComponent(purchasePanel);
        split.setDividerLocation(260);

        panel.add(split, BorderLayout.CENTER);

        // South -> refresh buttons
        JPanel bottom = new JPanel();
        JButton refreshProd = new JButton("Refresh Products");
        refreshProd.addActionListener(e -> refreshProductsTable());
        JButton refreshPurch = new JButton("Refresh Purchases");
        refreshPurch.addActionListener(e -> refreshPurchasesTable());
        bottom.add(refreshProd);
        bottom.add(refreshPurch);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    // ---------- Actions & Helpers ----------

    private void addEmployeeAction(){
        String id = empIdField.getText().trim();
        String name = empNameField.getText().trim();
        String email = empEmailField.getText().trim();
        String address = empAddressField.getText().trim();
        String phone = empPhoneField.getText().trim();

        if(id.isEmpty() || name.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Employee ID and Name are required.");
            return;
        }
        // Basic format validations (same as console)
        if(!id.matches("^E\\d{4}$")) { JOptionPane.showMessageDialog(frame, "Employee ID must be E followed by 4 digits."); return; }
        if(!email.isEmpty() && !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) { JOptionPane.showMessageDialog(frame,"Invalid email format."); return; }
        if(!phone.isEmpty() && !phone.matches("^01\\d{9}$")) { JOptionPane.showMessageDialog(frame,"Phone must be 11 digits starting with 01."); return; }

        admin.addEmployee(id,name,email,address,phone);
        refreshEmployeesTable();
        clearEmployeeFields();
    }

    private void clearEmployeeFields(){
        empIdField.setText("");
        empNameField.setText("");
        empEmailField.setText("");
        empAddressField.setText("");
        empPhoneField.setText("");
    }

    private void removeSelectedEmployee(){
        int sel = empTable.getSelectedRow();
        if(sel < 0) { JOptionPane.showMessageDialog(frame, "Select an employee first."); return; }
        String id = (String) empTableModel.getValueAt(sel,0);
        int res = JOptionPane.showConfirmDialog(frame, "Delete employee " + id + " ?","Confirm", JOptionPane.YES_NO_OPTION);
        if(res==JOptionPane.YES_OPTION){
            admin.removeEmployee(id);
            refreshEmployeesTable();
        }
    }

    private void addProductAction(){
        String id = prodIdField.getText().trim();
        String name = prodNameField.getText().trim();
        String manu = prodManuField.getText().trim();
        String supp = prodSuppField.getText().trim();
        String qtyS = prodQtyField.getText().trim();
        String priceS = prodPriceField.getText().trim();

        if(id.isEmpty() || name.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Product ID and Name required.");
            return;
        }
        int qty;
        float price;
        try {
            qty = Integer.parseInt(qtyS);
            price = Float.parseFloat(priceS);
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(frame, "Quantity must be integer and price numeric.");
            return;
        }

        // EmployeeRole.addProduct signature in your code previously required price param
        employee.addProduct(id, name, manu, supp, qty, price);
        refreshProductsTable();
        clearProductFields();
    }

    private void clearProductFields(){
        prodIdField.setText("");
        prodNameField.setText("");
        prodManuField.setText("");
        prodSuppField.setText("");
        prodQtyField.setText("");
        prodPriceField.setText("");
    }

    private void makePurchaseAction(){
        String ssn = purchaseSSN.getText().trim();
        String pid = purchaseProdID.getText().trim();
        String dateS = purchaseDate.getText().trim();
        if(ssn.isEmpty() || pid.isEmpty() || dateS.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Fill SSN, Product ID, and Date.");
            return;
        }
        LocalDate d;
        try { d = LocalDate.parse(dateS, dtf); }
        catch (DateTimeParseException ex){ JOptionPane.showMessageDialog(frame, "Date format dd-MM-yyyy"); return; }

        boolean ok = employee.purchaseProduct(ssn, pid, d);
        JOptionPane.showMessageDialog(frame, ok ? "Purchase completed." : "Purchase failed.");
        refreshProductsTable();
        refreshPurchasesTable();
    }

    private void returnProductAction(){
        String ssn = returnSSN.getText().trim();
        String pid = returnProdID.getText().trim();
        String purchaseS = returnPurchaseDate.getText().trim();
        String returnS = returnDate.getText().trim();
        if(ssn.isEmpty() || pid.isEmpty() || purchaseS.isEmpty() || returnS.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Fill all return fields.");
            return;
        }
        LocalDate p, r;
        try {
            p = LocalDate.parse(purchaseS, dtf);
            r = LocalDate.parse(returnS, dtf);
        } catch (DateTimeParseException ex){
            JOptionPane.showMessageDialog(frame, "Date format dd-MM-yyyy");
            return;
        }

        double refund = employee.returnProduct(ssn, pid, p, r);
        if(refund == -1) {
            JOptionPane.showMessageDialog(frame, "Return failed.");
        } else {
            JOptionPane.showMessageDialog(frame, "Returned. Refund: " + refund);
        }
        refreshProductsTable();
        refreshPurchasesTable();
    }

    private void applyPaymentAction(){
        String ssn = paymentSSN.getText().trim();
        String pd = paymentPurchaseDate.getText().trim();
        if(ssn.isEmpty() || pd.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Fill SSN and purchase date.");
            return;
        }
        LocalDate d;
        try { d = LocalDate.parse(pd, dtf); } catch (DateTimeParseException ex){ JOptionPane.showMessageDialog(frame, "Date format dd-MM-yyyy"); return; }

        boolean done = employee.applyPayment(ssn, d);
        JOptionPane.showMessageDialog(frame, done ? "Payment applied." : "Payment failed.");
        refreshPurchasesTable();
    }

    private void refreshEmployeesTable(){
        empTableModel.setRowCount(0);
        EmployeeUser[] list = admin.getListOfEmployees();
        if(list == null || list.length == 0) return;
        for(EmployeeUser e : list){
            if(e == null) continue;
            empTableModel.addRow(new Object[]{e.getSearchKey(), safeField(e.lineRepresentation(), 1), safeField(e.lineRepresentation(), 2), safeField(e.lineRepresentation(), 3), safeField(e.lineRepresentation(), 4)});
        }
    }

    private String safe(EmployeeUser e){ return e==null? "": e.getSearchKey(); }
    // helper to split lineRepresentation (employee) quickly (not pretty but works)
    private String safeField(String line, int idx){
        try {
            String[] parts = line.split(",", -1);
            return parts.length > idx ? parts[idx] : "";
        } catch (Exception ex){ return ""; }
    }

    private void refreshProductsTable(){
        prodTableModel.setRowCount(0);
        Product[] list = employee.getListOfProducts();
        if(list == null) return;
        for(Product p : list){
            if(p==null) continue;
            prodTableModel.addRow(new Object[]{p.getSearchKey(), p.lineRepresentation().split(",",-1)[1], p.lineRepresentation().split(",",-1)[2], p.lineRepresentation().split(",",-1)[3], p.getQuantity(), p.getPrice()});
        }
    }

    private void refreshPurchasesTable(){
        purchasesModel.setRowCount(0);
        CustomerProduct[] list = employee.getListOfPurchasingOperations();
        if(list == null) return;
        for(CustomerProduct cp : list){
            if(cp==null) continue;
            purchasesModel.addRow(new Object[]{ cp.getCustomerSSN(), cp.getProductID(), cp.getPurchaseDate().format(dtf), cp.isPaid()});
        }
    }

    private void refreshAllTables(){
        refreshEmployeesTable();
        refreshProductsTable();
        refreshPurchasesTable();
    }

    private void addWindowCloseHandler(){
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int res = JOptionPane.showConfirmDialog(frame, "Save data and exit?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
                if(res == JOptionPane.YES_OPTION){
                    try { admin.logout(); } catch (Exception ex) { /* ignore */ }
                    try { employee.logout(); } catch (Exception ex) { /* ignore */ }
                    frame.dispose();
                    System.exit(0);
                } else if(res == JOptionPane.NO_OPTION){
                    frame.dispose();
                    System.exit(0);
                }
                // else cancel -> do nothing
            }
        });
    }

    // ---------- main ----------
    public static void main(String[] args){
        SwingUtilities.invokeLater(InventoryGUI::new);
    }
}
