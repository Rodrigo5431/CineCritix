package br.com.backend.ccx.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.backend.ccx.dto.OmdbResponseDTO;
import br.com.backend.ccx.dto.SearchResultDTO;
import br.com.backend.ccx.dto.SeriesDTO;
import br.com.backend.ccx.dto.SeriesInsertDTO;
import br.com.backend.ccx.entities.Series;
import br.com.backend.ccx.entities.User;
import br.com.backend.ccx.exception.NotFoundException;
import br.com.backend.ccx.repository.SeriesRepository;
import br.com.backend.ccx.repository.UserRepository;
import br.com.backend.ccx.security.JwtUtil;

@Service
public class SeriesService {

    @Autowired
    private SeriesRepository seriesRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("2fcfe92f")
    private String apiKey;
    
    @Autowired
    private JwtUtil jwt;

    private static final String OMDB_API_URL = "http://www.omdbapi.com/";

    public List<SeriesDTO> insertOmdbSeries() {
        //cada pagina retorna 10 series
    	int maxPages = 100;
        int currentPage = 1;
        
        try {
            for (int i = 0; i < maxPages; i++) {
                String url = UriComponentsBuilder.fromHttpUrl(OMDB_API_URL)
                        .queryParam("s", "series")
                        .queryParam("page", currentPage + i)
                        .queryParam("apikey", apiKey)
                        .toUriString();
                
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                String responseBody = response.getBody();
                
                ObjectMapper objectMapper = new ObjectMapper();
                OmdbResponseDTO omdbResponse = objectMapper.readValue(responseBody, OmdbResponseDTO.class);
                
                for (SearchResultDTO seriesDtoApi : omdbResponse.getResults()) {
                    String seriesUrl = UriComponentsBuilder.fromHttpUrl(OMDB_API_URL)
                            .queryParam("i", seriesDtoApi.getImdbID())
                            .queryParam("apikey", apiKey)
                            .toUriString();
                    
                    ResponseEntity<String> seriesResponse = restTemplate.getForEntity(seriesUrl, String.class);
                    String seriesResponseBody = seriesResponse.getBody();
                    
                    SeriesDTO seriesDto = objectMapper.readValue(seriesResponseBody, SeriesDTO.class);
                    
                    Series series = new Series();
                    series.setTitle(seriesDto.getTitle());
                    series.setYear(seriesDto.getYear());
                    series.setType(seriesDto.getType());
                    series.setPoster(seriesDto.getPoster());
                    series.setDirector(seriesDto.getDirector());
                    series.setGenre(seriesDto.getGenre());
                    series.setReleased(seriesDto.getReleased());
                    series.setRuntime(seriesDto.getRuntime());
                    series.setIdImdb(seriesDto.getImdbID());
                    series.setWriter(seriesDto.getWriter());
                    series.setPlot(seriesDto.getPlot());
                    series.setImdbRating(seriesDto.getImdbRating());
                    seriesRepository.save(series);
                }
            }
            
            List<Series> seriesList = seriesRepository.findAll();
            return seriesList.stream().map(SeriesDTO::new).collect(Collectors.toList());
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar dados da API OMDB: " + e.getMessage());
        }
    }

    public List<SeriesDTO> listAll() {
        List<Series> series = seriesRepository.findAll();
        return series.stream().map(SeriesDTO::new).toList();
    }

    public Page<SeriesDTO> listPag(Pageable pageable) {
        Page<Series> series = seriesRepository.findAll(pageable);
        return series.map(SeriesDTO::new);
    }

    public SeriesDTO findById(Long id) {
        Optional<Series> seriesOpt = seriesRepository.findById(id);
        if (seriesOpt.isEmpty()) {
            throw new NotFoundException("Série não encontrada");
        }
        return new SeriesDTO(seriesOpt.get());
    }
    
    public SeriesDTO findByTitle(String title) {
        Optional<Series> seriesOpt = seriesRepository.findByTitle(title);
        if (seriesOpt.isEmpty()) {
            throw new NotFoundException("Série não encontrada");
        }
        return new SeriesDTO(seriesOpt.get());
    }

    public SeriesDTO create(SeriesInsertDTO insert, String token) {
        Long idUser = jwt.getId(token);
        
        Optional<User> userOpt = userRepository.findById(idUser);
        if (userOpt.isEmpty()) {
            throw new NotFoundException("Usuário não autenticado");
        }
        
        Series series = new Series();
        series.setTitle(insert.getTitle());
        series.setYear(insert.getYear());
        series.setType(insert.getType());
        series.setPoster(insert.getPoster());
        seriesRepository.save(series);
        return new SeriesDTO(series);
    }

    public SeriesDTO update(Long id, SeriesInsertDTO insertDto) {
        Optional<Series> seriesOpt = seriesRepository.findById(id);
        if (seriesOpt.isEmpty()) {
            throw new NotFoundException("Série não encontrada");
        }
        
        Series series = seriesOpt.get();
        series.setTitle(insertDto.getTitle() != null ? insertDto.getTitle() : series.getTitle());
        series.setYear(insertDto.getYear() != null ? insertDto.getYear() : series.getYear());
        series.setType(insertDto.getType() != null ? insertDto.getType() : series.getType());
        series.setPoster(insertDto.getPoster() != null ? insertDto.getPoster() : series.getPoster());
        
        seriesRepository.save(series);
        return new SeriesDTO(series);
    }
    
    public void delete(Long id) {
        Optional<Series> seriesOpt = seriesRepository.findById(id);
        if (seriesOpt.isEmpty()) {
            throw new NotFoundException("Série não encontrada");
        }
        seriesRepository.deleteById(id);
    }
}
