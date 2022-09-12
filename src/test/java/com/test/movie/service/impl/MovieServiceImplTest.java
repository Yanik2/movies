package com.test.movie.service.impl;

public class MovieServiceImplTest {
//    private MovieService movieService;
//    private MovieRepository movieRepositoryMock;
//
//    @BeforeEach
//    public void setUp() {
//        movieRepositoryMock = Mockito.mock(MovieRepository.class);
//        movieService = new MovieServiceImpl(movieRepositoryMock);
//    }
//
//    @Test
//    void shouldReturnMovieById() {
//        var expectedResult = new Movie();
//        when(movieRepositoryMock.findById(1)).thenReturn(Optional.of(expectedResult));
//
//        var actualResult = movieService.findById(1);
//
//        assertEquals(expectedResult, actualResult);
//    }
//
//    @Test
//    public void shouldThrowNotFoundExceptionFindById() {
//        assertThrows(NotFoundException.class, () -> movieService.findById(1));
//    }
//
//
//    @Test
//    public void shouldReturnMovieList() {
//        var params = new HashMap<String, String>();
//        params.put("pageNumber", "1");
//        params.put("pageSize", "3");
//        var expectedResult = List.of(new Movie());
//        var page = new PageImpl<>(expectedResult);
//
//        when(movieRepositoryMock.findAll(isA(Pageable.class))).thenReturn(page);
//
//        var actualResult = movieService.findAllMovies(params);
//
//        assertEquals(expectedResult, actualResult);
//    }
//
//    @Test
//    public void shouldReturnMovieCreate() {
//        var movieDto = new MovieDto();
//        var expectedResult = new Movie();
//        when(movieRepositoryMock.saveAndFlush(isA(Movie.class))).thenReturn(expectedResult);
//
//        var actualResult = movieService.createMovie(movieDto);
//
//        assertEquals(expectedResult, actualResult);
//    }
//
//    @Test
//    public void shouldReturnMovieUpdate() {
//        var movieDto = new MovieDto();
//        var expectedResult = new Movie();
//        when(movieRepositoryMock.saveAndFlush(isA(Movie.class))).thenReturn(expectedResult);
//
//        var actualResult = movieService.updateMovie(movieDto);
//
//        assertEquals(expectedResult, actualResult);
//    }
//
//    @Test
//    public void shouldReturnIdDelete() {
//        var movie = new Movie();
//        when(movieRepositoryMock.findById(1)).thenReturn(Optional.of(movie));
//
//        var actualResult = movieService.deleteMovie(1);
//
//        assertEquals(1, actualResult);
//    }
//
//    @Test
//    public void shouldThrowWhenDeleteMovie() {
//        assertThrows(NotFoundException.class, () -> movieService.deleteMovie(1));
//    }
}