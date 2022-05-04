import models.Hero;
import models.Squad;
import spark.ModelAndView;
import services.HeroService;
import services.SquadService;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        ProcessBuilder process = new ProcessBuilder();
        Integer port;

        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        }

        else {
            port = 4567;
        }
        port(port);

        staticFileLocation("/public/");

        get("/",((request, response) ->  {
            return modelAndView(new HashMap<>(), "index.hbs");
        }), new HandlebarsTemplateEngine());

        get("hero",((request, response) -> {
            return modelAndView(new HashMap<>(), "hero.hbs");
        }), new HandlebarsTemplateEngine());

        get("/squad",((request, response) -> {
            return modelAndView(new HashMap<>(), "squad.hbs");
        }),new HandlebarsTemplateEngine());

        //post a new hero
        post("/hero/new", ((request, response) -> {
            Map<String,Object> data = new HashMap<>();

            String name = request.queryParams("hero");
            String age = request.queryParams("age");
            String specialPower = request.queryParams("power");
            String weakness = request.queryParams("weakness");
            List<Hero>heroList = request.session().attribute("Heroes");


            Hero hero = new Hero(name,age,specialPower,weakness);
            HeroService heroservice=new HeroService();


            HeroService heroService = null;
            heroService.addHero(heroList, hero);
            List <Hero> updatedList = heroService.getHeroList();
            request.session().attribute("Heroes", updatedList);
            System.out.println(updatedList);


            data.put("Heroes", request.session().attribute("Heroes"));
            return new ModelAndView(data, "hero-new-hbs");
        }), new HandlebarsTemplateEngine());

        //get new heroes list template
        get("/hero/new", ((request, response) -> {
            Map<String,Object> data = new HashMap<>();
            data.put("Heroes", request.session().attribute("yourHeroList"));
            return new ModelAndView(data, "hero-new.hbs");

        }), new HandlebarsTemplateEngine());


        post("/squad/new", ((request, response) -> {
            Map<String,Object> data = new HashMap<>();


            String name = request.queryParams("squad");
            String maxSize = request.queryParams("size");
            String squadCause = request.queryParams("task");
            List <Squad> squadList = request.session().attribute("Squads");


            Squad squad = new Squad(maxSize, name, squadCause);
            SquadService squadService = new SquadService();
            squadService.addSquad(squadList, squad);


            List<Squad> updatedSquad = squadService.getSquadList();
            boolean updatedSquadList = false;
            request.session().attribute("Squad", updatedSquadList);
            System.out.println(updatedSquadList);

            //add key-key value data to map for displaying to the template
            data.put("Squad", request.session().attribute("Squad"));
            return new ModelAndView(data, "squad-new.hbs");
        }),new HandlebarsTemplateEngine());


        get("/squad/new",((request, response) -> {
            Map<String,Object>data = new HashMap<>();
            data.put("Squads", request.session().attribute("yourSquadList"));
            return new ModelAndView(data, "squad-new.hbs");
        }), new HandlebarsTemplateEngine());

    }


}
