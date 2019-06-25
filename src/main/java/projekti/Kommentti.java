package projekti;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

// ei implementoitu

@AllArgsConstructor @NoArgsConstructor @Data
@Entity
public class Kommentti extends AbstractPersistable<Long>{
    
    private String teksti;
    
    private LocalDateTime aika;
    
    @OneToOne
    private Kayttaja lahettaja;
}
