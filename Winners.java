import java.util.ArrayList;

public class Winners {
    private ArrayList<Client> winnersList;
    private int avrSum;

    public Winners()
    {
        winnersList = new ArrayList<>();
        avrSum = 0;
    }

    public void add(Client c)
    {
        winnersList.add(c);
    }

    public void setAvrSum(int avrSum)
    {
        this.avrSum = avrSum;
    }

    public ArrayList<Client> getWinnersList() {
        return winnersList;
    }

    public int getAvrSum() {
        return avrSum;
    }
}
