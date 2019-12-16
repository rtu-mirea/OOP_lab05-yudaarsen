import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

public class Totalizator {
    private List<Client> users;
    private List<String> horses;
    private User currentUser;

    public void addHorses(String[] names) {
        for (String horse : names) {
            horses.add(horse);
        }
    }

    public static void main(String[] args) {
        Totalizator tl = new Totalizator();
        tl.users = new ArrayList<Client>();
        tl.horses = new ArrayList<>();
        tl.readUsers();
        Admin admin = new Admin("Admin", "Admin", "password");
        JFrame frame = new JFrame("Lab 5");
        frame.setContentPane(new TotalizatorUI(tl).getMainPanel());
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addUser(String name, String login, String password) {
        try {
            users.add(new Client(name, login, password));
        } catch (IllegalArgumentException e) {
            System.out.println(e.toString());
        }
    }

    public void editUser(String lastLogin, Client newClient)
    {
        for(Client user : users)
        {
            if(user.getLogin().equals(lastLogin))
            {
                user.setLogin(newClient.getLogin());
                user.setName(newClient.getName());
                user.setPassword(newClient.getPassword());
            }
        }
    }

    private void readUsers()
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int res = chooser.showOpenDialog(null);
        String path = "";
        if(res == JFileChooser.APPROVE_OPTION)
        {
            path = chooser.getSelectedFile().getAbsolutePath();
        }
        try
        {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
            for(;;)
            {
                users.add((Client)in.readObject());
            }
        }catch(IOException | ClassNotFoundException e)
        {

        }
    }

    public User findUser(String login, String password) {
        for (Client u : users) {
            if (u.enter(login, password)) {
                return u;
            }
        }
        return null;
    }


    public boolean userExists(String login)
    {
        for(Client u : users)
        {
            if(u.getLogin().equals(login))
            {
                return true;
            }
        }
        return false;
    }

    public SortedMap<Integer, String> generateResults()
    {
        SortedMap<Integer, String> res = new TreeMap<>();
        int num;
        for(int i = 0; i < 7; i++)
        {
            num = (int)(Math.random() * 30 + 1);
            res.put(num, horses.get(i));
        }
        /*System.out.println("Таблица забега:");
        Iterator iterator = res.entrySet().iterator();
        SortedMap.Entry entry;
        while(iterator.hasNext())
        {
            entry = (SortedMap.Entry) iterator.next();
            System.out.println("Лошадь " + entry.getValue() + " пробежала за " + entry.getKey());
        }*/
        //calculateMoney(res);
        //clearAllParlays();
        return res;
    }

    public void clearAllParlays()
    {
        for(Client u : users)
        {
            u.clearParlays();
        }
    }

    public Winners calculateMoney(SortedMap list)
    {
        int sum = 0;
        Iterator iterator = list.entrySet().iterator();
        SortedMap.Entry entry = (SortedMap.Entry) iterator.next();
        String winner = (String)entry.getValue();
        for(Client c : users) //подсчет общей суммы
        {
            sum += c.getParlays().get(0).getSum();
        }
        int winCount = 0;
        for(Client c : users) // возвращаем победителям их вложения
        {
            if(c.getParlays().get(0).getHorse().equals(winner)) {
                sum -= c.getParlays().get(0).getSum();
                winCount++;
            }
        }
        int avrNum = 0;
        if(winCount > 0)
        {
            avrNum = sum / winCount;
        }
        Winners winners = new Winners();
        winners.setAvrSum(avrNum);
        for(Client c : users) // выдаем награду победителям
        {
            if(c.getParlays().get(0).getHorse().equals(winner))
            {
                winners.add(c);
            }
        }
        return winners;
    }

    public List<Client> getUsers()
    {
        return users;
    }

    public String[] getHorses() {
        return horses.toArray(new String[0]);
    }
}
