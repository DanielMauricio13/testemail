package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple Hello World Controller to display the string returned
 *
 * @author Vivek Bengre
 */

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to my experiment";
    } @GetMapping("/sum/{var1}/{var2}/")
    public int getSum(@PathVariable int var1,@PathVariable int var2) {
        return var1 + var2;
    }
    
}
