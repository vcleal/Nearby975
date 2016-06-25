package com.nearbysocialevents.nearby975;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ra119555 on 09/05/16.
 */
public class Evento implements Serializable{
    public String nome;
    private String id;



    private String owner;
    private List<String> atendentes;
    public float preco;
    public float distancia;
    public String local;
    public int ingressosDisponiveisParaCompra;
    public String descricao;
    public Date data;

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public boolean addAtendente(String atendente){
        try{
            atendentes.add(atendente);
            return true;
        }catch (Exception e) {
            return false;
        }

    }


    public List<String> getAtendentes(){
        List<String> copyAtendentes = new ArrayList<String>(this.atendentes);
        return copyAtendentes;
    }




    //TODO: Arrumar este construtor ou fazer outros
    public Evento(String id,String nome2, float preco2, Date data2, float distancia2){
        nome=nome2;
        preco = preco2;
        data=data2;
        distancia = distancia2;
        descricao = "";
        local = "Rua dos bobos";
        ingressosDisponiveisParaCompra = 20;
        owner="admin";
        atendentes = new ArrayList<String>();

    }

}
