package ifrs.gp.model;

public class ItemRetorno {
    private int qntd;
    private int value;

    public ItemRetorno() {
    }

    public ItemRetorno(int qntd, int value) {
        this.qntd = qntd;
        this.value = value;
    }

    public ItemRetorno(Item item) {
        this.qntd = item.getQuantidade();
        this.value = item.getId();
    }

    public int getQntd() {
        return qntd;
    }

    public void setQntd(int qntd) {
        this.qntd = qntd;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

