package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KayttajaRepository extends JpaRepository<Kayttaja, Long> {

    public Kayttaja findByKayttajatunnus(String kayttajatunnus);
    
    public List<Kayttaja> findByNimi(String nimi);
    
    public Kayttaja findByProfiilipolku(String profiilipolku);
    
}
