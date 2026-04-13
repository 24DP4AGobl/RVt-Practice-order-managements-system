package rvt.ui.DatabaseFunctionality.Order;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.GridLayout;

import rvt.util.FieldFormatting;
import rvt.util.TextFormatting;
import rvt.util.ButtonFormatting;
import rvt.util.ErrorHandler;
import rvt.util.UIColors;

import rvt.service.OrderService;
import rvt.service.StatusService;
import rvt.model.Order;
import rvt.model.Status;


public class OrderEdit extends JPanel{
        
    StatusService statService = new StatusService();
    OrderService service = new OrderService();

    TextFormatting text = new TextFormatting();
    FieldFormatting field = new FieldFormatting();
    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();

    private int id;
    private JButton cnfrmBtn = btn.tableOption("Akceptēt", color.button2());

    public OrderEdit() {
        setLayout(new GridLayout(3, 2, 80, 20));

        JComboBox<Status> statusBox = new JComboBox<>();

        try {
            for (Status s : statService.getAllStatuses()) {
                statusBox.addItem(s);
            }
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot", e);
        }

        add(text.text2("statuss:"));
        add(statusBox);

        cnfrmBtn.addActionListener(e -> {
            try {
                Status sStatus = (Status) statusBox.getSelectedItem();
                int StatusId = sStatus.getId();

                Order oldInfo = service.getOrderById(id);

                Order order = new Order(id, oldInfo.getDate(), oldInfo.getTotal(), oldInfo.getAmount(), StatusId, oldInfo.getEmplId(), oldInfo.getProdlId());
                service.updateOrder(order);
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda saglabājot pasūtījumu", ex);
            }
        });

        add(cnfrmBtn);
    }

    public void setOrderId(int id) {
        this.id = id;
    }
}
