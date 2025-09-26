package br.com.oracleproject.model;

import java.util.List;
import java.util.ArrayList;

public class Equipe {
    private int id;
    private String nome;
    private String descricao;
    private List<Usuario> membros = new ArrayList<>();


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public List<Usuario> getMembros() { return membros; }
    public void setMembros(List<Usuario> membros) { this.membros = membros; }
}