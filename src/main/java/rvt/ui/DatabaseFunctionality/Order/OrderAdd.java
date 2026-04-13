package rvt.ui.DatabaseFunctionality.Order;

import java.awt.GridLayout;
import java.math.BigDecimal;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import rvt.util.FieldFormatting;
import rvt.util.TextFormatting;
import rvt.util.ButtonFormatting;
import rvt.util.ErrorHandler;
import rvt.util.UIColors;

import rvt.service.OrderService;
import rvt.service.StatusService;
import rvt.service.EmployeeService;
import rvt.service.ProductService;
import rvt.model.Order;
import rvt.model.Product;
import rvt.model.Status;
import rvt.model.Employee;;

public class OrderAdd extends JPanel {
    
    OrderService service = new OrderService();
    StatusService statService = new StatusService();
    EmployeeService employeeService = new EmployeeService();
    ProductService productService = new ProductService();

    TextFormatting text = new TextFormatting();
    FieldFormatting field = new FieldFormatting();
    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();
    
    private JTextField idField = field.size2();
    private JTextField totalField = field.size2();
    private JTextField amountField = field.size2();
    private JButton cnfrmBtn = btn.tableOption("Akceptēt", color.button2());

    public OrderAdd(){
        setLayout(new GridLayout(7, 2, 80, 20));

        JComboBox<Status> statusBox = new JComboBox<>();
        JComboBox<Employee> employeeBox = new JComboBox<>();
        JComboBox<Product> productBox = new JComboBox<>();

        try {
            for (Status s : statService.getAllStatuses()) {
                statusBox.addItem(s);
            }

            for (Employee e : employeeService.getAllEmployees()) {
                employeeBox.addItem(e);
            }

            for (Product p : productService.getAllProducts()) {
                productBox.addItem(p);
            }
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot", e);
        }

        add(text.text2("Id:"));
        add(idField);
        add(text.text2("summa:"));
        add(totalField);
        add(text.text2("gab:"));
        add(amountField);
        add(text.text2("statuss:"));
        add(statusBox);
        add(text.text2("darbinieks:"));
        add(employeeBox);
        add(text.text2("produkts:"));
        add(productBox);

        cnfrmBtn.addActionListener(e -> {
            try {
                Status sStatus = (Status) statusBox.getSelectedItem();
                int StatusId = sStatus.getId();

                Employee sEmpoloyee = (Employee) employeeBox.getSelectedItem();
                int EmployeeId = sEmpoloyee.getId();

                Product sProduct = (Product) productBox.getSelectedItem();
                int ProductId = sProduct.getId();

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                String sqliteDateTime = now.format(formatter);

                Order order = new Order(Integer.valueOf(idField.getText()),
                                        sqliteDateTime,
                                        new BigDecimal(totalField.getText()),
                                        Integer.valueOf(amountField.getText()),
                                        StatusId,
                                        EmployeeId,
                                        ProductId
                                    );
                service.addOrder(order);
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda saglabājot kategoriju", ex);
            }
        });

        add(cnfrmBtn);
    }
}
