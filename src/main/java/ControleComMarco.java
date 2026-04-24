import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControleComMacro extends ControleDecorator {

    private final Map<String, List<String>> macros = new HashMap<>();

    public ControleComMacro(ControleRemoto controle) {
        super(controle);
    }

    public String gravarMacro(String nome, List<String> comandos) {
        macros.put(nome, new ArrayList<>(comandos));
        return "⚡ Macro '" + nome + "' gravada com " + comandos.size() + " comando(s).";
    }

    public String executarMacro(String nome) {
        if (!macros.containsKey(nome)) {
            return "⚡ Macro '" + nome + "' não encontrada.";
        }
        StringBuilder sb = new StringBuilder("⚡ Executando macro '" + nome + "':");
        for (String cmd : macros.get(nome)) {
            sb.append("\n   → ").append(cmd);
        }
        return sb.toString();
    }

    public String listarMacros() {
        if (macros.isEmpty()) {
            return "⚡ Nenhuma macro gravada.";
        }
        return "⚡ Macros disponíveis: " + String.join(", ", macros.keySet());
    }

    @Override
    public String descricao() {
        return controle.descricao() + " + Macros";
    }
}
