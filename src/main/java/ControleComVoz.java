public class ControleComVoz extends ControleDecorator {

    public ControleComVoz(ControleRemoto controle) {
        super(controle);
    }

    @Override
    public String ligar() {
        return "🎙️ [VOZ] Comando reconhecido: ligar → " + controle.ligar();
    }

    @Override
    public String desligar() {
        return "🎙️ [VOZ] Comando reconhecido: desligar → " + controle.desligar();
    }

    @Override
    public String mudarCanal(int canal) {
        return "🎙️ [VOZ] Comando reconhecido: canal " + canal + " → " + controle.mudarCanal(canal);
    }

    @Override
    public String ajustarVolume(int nivel) {
        return "🎙️ [VOZ] Comando reconhecido: volume " + nivel + " → " + controle.ajustarVolume(nivel);
    }

    @Override
    public String descricao() {
        return controle.descricao() + " + Controle por Voz";
    }
}
