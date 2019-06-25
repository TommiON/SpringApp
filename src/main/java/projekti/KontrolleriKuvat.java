package projekti;

import java.io.IOException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class KontrolleriKuvat {
    
    @Autowired
    private KuvaRepository kuvarepo;
    @Autowired
    private Palvelut palvelut;
    @Autowired
    private KayttajaRepository kayttajarepo;
    
    @PostMapping("/kuvat")
    public String lisaaKuva(@RequestParam("tiedosto") MultipartFile tiedosto,
            @RequestParam("kuvaus") String kuvaus) throws IOException {
        Kuva kuva = new Kuva();
        kuva.setDatasisalto(tiedosto.getBytes());
        kuva.setKuvateksti(kuvaus);
        kuva.setTykkaajat(new ArrayList());
        kuva.setTykkayksia(0);
        kuvarepo.save(kuva);
        palvelut.kirjautunutKayttaja().getKuvat().add(kuva);
        kayttajarepo.save(palvelut.kirjautunutKayttaja());
        return "redirect:/kotisivu";
    }
    
    
    @GetMapping(path="/kuvat/{id}", produces="image/jpg")
    @ResponseBody
    public byte[] annaKuva(@PathVariable long id) {
        return kuvarepo.getOne(id).getDatasisalto();
    }
    
    @GetMapping("/kuvateksti/{id}")
    @ResponseBody
    public String annaKuvateksti(@PathVariable long id) {
        if(kuvarepo.getOne(id).getKuvateksti() == null) {
            return "ei kuvatekstiä!";
        }
        return kuvarepo.getOne(id).getKuvateksti();
    }
    
    @PostMapping("/tykkaaKuvasta/{id}")
    public String tykkaaKuvasta(@PathVariable long id) {
        Kuva kuva = kuvarepo.getOne(id);
        Kayttaja tykatty = kuva.getOmistaja();
        Kayttaja tykkaaja = palvelut.kirjautunutKayttaja();
        
        if(!kuva.getTykkaajat().contains(tykkaaja)) {
            kuva.getTykkaajat().add(tykkaaja);
            kuva.setTykkayksia(kuva.getTykkayksia() + 1);
            kuvarepo.save(kuva);
            kayttajarepo.save(tykatty);
        }
        
        return "redirect:/kayttajat";
    }
    
    @PostMapping("/asetaProfiilikuvaksi/{id}")
    public String asetaProfiilikuvaksi(@PathVariable long id) {
        Kuva kuva = kuvarepo.getOne(id);
        Kayttaja kayttaja = palvelut.kirjautunutKayttaja();
        kayttaja.setProfiilikuva(kuva);
        kayttajarepo.save(kayttaja);
        
        return "redirect:/kotisivu";
    }
    
    @GetMapping(path="/profiilikuva", produces="image/jpg")
    @ResponseBody
    public byte[] profiilikuva(@PathVariable long id) {
        Kayttaja kayttaja = kayttajarepo.getOne(id);
        Kuva profiilikuva = kayttaja.getProfiilikuva();
        return profiilikuva.getDatasisalto();
    }
    
}
