import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditUI {
    private JTextField textName;
    private JTextField textLogin;
    private JTextField textPassword;
    private JButton buttonSave;
    private JPanel mainPanel;
    private Client user;
    private Totalizator tl;
    private OptionsUI opt;

    public EditUI(Client user, Totalizator tl, OptionsUI opt) {
        this.user = user;
        this.opt = opt;
        this.tl = tl;
        textName.setText(user.getName());
        textLogin.setText(user.getLogin());
        textPassword.setText(user.getPassword());
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUser();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void saveUser()
    {
        if(textName.getText().length() == 0 || textLogin.getText().length() == 0 || textPassword.getText().length() == 0)
        {
            JOptionPane.showMessageDialog(null, "Заполните все поля");
            return;
        }
        String last = user.getLogin();
        user.setName(textName.getText());
        user.setLogin(textLogin.getText());
        user.setPassword(textPassword.getText());
        tl.editUser(last, user);
        JOptionPane.showMessageDialog(null, "Изменения сохранены");
        opt.updateList();
    }
}
