package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class Palvelut {
    
    @Autowired
    private KayttajaRepository kayttajarepo;
    
    public Kayttaja kirjautunutKayttaja() {
        Authentication autentikaatio = SecurityContextHolder.getContext().getAuthentication();
        String kayttajatunnus = autentikaatio.getName();
        return kayttajarepo.findByKayttajatunnus(kayttajatunnus);
    }
}
