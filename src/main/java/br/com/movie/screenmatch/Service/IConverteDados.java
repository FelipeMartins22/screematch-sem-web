package br.com.movie.screenmatch.Service;

public interface IConverteDados {
   <T> T  obterDados(String json, Class<T> classe);
}
