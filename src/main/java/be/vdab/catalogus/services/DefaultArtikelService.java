package be.vdab.catalogus.services;


import be.vdab.catalogus.domain.Artikel;
import be.vdab.catalogus.events.ArtikelGemaakt;
import be.vdab.catalogus.repositories.ArtikelRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultArtikelService implements ArtikelService {

    private final ArtikelRepository artikelRepository;

    // AmqpTemplate enables sending RabbitMQ messages >> needs the 'Spring for RabbitMQ' dependency in pom.xml
    private final AmqpTemplate amqpTemplate;


    public DefaultArtikelService(ArtikelRepository artikelRepository, AmqpTemplate amqpTemplate) {
        this.artikelRepository = artikelRepository;
        this.amqpTemplate = amqpTemplate;
    }


    @Override
    public void create(Artikel artikel) {
        artikelRepository.save(artikel);
        amqpTemplate.
                // convert to JSON and send with rabbitMQ
                convertAndSend(
                        "catalogus",              // name of exchange you're sending too
                        null,                    // advanced use, not in this scope
                        new ArtikelGemaakt(artikel) // object representing the new message
                );
    }


}
