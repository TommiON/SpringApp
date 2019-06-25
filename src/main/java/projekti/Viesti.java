package projekti;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@AllArgsConstructor @NoArgsConstructor @Data
@Entity
public class Viesti extends AbstractPersistable<Long> {
    
    private String teksti;
    
    private LocalDateTime aika;
    
    @OneToOne
    private Kayttaja lahettaja;
    
    //@OneToMany
    @ManyToMany
    private List<Kayttaja> tykkaajat;
    
    private int tykkayksia;
    
    // Kommenttitoimintoa en ehtinyt ajanpuutteen vuoksi implementoida.
    // @OneToMany
    // private List<Kommentti> kommentit;
    
    /*
    @OneToMany
    private List<Tykkays> tykkaukset;
    */
}
