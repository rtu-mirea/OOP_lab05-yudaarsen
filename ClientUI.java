import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientUI {
    private JPanel mainPanel;
    private JComboBox<String> comboHorses;
    private JTextField textField1;
    private JButton buttonParlay;
    private JButton buttonView;
    private Client client;
    private String[] horses;

    public ClientUI(String[] horses, Client client) {
        this.client = client;
        this.horses = horses;
        buttonParlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addParlay();
            }
        });
        buttonView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewParlay();
            }
        });
    }

    private void addParlay()
    {
        if(comboHorses.getSelectedIndex() != -1 && client.getParlays().isEmpty())
        {
            if(textField1.getText().length() != 0 && isNumber(textField1.getText()))
            {
                client.addParlay((String)comboHorses.getSelectedItem(), Integer.parseInt(textField1.getText()));
                JOptionPane.showMessageDialog(null, "Ставка успешно добавлена");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Некорректная сумма в ставке");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Вы не выбрали лошадь, либо уже сделали ставку");
        }
    }

    private void viewParlay()
    {
        if(!client.getParlays().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Ставка сделана на лошадь " + client.getParlays().get(0).getHorse() +
                    ", сумма: " + client.getParlays().get(0).getSum());
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Вы не делали ставок");
        }
    }

    private boolean isNumber(String text)
    {
        for(int i = 0; i < text.length(); i++)
        {
            if(!Character.isDigit(text.charAt(i)))
                return false;
        }
        return true;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        comboHorses = new JComboBox<>(horses);
    }
}
