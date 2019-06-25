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
public class KontrolleriProfiilit {
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
    
    
    // näyttää kirjautuneen käyttäjän profiilisivun
    @GetMapping("/kotisivu")
    public String kotisivu(Model model) {
        model.addAttribute("aktiivinenKayttaja", palvelut.kirjautunutKayttaja());
        model.addAttribute("kayttaja", palvelut.kirjautunutKayttaja());
        model.addAttribute("omasivu", true);
        if(palvelut.kirjautunutKayttaja().getProfiilikuva() != null) {
            model.addAttribute("kuva", true);
        }
        return "profiili";
    }
    
    // näyttää toisen käyttäjän profiilisivun
    @GetMapping("/profiili/{x}")
    public String profiili(@PathVariable String x, Model model) {
        model.addAttribute("aktiivinenKayttaja", palvelut.kirjautunutKayttaja());
        Kayttaja naytettavaKayttaja = kayttajarepo.findByProfiilipolku(x);
        model.addAttribute("kayttaja", naytettavaKayttaja);
        if(naytettavaKayttaja.getProfiilikuva() != null) {
            model.addAttribute("kuva", true);
        }
        if(naytettavaKayttaja == palvelut.kirjautunutKayttaja()) {
            model.addAttribute("omasivu", true);
            return "redirect:/kotisivu";
        } else {
            model.addAttribute("omasivu", false);
        }
        
        if(naytettavaKayttaja.onKaveri(palvelut.kirjautunutKayttaja())) {
            return "profiili";
        }
        
        if(naytettavaKayttaja.onJoPyytanytKaveriksi(palvelut.kirjautunutKayttaja()) 
                || naytettavaKayttaja == palvelut.kirjautunutKayttaja()) {
            model.addAttribute("voiPyytaaKaveriksi", false);
        } else {
            model.addAttribute("voiPyytaaKaveriksi", true);
        }   
        return "suljettuprofiili";
    }
    
    // ottaa vastaan kaveripyynnön
    @PostMapping("/kaveripyynto/{x}")
    public String pyydaKaveriksi(@PathVariable String x) {
        Kayttaja pyytaja = palvelut.kirjautunutKayttaja();
        Kayttaja pyydetty = kayttajarepo.findByKayttajatunnus(x);
        if(!pyydetty.onJoPyytanytKaveriksi(pyytaja)) {
            pyydetty.getKaveripyynnot().add(pyytaja);
            kayttajarepo.save(pyydetty);
        }
        return "redirect:/profiili/{x}";
    }
    
    @PostMapping("/hyvaksyPyynto/{x}")
    public String hyvaksyPyynto(@PathVariable String x) {
        Kayttaja hyvaksyja = palvelut.kirjautunutKayttaja();
        Kayttaja pyytaja = kayttajarepo.findByKayttajatunnus(x);
        
        hyvaksyja.getKaverit().add(pyytaja);
        hyvaksyja.getKaveripyynnot().remove(pyytaja);
        kayttajarepo.save(hyvaksyja);
        
        pyytaja.getKaverit().add(hyvaksyja);
        kayttajarepo.save(pyytaja);
        
        return "redirect:/kotisivu";
    }
    
    @PostMapping("/hylkaaPyynto/{x}")
    public String hylkaaPyynto(@PathVariable String x) {
        Kayttaja hylkaaja = palvelut.kirjautunutKayttaja();
        Kayttaja pyytaja = kayttajarepo.findByKayttajatunnus(x);
        hylkaaja.getKaveripyynnot().remove(pyytaja);
        kayttajarepo.save(hylkaaja);
        kayttajarepo.save(pyytaja);
        return "redirect:/kotisivu";
    }
    
    
    
}
