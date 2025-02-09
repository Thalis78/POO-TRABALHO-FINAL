package Services;

import Controllers.RedeSocial;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import java.io.File;
import java.io.IOException;

public class DataProvider {

    private static final String JSON_FILE = "sistema_rede_social.json";

    public RedeSocial carregarSistema() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Habilita a formatação com indentação
        objectMapper.setDateFormat(new StdDateFormat()); // Configura o formato de data padrão
        RedeSocial redeSocial = new RedeSocial();
        File file = new File(JSON_FILE);
        if (file.exists()) {
            try {
                redeSocial = objectMapper.readValue(file, RedeSocial.class);
                System.out.println("Dados carregados do arquivo '" + JSON_FILE + "'");
            } catch (IOException e) {
                System.out.println("Erro ao carregar o arquivo JSON: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Arquivo JSON não encontrado. Criando um novo sistema.");
        }

        return redeSocial;
    }

    public void salvarSistema(RedeSocial redeSocial) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Habilita a formatação com indentação
        objectMapper.setDateFormat(new StdDateFormat()); // Configura o formato de data padrão
        try {
            objectMapper.writeValue(new File(JSON_FILE), redeSocial);
            System.out.println("Dados salvos no arquivo '" + JSON_FILE + "'");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}