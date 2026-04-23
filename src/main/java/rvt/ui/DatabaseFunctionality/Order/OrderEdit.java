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

    private Runnable onSave;

    TextFormatting text = new TextFormatting();
    FieldFormatting field = new FieldFormatting();
    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();

    private int id;
    JComboBox<Status> statusBox = new JComboBox<>();

    private JButton cnfrmBtn = btn.tableOption("Akceptēt", color.button2());

    public OrderEdit() {
        setLayout(new GridLayout(3, 2, 80, 20));

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

                if (onSave != null) {
                    onSave.run();
                }
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda saglabājot pasūtījumu", ex);
            }
        });

        add(cnfrmBtn);
    }

    public void loadOrder(int id) {
        this.id = id;

        try {
            Order oldInfo = service.getOrderById(id);

            for (int i = 0; i < statusBox.getItemCount(); i++) {
                if (statusBox.getItemAt(i).getId() == oldInfo.getStatId()) {
                    statusBox.setSelectedIndex(i);
                    break;
                }
            }
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot produktu", e);
        }
    }

    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }
}
