package bip.gravarvendedor.com.br.gateway.listener;

import com.google.gson.Gson;

import bip.gravarvendedor.com.br.domain.Vendedor;
import bip.gravarvendedor.com.br.gateway.json.VendedorJson;
import bip.gravarvendedor.com.br.service.SaveVendedorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ReadVendedorListener {

    @Autowired
    private SaveVendedorService saveVendedorService;

    @KafkaListener(topics = "${kafka.topicgravardados}", groupId = "${kafka.consumergroup}")
    public void execute(String message) throws IOException {
        VendedorJson vendedorJson = new Gson().fromJson(message, VendedorJson.class);
        saveVendedorService.execute(
                Vendedor
                        .builder()
                        .nome(vendedorJson.getNome())
                        .cpf(vendedorJson.getCpf())
                        .meta(vendedorJson.getMeta())
                        .build()
        );
    }

}
