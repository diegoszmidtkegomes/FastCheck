package ifrs.gp.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private List<ItemRetorno> itens;
    private int mesa;
    private String obs;
    public static String PEDIDO;

    public Pedido() {
        itens = new ArrayList<>();
    }

    public List<ItemRetorno> getItens() {
        return itens;
    }

    public void addItens(Item item) {
        if (item.getQuantidade() > 0) {
            this.itens.add(new ItemRetorno(item));
        }
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }
}
