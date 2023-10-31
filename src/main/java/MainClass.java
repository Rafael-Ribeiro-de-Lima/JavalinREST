import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.json.JsonMapper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainClass {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().create();
        JsonMapper gsonMapper = new JsonMapper() {
            @Override
            public String toJsonString(@NotNull Object obj, @NotNull Type type) {
                return gson.toJson(obj, type);
            }
            @Override
            public <T> T fromJsonString(@NotNull String json, @NotNull Type targetType) {
                return gson.fromJson(json, targetType);
            }
        };
        var app = Javalin.create(config -> config.jsonMapper(gsonMapper)).start(7070);
        app.get("/", ctx -> ctx.result("Hello World!!!!"));

        app.get("/car", MainClass::returnCar);
        app.get("/manycars", MainClass::returnManyCars);
        app.post("/addCar", MainClass::addNewCar);
    }

    private static void addNewCar(Context context) {
        Car newCar = context.bodyAsClass(Car.class);
        System.out.println(newCar);
        context.result("Thanks for adding a car!");
    }

    private static void returnManyCars(Context context) {
        ArrayList<Car> carArrayList = new ArrayList<>();
        carArrayList.add(new Car("VW", "Golf", 120, 40000));
        carArrayList.add(new Car("Audi", "A4", 180, 75000));
        carArrayList.add(new Car("Mercedes", "C180", 150, 68000));
        context.json(carArrayList);
    }


    public static void returnCar(Context context){
        Car golf = new Car("VW", "Golf", 120, 40000);
        context.json(golf);
    }
}