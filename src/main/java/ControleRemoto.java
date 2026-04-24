public interface ControleRemoto {
    String ligar();
    String desligar();
    String mudarCanal(int canal);
    String ajustarVolume(int nivel);
    String descricao();
}
