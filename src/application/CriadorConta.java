package application;

import entities.enums.TipoConta;
import entities.enums.TipoContaJuridica;
import entities.enums.TipoPessoa;

import java.util.Optional;
import java.util.Scanner;

public class CriadorConta {

    Scanner sc = new Scanner(System.in);
    GestaoClientesContas gestaoClientesContas = new GestaoClientesContas();

    public void criadorConta() {

        int i = 0;
        int j = 0;

        System.out.println("Digite o nome do responsavel: ");
        String nome = sc.next();

        System.out.println("Digite o documento do responsavel (CPF ou CNPJ): ");
        String documento = sc.next();

        String tipoPessoaIn2 = TipoPessoa(documento);

        System.out.println("Digite o tipo da conta desejado: ");
        if (tipoPessoaIn2.equals(TipoPessoa.PESSOA_FISICA.toString())) {
            for (
                    TipoConta tipoConta : TipoConta.values()) {
                System.out.println(j + " - " + tipoConta);
                j++;
            }
        } else {
            for (
                    TipoContaJuridica tipoContaJuridica : TipoContaJuridica.values()) {
                System.out.println(j + " - " + tipoContaJuridica);
                j++;
            }
        }

        int tipoContaIn = sc.nextInt();
        String tipoContaIn2 = TipoConta(tipoContaIn, tipoPessoaIn2, j);
        InseriConta(nome, documento, tipoPessoaIn2, tipoContaIn2);
    }

    public static String TipoPessoa(String documento) {

        String tipoPessoaIn1 = null;
        switch (documento.length()) {
            case 11:
                tipoPessoaIn1 = String.valueOf(TipoPessoa.PESSOA_FISICA);
                break;
            case 14:
                tipoPessoaIn1 = String.valueOf(TipoPessoa.PESSOA_JURIDICA);
                break;
            default:
                System.out.println("Valor de documento Inválido");
                break;
        }
        return tipoPessoaIn1;
    }

    public static String TipoConta(int tipoContaIn, String tipoPessoaIn2, int j) {

        String tipoContaIn2 = null;
        if (tipoContaIn >= j) {
            System.out.println("Valor de Tipo Conta Inválido");
        } else if (tipoPessoaIn2.equals(TipoPessoa.PESSOA_FISICA.toString())) {
            TipoConta[] tipoContaIn1 = TipoConta.values();
            tipoContaIn2 = String.valueOf(tipoContaIn1[tipoContaIn]);
        } else {
            TipoContaJuridica[] tipoContaIn1 = TipoContaJuridica.values();
            tipoContaIn2 = String.valueOf(tipoContaIn1[tipoContaIn]);
        }
        return tipoContaIn2;
    }

