package projekti;

import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KontrolleriKayttajat {
    @Autowired
    private HttpSession istunto;
    @Autowired
    private KayttajaRepository kayttajarepo;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private Palvelut palvelut;
    private Kayttaja naytettavaKayttaja;
    
    // näyttää käyttäjälistan ja käyttäjähaun
    @GetMapping("/kayttajat")
    public String listaaKayttajat(Model model) {
        model.addAttribute("kayttajat", kayttajarepo.findAll());
        model.addAttribute("aktiivinenKayttaja", palvelut.kirjautunutKayttaja());
        return "kayttajat";
    }
    
    // ottaa vastaan käyttäjähaun
    @PostMapping("/kayttajat")
    public String etsiKayttaja(@RequestParam String nimi, Model model) {
        if(nimi.equals("")) {
            model.addAttribute("kayttajat", kayttajarepo.findAll());
        } else {
            model.addAttribute("kayttajat", kayttajarepo.findByNimi(nimi));
        }
        model.addAttribute("aktiivinenKayttaja", palvelut.kirjautunutKayttaja());
        return "kayttajat";
    }
}
