import java.util.HashMap;
import java.util.Map;

public class ControleComPerfis extends ControleDecorator {

    private static class Perfil {
        final int canal;
        final int volume;
        Perfil(int canal, int volume) { this.canal = canal; this.volume = volume; }
    }

    private final Map<String, Perfil> perfis = new HashMap<>();
    private String perfilAtivo = null;

    public ControleComPerfis(ControleRemoto controle) {
        super(controle);
    }

    public String criarPerfil(String nome, int canalFavorito, int volumePadrao) {
        perfis.put(nome, new Perfil(canalFavorito, volumePadrao));
        return "👤 Perfil '" + nome + "' criado (canal=" + canalFavorito + ", volume=" + volumePadrao + ").";
    }

    public String ativarPerfil(String nome) {
        if (!perfis.containsKey(nome)) {
            return "👤 Perfil '" + nome + "' não existe.";
        }
        perfilAtivo = nome;
        Perfil p = perfis.get(nome);
        String canalMsg = controle.mudarCanal(p.canal);
        String volMsg   = controle.ajustarVolume(p.volume);
        return "👤 Perfil '" + nome + "' ativado!\n   " + canalMsg + "\n   " + volMsg;
    }

    public String getPerfilAtivo() {
        return "👤 Perfil ativo: " + (perfilAtivo != null ? perfilAtivo : "nenhum");
    }

    @Override
    public String descricao() {
        return controle.descricao() + " + Perfis de Usuário";
    }
}
