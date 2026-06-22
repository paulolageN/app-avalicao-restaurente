package model;

import java.io.Serializable;

public class Restaurante implements Serializable {
    private int idRestaurante;
    private String nomeRestaurante;
    private String enderecoRestaurante;
    private String telefoneRestaurante;
    private String descricaoRestaurante;
    private String horarioFuncionamento;

    public String getNomeRestaurante() {
        return nomeRestaurante;
    }

    public void setNomeRestaurante(String nomeRestaurante) {
        this.nomeRestaurante = nomeRestaurante;
    }

    public String getEnderecoRestaurante() {
        return enderecoRestaurante;
    }

    public void setEnderecoRestaurante(String enderecoRestaurante) {
        this.enderecoRestaurante = enderecoRestaurante;
    }

    public String getTelefoneRestaurante() {
        return telefoneRestaurante;
    }

    public void setTelefoneRestaurante(String telefoneRestaurante) {
        this.telefoneRestaurante = telefoneRestaurante;
    }

    public String getDescricaoRestaurnate() {
        return descricaoRestaurante;
    }

    public void setDescricaoRestaurante(String descricaoRestaurante) {
        this.descricaoRestaurante = descricaoRestaurante;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }


}
