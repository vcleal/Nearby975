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

    public int getId() {
        return id;
    }

    public int id;



    private String owner;
    private List<String> atendentes;
    public double preco;
    public float distancia;
    public String local;
    public int ingressosDisponiveisParaCompra;
    public String descricao;
    public Date data;
    public String datac;

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
    public Evento(int id2,String nome2, float preco2, Date data2, float distancia2){
        this.id = id2;
        nome=nome2;
        preco = preco2;
        data=data2;
        distancia = distancia2;
        descricao = "";
        local = "Rua dos bobos";
        ingressosDisponiveisParaCompra = 20;
        owner="admin2";
        atendentes = new ArrayList<String>();

    }

    public Evento(int id2,String nome2, float preco2, Date data2, String local2,String descricao2,String dono){
        nome=nome2;
        this.id = id2;
        preco = preco2;
        data=data2;
        descricao = descricao2;
        local = local2;
        ingressosDisponiveisParaCompra = 20;
        owner=dono;
        atendentes = new ArrayList<String>();

    }



    public Evento(String nome2, double preco2, Date data2){
        nome=nome2;
        preco = preco2;
        data=data2;
        descricao = "";
        local = "Rua dos bobos";
        ingressosDisponiveisParaCompra = 20;
        owner="admin2";
    }

}
