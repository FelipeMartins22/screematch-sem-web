package br.com.movie.screenmatch.model;


import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numeroEp;
    private double avaliacaoEp;
    private LocalDate dataDeCriacao;

    public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodio) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodio.titulo();
        this.numeroEp = dadosEpisodio.numeroEp();

        try {
            this.avaliacaoEp = Double.valueOf(dadosEpisodio.avaliacaoEp());
        } catch (NumberFormatException ex) {
            this.avaliacaoEp = 0.0;
        }

        try {
            this.dataDeCriacao = LocalDate.parse(dadosEpisodio.dataDeCriacao());
        } catch (DateTimeParseException ex) {
            this.dataDeCriacao = null;
        }

    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public LocalDate getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(LocalDate dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public double getAvaliacaoEp() {
        return avaliacaoEp;
    }

    public void setAvaliacaoEp(double avaliacaoEp) {
        this.avaliacaoEp = avaliacaoEp;
    }

    public Integer getNumeroEp() {
        return numeroEp;
    }

    public void setNumeroEp(Integer numeroEp) {
        this.numeroEp = numeroEp;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "temporada= " + temporada +
                ", titulo= '" + titulo + '\'' +
                ", numeroEp= " + numeroEp +
                ", avaliacaoEp= " + avaliacaoEp +
                ", dataDeCriacao= " + dataDeCriacao ;
    }
}
