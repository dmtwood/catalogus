// represents an event in CatalogusApp
package be.vdab.catalogus.events;


import be.vdab.catalogus.domain.Artikel;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 'upgraded' to Entity Class to temp store new artikels in table artikelsgemaakt, later send and cleared and then refilled
@Entity
@Table( name = "artikelsgemaakt")
    // Artikels to (later) send to RabbitMQ
public class ArtikelGemaakt {
    @Id
    private  long id;
    private  String naam;

    // Inject full Artikel object
    public ArtikelGemaakt(Artikel artikel) {
        id = artikel.getId();
        naam = artikel.getNaam();
    }

    protected ArtikelGemaakt() {
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

}
