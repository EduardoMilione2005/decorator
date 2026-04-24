public class ControleBasico implements ControleRemoto {

    @Override
    public String ligar() {
        return "📺 TV ligada.";
    }

    @Override
    public String desligar() {
        return "📺 TV desligada.";
    }

    @Override
    public String mudarCanal(int canal) {
        return "📺 Canal alterado para " + canal + ".";
    }

    @Override
    public String ajustarVolume(int nivel) {
        return "📺 Volume ajustado para " + nivel + ".";
    }

    @Override
    public String descricao() {
        return "Controle Básico";
    }
}
