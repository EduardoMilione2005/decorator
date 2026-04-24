import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Controle Remoto — Padrão Decorator")
class ControleRemotoTest {

    @Nested
    @DisplayName("Hierarquia de tipos")
    class HierarquiaTest {

        @Test
        @DisplayName("ControleBasico implementa ControleRemoto")
        void basicoImplementaInterface() {
            assertInstanceOf(ControleRemoto.class, new ControleBasico());
        }

        @Test
        @DisplayName("ControleComVoz implementa ControleRemoto")
        void vozImplementaInterface() {
            assertInstanceOf(ControleRemoto.class, new ControleComVoz(new ControleBasico()));
        }

        @Test
        @DisplayName("ControleComBacklight implementa ControleRemoto")
        void backlightImplementaInterface() {
            assertInstanceOf(ControleRemoto.class, new ControleComBacklight(new ControleBasico()));
        }

        @Test
        @DisplayName("ControleComMacro implementa ControleRemoto")
        void macroImplementaInterface() {
            assertInstanceOf(ControleRemoto.class, new ControleComMacro(new ControleBasico()));
        }

        @Test
        @DisplayName("ControleComPerfis implementa ControleRemoto")
        void perfisImplementaInterface() {
            assertInstanceOf(ControleRemoto.class, new ControleComPerfis(new ControleBasico()));
        }

        @Test
        @DisplayName("ControleComVoz estende ControleDecorator")
        void vozEhDecorator() {
            assertInstanceOf(ControleDecorator.class, new ControleComVoz(new ControleBasico()));
        }

        @Test
        @DisplayName("ControleComBacklight estende ControleDecorator")
        void backlightEhDecorator() {
            assertInstanceOf(ControleDecorator.class, new ControleComBacklight(new ControleBasico()));
        }
    }


    @Nested
    @DisplayName("ControleBasico")
    class ControleBasicoTest {

        private ControleBasico ctrl;

        @BeforeEach
        void setUp() { ctrl = new ControleBasico(); }

        @Test
        @DisplayName("ligar retorna mensagem correta")
        void ligar() {
            assertTrue(ctrl.ligar().contains("TV ligada"));
        }

        @Test
        @DisplayName("desligar retorna mensagem correta")
        void desligar() {
            assertTrue(ctrl.desligar().contains("TV desligada"));
        }

        @ParameterizedTest(name = "mudarCanal({0})")
        @ValueSource(ints = {1, 5, 12, 100})
        @DisplayName("mudarCanal inclui o número do canal")
        void mudarCanal(int canal) {
            assertTrue(ctrl.mudarCanal(canal).contains(String.valueOf(canal)));
        }

        @ParameterizedTest(name = "ajustarVolume({0})")
        @ValueSource(ints = {0, 10, 50, 100})
        @DisplayName("ajustarVolume inclui o nível")
        void ajustarVolume(int nivel) {
            assertTrue(ctrl.ajustarVolume(nivel).contains(String.valueOf(nivel)));
        }

        @Test
        @DisplayName("descricao menciona Básico")
        void descricao() {
            assertTrue(ctrl.descricao().toLowerCase().contains("sico")); // Bás-ico
        }
    }


    @Nested
    @DisplayName("ControleComVoz")
    class ControleComVozTest {

        private ControleComVoz ctrl;

        @BeforeEach
        void setUp() { ctrl = new ControleComVoz(new ControleBasico()); }

        @Test void ligarContemVoz()            { assertTrue(ctrl.ligar().contains("VOZ")); }
        @Test void ligarDelegaAoBasico()        { assertTrue(ctrl.ligar().contains("TV ligada")); }
        @Test void desligarContemVoz()          { assertTrue(ctrl.desligar().contains("VOZ")); }
        @Test void mudarCanalContemNumero()     { assertTrue(ctrl.mudarCanal(7).contains("7")); }
        @Test void ajustarVolumeContemNivel()   { assertTrue(ctrl.ajustarVolume(15).contains("15")); }
        @Test void descricaoInclui()           {
            String d = ctrl.descricao().toLowerCase();
            assertTrue(d.contains("oz"));
            assertTrue(d.contains("sico"));
        }
    }


    @Nested
    @DisplayName("ControleComBacklight")
    class ControleComBacklightTest {

        private ControleComBacklight ctrl;

        @BeforeEach
        void setUp() { ctrl = new ControleComBacklight(new ControleBasico(), 7); }

        @Test void ligarContemBacklight()  { assertTrue(ctrl.ligar().contains("BACKLIGHT")); }
        @Test void intensidadeNaDescricao(){ assertTrue(ctrl.descricao().contains("7")); }

        @Test
        void setIntensidadeDentroDoLimite() {
            ctrl.setIntensidade(3);
            assertEquals(3, ctrl.getIntensidade());
        }

        @Test
        void setIntensidadeLimiteSuperior() {
            ctrl.setIntensidade(999);
            assertEquals(10, ctrl.getIntensidade());
        }

        @Test
        void setIntensidadeLimiteInferior() {
            ctrl.setIntensidade(-5);
            assertEquals(1, ctrl.getIntensidade());
        }

        @Test void desligarContemBacklight(){ assertTrue(ctrl.desligar().contains("BACKLIGHT")); }

