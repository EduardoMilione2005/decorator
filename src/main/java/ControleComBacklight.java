public class ControleComBacklight extends ControleDecorator {

    private int intensidade;

    public ControleComBacklight(ControleRemoto controle) {
        this(controle, 5);
    }

    public ControleComBacklight(ControleRemoto controle, int intensidade) {
        super(controle);
        this.intensidade = Math.max(1, Math.min(10, intensidade));
    }

    public String setIntensidade(int valor) {
        this.intensidade = Math.max(1, Math.min(10, valor));
        return "💡 Backlight ajustado para " + this.intensidade + "/10.";
    }

    public int getIntensidade() {
        return intensidade;
    }

    @Override
    public String ligar() {
        return "💡 [BACKLIGHT " + intensidade + "/10] " + controle.ligar();
    }

    @Override
    public String desligar() {
        return "💡 [BACKLIGHT desligado] " + controle.desligar();
    }

    @Override
    public String mudarCanal(int canal) {
        return "💡 [BACKLIGHT ativo] " + controle.mudarCanal(canal);
    }

    @Override
    public String ajustarVolume(int nivel) {
        return "💡 [BACKLIGHT ativo] " + controle.ajustarVolume(nivel);
    }

    @Override
    public String descricao() {
        return controle.descricao() + " + Backlight(intensidade=" + intensidade + ")";
    }
}
