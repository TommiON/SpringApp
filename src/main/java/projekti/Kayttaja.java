package projekti;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.AbstractPersistable;

// Tämä luokka mallintaa yksittäisen käyttäjän

@NoArgsConstructor @AllArgsConstructor @Data
@Entity
public class Kayttaja extends AbstractPersistable<Long> {
    private String kayttajatunnus;
    private String salasana;
    private String nimi;
    private String profiilipolku;
    //@OneToMany
    @ManyToMany
    private List<Kayttaja> kaverit;
    // @OneToMany
    @ManyToMany
    private List<Kayttaja> kaveripyynnot;
    // @OneToMany
    @ManyToMany
    private List<Kuva> kuvat;
    @OneToOne
    private Kuva profiilikuva;
    @OneToMany
    private List<Viesti> viestit;
    
    public boolean onJoPyytanytKaveriksi(Kayttaja kayttaja) {
        if(this.kaveripyynnot.contains(kayttaja)) {
            return true;
        }
        return false;
    }
    
    public boolean onKaveri(Kayttaja kayttaja) {
        if(this.kaverit.contains(kayttaja)) {
            return true;
        }
        return false;
    }
}
