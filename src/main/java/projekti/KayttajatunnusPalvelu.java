package projekti;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class KayttajatunnusPalvelu implements UserDetailsService {
    
    @Autowired
    KayttajaRepository kayttajarepo;
    
    @Override
    public UserDetails loadUserByUsername(String kayttajatunnus) throws UsernameNotFoundException {
        Kayttaja kayttaja = kayttajarepo.findByKayttajatunnus(kayttajatunnus);
        
        if(kayttaja == null) {
            throw new UsernameNotFoundException("Ei löydy: " + kayttajatunnus);
        }
        
        return new org.springframework.security.core.userdetails.User(
                kayttaja.getKayttajatunnus(),
                kayttaja.getSalasana(),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}
