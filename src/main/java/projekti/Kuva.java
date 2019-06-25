package projekti;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Kuva extends AbstractPersistable<Long> {

    @ManyToOne
    private Kayttaja omistaja;
    
    @Lob
    @Type(type="org.hibernate.type.ImageType")
    private byte[] datasisalto;
    
    private String kuvateksti;
    
    private LocalDateTime aika;
    
    @OneToMany
    private List<Kayttaja> tykkaajat;
    
    private int tykkayksia;
    
    // Kommenttitoimintoa en ehtinyt ajanpuutteen vuoksi implementoida.
    // @OneToMany
    // private List<Kommentti> kommentit;
    
    // @OneToMany
    // private List<Tykkays> tykkaukset;
}
