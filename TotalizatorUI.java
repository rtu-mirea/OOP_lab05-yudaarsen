import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.SortedMap;

public class TotalizatorUI {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JButton findUser;
    private JButton startRace;
    private JButton addUser;
    private JButton saveHorses;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField12;
    private JPanel mainPanel;
    private JButton buttonOptions;
    private Totalizator tl;

    public TotalizatorUI(Totalizator tl) {
        this.tl = tl;
        saveHorses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addHorses();
            }
        });
        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
        findUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });
        startRace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                race();
            }
        });
        buttonOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openOptions();
            }
        });
    }

    private void loginUser()
    {
        if(textField11.getText().length() > 0 && textField12.getText().length() > 0)
        {
            User user = tl.findUser(textField11.getText(), textField12.getText());
            if(user != null)
            {
                ClientUI client = new ClientUI(tl.getHorses(), (Client)user);
                JFrame window = new JFrame();
                window.setContentPane(client.getMainPanel());
                window.pack();
                window.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Пользователь не найден");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Для авторизации пользователя введите всю информацию");
        }
    }

    private void addHorses()
    {
        if(textField1.getText().length() > 0 && textField2.getText().length() > 0 && textField3.getText().length() > 0 &&
                textField4.getText().length() > 0 && textField5.getText().length() > 0 && textField6.getText().length() > 0 &&
                textField7.getText().length() > 0)
        {
            String[] horses = new String[7];
            horses[0] = textField1.getText();
            horses[1] = textField2.getText();
            horses[2] = textField3.getText();
            horses[3] = textField4.getText();
            horses[4] = textField5.getText();
            horses[5] = textField6.getText();
            horses[6] = textField7.getText();
            tl.addHorses(horses);
            textField1.setEnabled(false);
            textField2.setEnabled(false);
            textField3.setEnabled(false);
            textField4.setEnabled(false);
            textField5.setEnabled(false);
            textField6.setEnabled(false);
            textField7.setEnabled(false);
            saveHorses.setEnabled(false);
            findUser.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Список лошадей сохранен");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Должны быть введены все имена лошадей");
        }
    }

    private void addUser()
    {
        if(textField8.getText().length() > 0 && textField9.getText().length() > 0 && textField10.getText().length() > 0)
        {
            if(!tl.userExists(textField9.getText()))
            {
                tl.addUser(textField8.getText(), textField9.getText(), textField10.getText());
                JOptionPane.showMessageDialog(null, "Пользователь " + textField9.getText() + " успешно добавлен.");
                textField8.setText("");
                textField9.setText("");
                textField10.setText("");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Пользователь с таким логином существует.");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Для добавления пользователя введите всю информацию");
        }
    }

    private void race()
    {
        SortedMap<Integer, String> res = tl.generateResults();
        StringBuilder data = new StringBuilder();
        Iterator iterator = res.entrySet().iterator();
        SortedMap.Entry entry;
        while(iterator.hasNext())
        {
            entry = (SortedMap.Entry) iterator.next();
            data.append("Лошадь " + entry.getValue() + " пробежала за " + entry.getKey() + "\n");
        }
        JOptionPane.showMessageDialog(null, "Результаты:\n" + data.toString());
        Winners winners = tl.calculateMoney(res);
        data.delete(0, data.length());
        for(Client c : winners.getWinnersList())
        {
            data.append("Пользователь " + c.getName() + " выигрывает " + winners.getAvrSum() + "\n");
        }
        JOptionPane.showMessageDialog(null, "Таблица выигрышей:\n" + data.toString());
        tl.clearAllParlays();
    }

    private void openOptions()
    {
        OptionsUI op = new OptionsUI(tl.getUsers(), tl);
        JFrame options = new JFrame("Настройки");
        options.setContentPane(op.getMainPanel());
        options.pack();
        options.setVisible(true);
    }

    public JPanel getMainPanel()
    {
        return mainPanel;
    }

}
