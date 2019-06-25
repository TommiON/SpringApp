package projekti;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KontrolleriTervetuloa {

    @GetMapping("/tervetuloa")
    public String tervetuloa() {
        return "index";
    }
    
    @GetMapping("*")
    public String ohjaus() {
        return "redirect:/tervetuloa";
    }
}
