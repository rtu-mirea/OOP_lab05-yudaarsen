import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class OptionsUI {
    private JComboBox comboBox1;
    private JPanel mainPanel;
    private JButton buttonDelete;
    private JButton buttonEdit;
    private JButton buttonSave;
    private ArrayList<Client> users;
    private Totalizator tl;

    public OptionsUI(List<Client> users, Totalizator tl)
    {
        this.tl = tl;
        this.users = (ArrayList<Client>)users;
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
        buttonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editUser();
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUsers();
            }
        });
    }

    public JPanel getMainPanel()
    {
        return mainPanel;
    }

    private void createUIComponents() {
        comboBox1 = new JComboBox();
        for(Client user : users)
            comboBox1.addItem(user.getLogin());
    }

    private void deleteUser()
    {
        int idx = comboBox1.getSelectedIndex();
        if(idx != -1)
        {
            users.remove(idx);
            comboBox1.removeItemAt(idx);
            JOptionPane.showMessageDialog(null, "Пользователь удален");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Пользователь не выбран");
        }
    }

    private void editUser()
    {
        int idx = comboBox1.getSelectedIndex();
        EditUI edit = new EditUI(users.get(idx), tl, this);
        JFrame e = new JFrame();
        e.setContentPane(edit.getMainPanel());
        e.pack();
        e.setVisible(true);
    }

    public void updateList()
    {
        comboBox1.removeAllItems();
        users = (ArrayList<Client>)tl.getUsers();
        for(Client user : users)
            comboBox1.addItem(user.getLogin());
    }

    private void saveUsers()
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int res = chooser.showOpenDialog(null);
        String path = "";
        if(res == JFileChooser.APPROVE_OPTION)
        {
            path = chooser.getSelectedFile().getAbsolutePath();
        }
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
            for(Client user : users)
            {
                out.writeObject(user);
            }
            out.close();
            JOptionPane.showMessageDialog(null, "Список сохранен");
        }catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "Ошибка: " + e.toString());
        }
    }
}
