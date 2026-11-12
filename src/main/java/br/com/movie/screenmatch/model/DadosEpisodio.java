package br.com.movie.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(@JsonAlias("Title") String titulo,
                            @JsonAlias("Episode") Integer numeroEp,
                            @JsonAlias("Season") Integer temporada,
                            @JsonAlias("imdbRating") String avaliacaoEp,
                            @JsonAlias("Released") String dataDeCriacao) {
}
