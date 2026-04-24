public abstract class ControleDecorator implements ControleRemoto {

    protected final ControleRemoto controle;

    public ControleDecorator(ControleRemoto controle) {
        this.controle = controle;
    }

    @Override
    public String ligar() {
        return controle.ligar();
    }

    @Override
    public String desligar() {
        return controle.desligar();
    }

    @Override
    public String mudarCanal(int canal) {
        return controle.mudarCanal(canal);
    }

    @Override
    public String ajustarVolume(int nivel) {
        return controle.ajustarVolume(nivel);
    }

    @Override
    public String descricao() {
        return controle.descricao();
    }
}