    public static void InseriConta(String nome, String documento, String tipoPessoaIn2, String tipoContaIn2) {
        GestaoClientesContas gestaoClientesContas = new GestaoClientesContas();

        IniciaIdsMax();

        int idContaCorrente = gestaoClientesContas.getIdContaCorrenteMax();
        int idContaPoupanca = gestaoClientesContas.getIdContaPoupancaMax();
        int idContaInvestimento = gestaoClientesContas.getIdContaInvestimentoMax();

        if (tipoContaIn2.equals(TipoConta.CONTA_CORRENTE.toString())) {
            idContaCorrente += 1;
            gestaoClientesContas.setIdContaCorrenteMax(idContaCorrente);
        } else if (tipoContaIn2.equals(TipoConta.CONTA_INVESTIMENTO.toString())) {
            idContaInvestimento += 1;
            gestaoClientesContas.setIdContaInvestimentoMax(idContaInvestimento);
        } else {
            idContaPoupanca += 1;
            gestaoClientesContas.setIdContaPoupancaMax(idContaPoupanca);
        }

        if (!gestaoClientesContas.validaIdCliente(documento, nome, tipoPessoaIn2)) {
            gestaoClientesContas.criarCliente(documento, nome, tipoPessoaIn2);
            gestaoClientesContas.criarNovoCadastro(documento, idContaCorrente, idContaPoupanca, idContaInvestimento);
            gestaoClientesContas.criarConta(idContaCorrente, idContaPoupanca, idContaInvestimento, documento, tipoContaIn2);
            System.out.println("Cliente cadastrado e conta criada com sucesso!");
        } else {
            boolean ctrlCadastro = false;
            gestaoClientesContas.validaContasCliente(documento,idContaCorrente,idContaPoupanca,idContaInvestimento);
            if (gestaoClientesContas.getIdContaCorrente()!=0 && tipoContaIn2.equals(TipoConta.CONTA_CORRENTE.toString())){
                System.out.println("Cliente já possui conta corrente");
                ctrlCadastro = true;
            }
            if (gestaoClientesContas.getIdContaInvestimento()!=0 && tipoContaIn2.equals(TipoConta.CONTA_INVESTIMENTO.toString())) {
                System.out.println("Cliente ja possui conta investimento");
                ctrlCadastro = true;
            }
            if (gestaoClientesContas.getIdContaPoupanca()!=0 && tipoContaIn2.equals(TipoConta.CONTA_POUPANCA.toString())){
                System.out.println("Cliente ja possui conta conta poupança");
                ctrlCadastro = true;
            }
            if(!ctrlCadastro){
                if(tipoContaIn2.equals(TipoConta.CONTA_CORRENTE.toString())){
                    gestaoClientesContas.atualizaNovoCadastro(documento, idContaCorrente, GestaoClientesContas.getIdContaPoupanca(), GestaoClientesContas.getIdContaInvestimento());
                    gestaoClientesContas.atualizaConta(idContaCorrente, GestaoClientesContas.getIdContaPoupanca(), GestaoClientesContas.getIdContaInvestimento(), documento, tipoContaIn2);
                    System.out.println("Conta corrente criada para o cliente!");
                } else if (tipoContaIn2.equals(TipoConta.CONTA_POUPANCA.toString())) {
                    gestaoClientesContas.atualizaNovoCadastro(documento, GestaoClientesContas.getIdContaCorrente(), idContaPoupanca, GestaoClientesContas.getIdContaInvestimento());
                    gestaoClientesContas.atualizaConta(GestaoClientesContas.getIdContaCorrente(), idContaPoupanca, GestaoClientesContas.getIdContaInvestimento(), documento, tipoContaIn2);
                    System.out.println("Conta poupança criada para o cliente!");
                } else if (tipoContaIn2.equals(TipoConta.CONTA_INVESTIMENTO.toString())) {
                    gestaoClientesContas.atualizaNovoCadastro(documento, GestaoClientesContas.getIdContaCorrente(), GestaoClientesContas.getIdContaPoupanca(), idContaInvestimento);
                    gestaoClientesContas.atualizaConta(GestaoClientesContas.getIdContaCorrente(), GestaoClientesContas.getIdContaPoupanca(), idContaInvestimento, documento, tipoContaIn2);
                    System.out.println("Conta investimento criada para o cliente!");
                }else{
                    System.out.println("Conta nao identificada");
                }
            }
        }
    }

    public static void IniciaIdsMax(){
        GestaoClientesContas gestaoClientesContas = new GestaoClientesContas();

        Optional<Integer> gestaoClientesContas1 = Optional.ofNullable(gestaoClientesContas.getIdContaCorrenteMax());
        if (!gestaoClientesContas1.isPresent()) {
            gestaoClientesContas.setIdContaCorrenteMax(0);
        }
        Optional<Integer> gestaoClientesContas2 = Optional.ofNullable(gestaoClientesContas.getIdContaPoupancaMax());
        if (!gestaoClientesContas1.isPresent()) {
            gestaoClientesContas.setIdContaPoupancaMax(0);
        }
        Optional<Integer> gestaoClientesContas3 = Optional.ofNullable(gestaoClientesContas.getIdContaInvestimentoMax());
        if (!gestaoClientesContas1.isPresent()) {
            gestaoClientesContas.setIdContaInvestimentoMax(0);
        }
    }
}
