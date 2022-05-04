import spark.template.handlebars.HandlebarsTemplateEngine;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
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
            Map<String,Object> data = new HashMap<>();

            //get inputs from form
            String name = request.queryParams("hero");
            String age = request.queryParams("age");
            String specialPower = request.queryParams("power");
            String weakness = request.queryParams("weakness");
            List<Hero>heroList = request.session().attribute("Heroes");

            //create hero instance from data
            Hero hero = new Hero(name,age,specialPower,weakness);




        }));






    }


}
