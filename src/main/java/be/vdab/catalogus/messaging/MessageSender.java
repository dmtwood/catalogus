package be.vdab.catalogus.messaging;

import be.vdab.catalogus.events.ArtikelGemaakt;
import be.vdab.catalogus.repositories.ArtikelGemaaktRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessageSender {
    private final ArtikelGemaaktRepository artikelGemaaktRepository;
    private final AmqpTemplate amqpTemplate;

    public MessageSender(ArtikelGemaaktRepository artikelGemaaktRepository, AmqpTemplate amqpTemplate) {
        this.artikelGemaaktRepository = artikelGemaaktRepository;
        this.amqpTemplate = amqpTemplate;
    }
    // done every 50 seconds
    @Scheduled(fixedDelay = 5_000)
    @Transactional
    void sendMessages(){
        var artikelsGemaakt = artikelGemaaktRepository.findAll();
        for (ArtikelGemaakt gemaaktArtikel : artikelsGemaakt){
            amqpTemplate.convertAndSend("catalogus", null, gemaaktArtikel);
        }
        artikelGemaaktRepository.deleteAll(artikelsGemaakt);
    }

}
