package br.com.movie.screenmatch.principal;

import br.com.movie.screenmatch.Service.ConsumoApi;
import br.com.movie.screenmatch.Service.ConverteDados;
import br.com.movie.screenmatch.model.DadosEpisodio;
import br.com.movie.screenmatch.model.DadosSerie;
import br.com.movie.screenmatch.model.DadosTemporada;
import br.com.movie.screenmatch.model.Episodio;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=";

    public void exibirMenu() {
        System.out.println("Digite o nome da serie para a busca: ");
        var nomeSerie = leitura.nextLine();

        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for(int i = 1; i<=dados.totalTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+")+ "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodios().forEach((e -> System.out.println(e.titulo()))));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        System.out.println("\n Top 5 episodios!");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacaoEp().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primeiro filtro (N/A) " + e ))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacaoEp).reversed())
                .peek(e -> System.out.println("Ordenacao " + e))
                .limit(5)
                .peek(e -> System.out.println("Limite " + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Mapeamento " + e))
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numeroTemporada(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("Digite o trecho do episodio: ");
        var trechoTitulo = leitura.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();
        if(episodioBuscado.isPresent()) {
            System.out.println("Episodio encontrado");
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
        } else {
            System.out.println("Episodio nao encontrado!");
        }

        System.out.println("A partir de qual ano voce deseja ver os episodios? ");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate databusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getDataDeCriacao() != null && e.getDataDeCriacao().isAfter(databusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                " Episodio: " + e.getTitulo() +
                                " Data de lancamento: " + e.getDataDeCriacao().format(df)
                ));

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacaoEp() > 0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacaoEp)));
        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacaoEp() > 0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacaoEp));
        System.out.println("Media: " + est.getAverage());
        System.out.println("Nota max: " + est.getMax());
        System.out.println("Nota min: " + est.getMin());
        System.out.println("Numero de episodios avaliados: " + est.getCount());
    }
}

