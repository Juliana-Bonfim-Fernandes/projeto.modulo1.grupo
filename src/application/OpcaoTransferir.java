package application;

import entities.Cliente;
import entities.ContaNova;
import entities.ContasCliente;
import entities.enums.TipoConta;
import entities.enums.TipoContaJuridica;
import entities.enums.TipoPessoa;

import java.math.BigDecimal;
import java.util.Scanner;

public class OpcaoTransferir {

    int j = 0;
    GestaoClientesContas gestaoClientesContas = new GestaoClientesContas();

    public void tranferir(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o documento do responsavel (CPF ou CNPJ): ");
        String documento = scanner.next();


        CriadorConta criadorConta = new CriadorConta();
        String tipoPessoaDoc = criadorConta.TipoPessoa(documento);


        System.out.println("Digite o tipo da conta desejado: ");
        if (tipoPessoaDoc.equals(TipoPessoa.PESSOA_FISICA.toString())) {
            System.out.println(j + " - " + TipoConta.CONTA_CORRENTE);
            j++;
            System.out.println(j + " - " + TipoConta.CONTA_POUPANCA);
            j++;
            System.out.println(j + " - " + TipoConta.CONTA_INVESTIMENTO);
            j++;
        } else {
            for (
                    TipoContaJuridica tipoContaJuridica : TipoContaJuridica.values()) {
                System.out.println(j + " - " + tipoContaJuridica);
                j++;
            }
        }
        int tipoContaEscolhido = scanner.nextInt();
        TipoConta tipoconta = null;
        switch (tipoContaEscolhido) {
            case 0:
                tipoconta = TipoConta.CONTA_CORRENTE;
                break;
            case 1:
                tipoconta = TipoConta.CONTA_POUPANCA;
                break;
            case 2:
                tipoconta = TipoConta.CONTA_INVESTIMENTO;
                break;
        }

        System.out.println("Digite o valor para transferir: ");
        BigDecimal vltranf = scanner.nextBigDecimal();

        ContaNova contaNova = gestaoClientesContas.buscaContaCliente(documento, tipoconta);
        if (contaNova!= null){
            contaNova.sacar(vltranf);
           RealizaTranferencia realizaTranferencia = new RealizaTranferencia();
           realizaTranferencia.efetivatransferencia(vltranf);
        }else {
            System.out.println(" xxxxxx Cliente não possui conta. Crie uma conta para realizar a operação xxxxxx");
        }

    }
}
