package br.com.movie.screenmatch;

import br.com.movie.screenmatch.Service.ConsumoApi;
import br.com.movie.screenmatch.Service.ConverteDados;
import br.com.movie.screenmatch.model.DadosSerie;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        var consumoApi = new ConsumoApi();
        var json = consumoApi.obterDados("https://www.omdbapi.com/?t=Breaking_Bad&apikey=6d00c52e");

        System.out.println(json);

        var conversor = new ConverteDados();
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

        System.out.println(dados);
    }
}


