package projekti;

import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KontrolleriViestit {
    @Autowired
    private HttpSession istunto;
    @Autowired
    private KayttajaRepository kayttajarepo;
    @Autowired
    private ViestiRepository viestirepo;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private Palvelut palvelut;
    private Kayttaja naytettavaKayttaja;
    
    @PostMapping("/uusiViesti/{x}")
    public String uusiViesti(@RequestParam String teksti, @PathVariable String x, Model model) {
        Kayttaja lahettaja = palvelut.kirjautunutKayttaja();
        Viesti viesti = new Viesti(teksti,
                LocalDateTime.now(),
                lahettaja,
                new ArrayList<>(),
                0);
        viestirepo.save(viesti);
        
        Kayttaja vastaanottaja = kayttajarepo.findByKayttajatunnus(x);
        vastaanottaja.getViestit().add(viesti);
        kayttajarepo.save(vastaanottaja);
        return "redirect:/profiili/{x}";
    }
    
    @PostMapping("/tykkaaViestista/{id}")
    public String tykkaaViestista(@PathVariable long id) {
        Viesti viesti = viestirepo.getOne(id);
        Kayttaja tykatty = viesti.getLahettaja();
        Kayttaja tykkaaja = palvelut.kirjautunutKayttaja();
        
        if(!viesti.getTykkaajat().contains(tykkaaja)) {
            viesti.getTykkaajat().add(tykkaaja);
            viesti.setTykkayksia(viesti.getTykkayksia() + 1);
            viestirepo.save(viesti);
            kayttajarepo.save(tykatty);
        }
        
        String paluupolku = viesti.getLahettaja().getProfiilipolku();
        
        return "redirect:/kayttajat";
    }
    
    
}
