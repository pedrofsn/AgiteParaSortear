package agite.para.sortear;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by pedrofsn on 09/05/2017.
 */

public class Sorteio implements Serializable {

    private Integer min;
    private Integer max;
    private boolean limitesExclusivos;
    private Random random = new Random();
    private List<Integer> listaSorteados = new ArrayList<>();

    public Sorteio(Integer max) {
        this.max = max;
    }

    public Sorteio(String min, String max) {
        int mMin = Integer.valueOf(min);
        int mMax = Integer.valueOf(max);

        this.min = Math.min(mMin, mMax);
        this.max = Math.max(mMin, mMax);
    }

    public int getSorteado() throws QuantidadeMaximaException {
        int sorteado = Constantes.VALOR_INVALIDO;
        int quantidadeSorteados = listaSorteados.size();

        if (hasLimiteMinimo()) {
            int quantidadeMaximaPossivelNaLista = isLimitesExclusivos() ? max - min : (max - min) + 1;

            if (quantidadeMaximaPossivelNaLista == listaTamanhoOriginal) {
                throw new QuantidadeMaximaException(App.getContext().getString(R.string.o_limite_de_numeros_possiveis_foi_atingido));
            }
        }

        do {
            sorteado = getSorteado();
            if (!lista.contains(sorteado)) {
                lista.add(sorteado);
            }

            //TODO: quando muda de bounds included para bounds NOT included e fica tentando gerar, tá caindo em um looping infinito > tratar
        } while (Utils.isNullOrEmpty(lista) || lista.size() == listaTamanhoOriginal);

        return sorteado;
    }

    public int getSorteado(boolean permitirNumerosRepetidos, List<Integer> listaSorteados) {
        int sorteado = 0;

        if (hasLimiteMinimo()) {
            int limite = !limitesExclusivos ? 1 : 0;

            do {
                sorteado = random.nextInt(max + limite);
            } while (!isDentroDosLimites(sorteado));

        } else {
            sorteado = random.nextInt(max);
        }

        return sorteado;
    }

    private boolean isDentroDosLimites(int sorteado) {
        return limitesExclusivos ? isAlcanceLimitesExclusivos(sorteado) : isAlcanceLimitesInclusivos(sorteado);
    }

    private boolean isAlcanceLimitesInclusivos(int sorteado) {
        return sorteado >= min && sorteado <= max;
    }

    private boolean isAlcanceLimitesExclusivos(int sorteado) {
        return sorteado > min && sorteado < max;
    }

    public boolean isValido() {
        if (hasLimiteMinimo()) {
            return max > min && (min + 1 < max);
        }

        return hasValidacaoMinima();
    }

    private boolean hasValidacaoMinima() {
        return !Utils.isNullOrEmpty(max) && max > 0;
    }

    public boolean hasLimites() {
        return hasLimiteMinimo() && hasLimiteMaximo();
    }

    private boolean hasLimiteMinimo() {
        return !Utils.isNullOrEmpty(min);
    }

    private boolean hasLimiteMaximo() {
        return !Utils.isNullOrEmpty(max);
    }

    public boolean isLimitesExclusivos() {
        return limitesExclusivos;
    }

    public void setLimitesExclusivos(boolean limitesExclusivos) {
        this.limitesExclusivos = limitesExclusivos;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }
}