        @Test
        void intensidadePadrao() {
            ControleComBacklight padrao = new ControleComBacklight(new ControleBasico());
            assertEquals(5, padrao.getIntensidade());
        }
    }


    @Nested
    @DisplayName("ControleComMacro")
    class ControleComMacroTest {

        private ControleComMacro ctrl;

        @BeforeEach
        void setUp() { ctrl = new ControleComMacro(new ControleBasico()); }

        @Test
        void gravarMacro() {
            String r = ctrl.gravarMacro("cinema", List.of("ligar", "canal 5", "volume 10"));
            assertTrue(r.contains("cinema"));
            assertTrue(r.contains("3"));
        }

        @Test
        void executarMacroExistente() {
            ctrl.gravarMacro("esporte", List.of("ligar", "canal 13"));
            String r = ctrl.executarMacro("esporte");
            assertTrue(r.contains("esporte"));
            assertTrue(r.contains("canal 13"));
        }

        @Test
        void executarMacroInexistente() {
            assertTrue(ctrl.executarMacro("naoExiste").contains("encontrada"));
        }

        @Test
        void listarMacrosVazio() {
            assertTrue(ctrl.listarMacros().contains("Nenhuma"));
        }

        @Test
        void listarMacrosComItens() {
            ctrl.gravarMacro("m1", List.of("cmd"));
            ctrl.gravarMacro("m2", List.of("cmd"));
            String r = ctrl.listarMacros();
            assertTrue(r.contains("m1"));
            assertTrue(r.contains("m2"));
        }

        @Test void delegacaoLigar() { assertTrue(ctrl.ligar().contains("TV ligada")); }
        @Test void descricaoInclui() { assertTrue(ctrl.descricao().contains("Macro")); }
    }


    @Nested
    @DisplayName("ControleComPerfis")
    class ControleComPerfisTest {

        private ControleComPerfis ctrl;

        @BeforeEach
        void setUp() { ctrl = new ControleComPerfis(new ControleBasico()); }

        @Test
        void criarPerfil() {
            assertTrue(ctrl.criarPerfil("João", 7, 25).contains("João"));
        }

        @Test
        void ativarPerfilExistente() {
            ctrl.criarPerfil("Maria", 3, 15);
            String r = ctrl.ativarPerfil("Maria");
            assertTrue(r.contains("Maria"));
            assertTrue(r.contains("3"));
            assertTrue(r.contains("15"));
        }

        @Test
        void ativarPerfilInexistente() {
            assertTrue(ctrl.ativarPerfil("Fantasma").contains("existe"));
        }

        @Test void perfilAtivoInicial() { assertTrue(ctrl.getPerfilAtivo().contains("nenhum")); }

        @Test
        void perfilAtivoAposAtivacao() {
            ctrl.criarPerfil("Carlos", 10, 20);
            ctrl.ativarPerfil("Carlos");
            assertTrue(ctrl.getPerfilAtivo().contains("Carlos"));
        }

        @Test void delegacaoDesligar() { assertTrue(ctrl.desligar().contains("TV desligada")); }
        @Test void descricaoInclui()   { assertTrue(ctrl.descricao().toLowerCase().contains("perfis")); }
    }


    @Nested
    @DisplayName("Composição de Decorators")
    class ComposicaoTest {

        @Test
        @DisplayName("Voz sobre Backlight: ligar encadeia prefixos")
        void vozSobreBacklight() {
            ControleRemoto ctrl = new ControleComVoz(new ControleComBacklight(new ControleBasico(), 8));
            String r = ctrl.ligar();
            assertTrue(r.contains("VOZ"));
            assertTrue(r.contains("BACKLIGHT"));
            assertTrue(r.contains("TV ligada"));
        }

        @Test
        @DisplayName("Descricao encadeada lista todos os decorators")
        void descricaoEncadeada() {
            ControleRemoto ctrl = new ControleComMacro(
                                    new ControleComVoz(
                                      new ControleComBacklight(new ControleBasico(), 3)));
            String d = ctrl.descricao().toLowerCase();
            assertTrue(d.contains("backlight"));
            assertTrue(d.contains("oz"));
            assertTrue(d.contains("macro"));
        }

        @Test
        @DisplayName("Triple wrap: mudarCanal propaga corretamente")
        void tripleWrapMudarCanal() {
            ControleRemoto ctrl = new ControleComVoz(
                                    new ControleComPerfis(
                                      new ControleComMacro(new ControleBasico())));
            assertTrue(ctrl.mudarCanal(99).contains("99"));
        }

        @Test
        @DisplayName("Backlight sobre Macro: ajustarVolume exibe BACKLIGHT")
        void backlightSobreMacro() {
            ControleRemoto ctrl = new ControleComBacklight(
                                    new ControleComMacro(new ControleBasico()), 5);
            String r = ctrl.ajustarVolume(50);
            assertTrue(r.contains("50"));
            assertTrue(r.contains("BACKLIGHT"));
        }

        @Test
        @DisplayName("Perfis + Voz: ativar perfil delega ao encadeamento")
        void perfisEVozCombinados() {
            ControleComPerfis ctrl = new ControleComPerfis(new ControleComVoz(new ControleBasico()));
            ctrl.criarPerfil("Admin", 1, 5);
            assertTrue(ctrl.ativarPerfil("Admin").contains("Admin"));
        }
    }
}
