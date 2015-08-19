package com.citrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MovieController {

    @Autowired
    private MovieDatabase movieDatabase;

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public @ResponseBody List<Movie> filterMovies(@RequestParam(required = false) String filter) throws ScriptException {
        List<Movie> movies = movieDatabase.getMovies();
        return filter != null ? filter(movies, filter) : movies;
    }

    private List<Movie> filter(List<Movie> movies, String filter) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        engine.eval(filter);

        Invocable invocable = (Invocable) engine;
        Predicate predicate = invocable.getInterface(Predicate.class);
        return movies.stream().filter(predicate::test).collect(Collectors.toList());
    }

    public interface Predicate {
        boolean test(Movie movie);
    }

}
