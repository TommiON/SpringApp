
package projekti;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KontrolleriRekisteroityminen {
    @Autowired
    private HttpSession istunto;
    @Autowired
    private KayttajaRepository kayttajarepo;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private Palvelut palvelut;
    private Kayttaja naytettavaKayttaja;
    
    // näyttää rekisteröitymissivun
    @GetMapping("/rekisteroidy")
    public String rekisteroitymissivu() {
        return "rekisteroidy";
    }
    
    // ottaa vastaan käyttäjärekisteröitymisen
    @PostMapping("/rekisteroidy")
    public String rekisteroiUusiKayttaja(@RequestParam String kayttajatunnus,
            @RequestParam String salasana,
            @RequestParam String nimi,
            @RequestParam String profiilipolku) {
        Kayttaja uusi = new Kayttaja(kayttajatunnus,
                encoder.encode(salasana),
                nimi,
                profiilipolku,
                new ArrayList(),
                new ArrayList(),
                new ArrayList(),
                null,
                new ArrayList());
        kayttajarepo.save(uusi);
        if(kayttajarepo.findByKayttajatunnus(kayttajatunnus) != null) {
            return "redirect:/login";
        } else {
            return "redirect:/rekisteroidy";
        }   
    }
}
