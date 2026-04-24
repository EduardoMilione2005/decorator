public class Main {

    public static void main(String[] args) {

        sep("1. Controle Básico");
        ControleRemoto basico = new ControleBasico();
        System.out.println(basico.ligar());
        System.out.println(basico.mudarCanal(5));
        System.out.println(basico.ajustarVolume(20));
        System.out.println(basico.desligar());
        System.out.println("Descrição: " + basico.descricao());

        sep("2. Controle com Voz");
        ControleRemoto comVoz = new ControleComVoz(new ControleBasico());
        System.out.println(comVoz.ligar());
        System.out.println(comVoz.mudarCanal(12));
        System.out.println("Descrição: " + comVoz.descricao());

        sep("3. Controle com Backlight (intensidade 8)");
        ControleComBacklight comBacklight = new ControleComBacklight(new ControleBasico(), 8);
        System.out.println(comBacklight.ligar());
        System.out.println(comBacklight.ajustarVolume(30));
        System.out.println(comBacklight.setIntensidade(3));
        System.out.println("Descrição: " + comBacklight.descricao());

        sep("4. Controle com Macro");
        ControleComMacro comMacro = new ControleComMacro(new ControleBasico());
        System.out.println(comMacro.gravarMacro("cinema", java.util.List.of("ligar", "canal 5", "volume 10")));
        System.out.println(comMacro.gravarMacro("esporte", java.util.List.of("ligar", "canal 13", "volume 40")));
        System.out.println(comMacro.listarMacros());
        System.out.println(comMacro.executarMacro("cinema"));
        System.out.println(comMacro.executarMacro("inexistente"));

        sep("5. Controle com Perfis");
        ControleComPerfis comPerfis = new ControleComPerfis(new ControleBasico());
        System.out.println(comPerfis.criarPerfil("João", 7, 25));
        System.out.println(comPerfis.criarPerfil("Maria", 3, 15));
        System.out.println(comPerfis.getPerfilAtivo());
        System.out.println(comPerfis.ativarPerfil("João"));
        System.out.println(comPerfis.getPerfilAtivo());

        sep("6. Composição: Voz + Backlight + Macro");
        ControleRemoto mega = new ControleComVoz(
                                new ControleComBacklight(
                                  new ControleComMacro(new ControleBasico()), 6));
        System.out.println(mega.ligar());
        System.out.println(mega.mudarCanal(9));
        System.out.println("Descrição: " + mega.descricao());
    }

    private static void sep(String titulo) {
        System.out.println("\n══════════════════════════════════════");
        System.out.println("  " + titulo);
        System.out.println("══════════════════════════════════════");
    }
}
