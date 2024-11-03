package br.com.project.postgre;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class automation {
    private final App daoApp;

    public automation(App daoApp){
        this.daoApp = daoApp;
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void executarAutomacao(){
        daoApp.adicionarProduto("Produto Automático", Math.random() * 100);
        System.out.println("Produto adicionado automaticamente.");
    }

    @Scheduled(cron = "0 0 * * * *")
    public void produtoScheduler(){
        daoApp.listarProduto();
        System.out.println("Produtos listados automaticamente");
    }
}
