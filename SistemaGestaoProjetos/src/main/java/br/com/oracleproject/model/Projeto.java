package br.com.oracleproject.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Projeto {
    private int id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTerminoPrevista;
    private Usuario gerenteResponsavel;
    private Status status;
    private List<Equipe> equipesAlocadas = new ArrayList<>();

    public enum Status {
        PLANEJADO, EM_ANDAMENTO, CONCLUIDO, CANCELADO
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataTerminoPrevista() { return dataTerminoPrevista; }
    public void setDataTerminoPrevista(LocalDate dataTerminoPrevista) { this.dataTerminoPrevista = dataTerminoPrevista; }
    public Usuario getGerenteResponsavel() { return gerenteResponsavel; }
    public void setGerenteResponsavel(Usuario gerenteResponsavel) { this.gerenteResponsavel = gerenteResponsavel; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }


    public List<Equipe> getEquipesAlocadas() {
        return equipesAlocadas;
    }

    public void setEquipesAlocadas(List<Equipe> equipesAlocadas) {
        this.equipesAlocadas = equipesAlocadas;
    }
}