package ifrs.gp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Item implements Serializable{
    private int id;
    private int tipo;
    private int quantidade;
    private String nome;
    public static Map<Integer, Item> LISTAO;

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public static Map<Integer, Item> getLISTAO() {
        return LISTAO;
    }

    public static void setLISTAO(Map<Integer, Item> LISTAO) {
        Item.LISTAO = LISTAO;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Item() {
        this.quantidade = 0;
    }

    public int getId() {
        return id;
    }

    public int getTipo() {
        return tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void incQuantidade() {
        this.quantidade++;
    }

    public void limpaQuantidade() {
        this.quantidade = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", quantidade=" + quantidade +
                ", nome='" + nome + '\'' +
                '}';
    }
}
