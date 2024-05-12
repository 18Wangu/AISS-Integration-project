package aiss.vimeominer.service;

import org.junit.jupiter.api.Test;

import aiss.vimeominer.model.channel.SimpleChannel;

public class VimeoAPIServiceTest {

    @Test
    public void testGetChannelInfo() {
        VimeoAPIService vimeoAPIService = new VimeoAPIService(); // Créez une instance du service
        SimpleChannel channelInfo = vimeoAPIService.getChannelInfo("1234"); // Remplacez "YOUR_CHANNEL_ID_HERE" par l'ID de votre chaîne Vimeo
        
        System.out.println(channelInfo); // Affichez les informations de la chaîne
    }
}
