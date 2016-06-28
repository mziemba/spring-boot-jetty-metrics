package mziemba.examples;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/test")
class TestController {

    @RequestMapping(value = "/1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = GET)
    public CompletableFuture<String> get1(){
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "done";
        });
    }

    @RequestMapping(value = "/2", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = GET)
    public String get2(){
        return "done";
    }
}
