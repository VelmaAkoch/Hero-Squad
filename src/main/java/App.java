import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;

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






    }


}
