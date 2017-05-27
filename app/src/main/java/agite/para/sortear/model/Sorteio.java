package agite.para.sortear.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import agite.para.sortear.domain.OnSorteiroRealizado;
import agite.para.sortear.utils.RandomNumberGenerator;
import agite.para.sortear.utils.Utils;

/**
 * Created by pedrofsn on 09/05/2017.
 */

public class Sorteio implements Serializable {

    private Integer min;
    private Integer max;
    private OnSorteiroRealizado callback;
    private Random random = new Random();
    private List<Integer> listaSorteados = new ArrayList<>();
    private List<Integer> valoresPossiveis;
    private RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

    public Sorteio(Integer max) {
        this.max = max;
        popularValoresPossiveis();
    }

    public Sorteio(String min, String max) {
        int mMin = Integer.valueOf(min);
        int mMax = Integer.valueOf(max);

        this.min = Math.min(mMin, mMax);
        this.max = Math.max(mMin, mMax);

        popularValoresPossiveis();
    }

    private void popularValoresPossiveis() {
        valoresPossiveis = new ArrayList<>();

        if (Utils.isNullOrEmpty(min)) {
            min = 0;
        }

        for (int i = min; i <= max; i++) {
            valoresPossiveis.add(i);
        }
    }

    public Integer getSorteado(boolean permitirNumerosRepetidos) {
        Integer sorteado = null;

        if (permitirNumerosRepetidos) {
            if (hasLimitesMinMax()) {
                sorteado = getNumeroSorteadoComLimites();

            } else if (hasLimiteMaximo()) {
                sorteado = random.nextInt(max + 1);
            }
        } else {
            if (!isTodasPossibilidadesAtingidas()) {
                if (hasLimitesMinMax()) {
                    sorteado = getNumeroSorteadoComLimites();
                    while (listaSorteados.contains(sorteado)) {
                        sorteado = getNumeroSorteadoComLimites();
                    }

                } else if (hasLimiteMaximo()) {
                    sorteado = random.nextInt(max + 1);
                    while (listaSorteados.contains(sorteado)) {
                        sorteado = random.nextInt(max + 1);
                    }
                }
            }
        }

        listaSorteados.add(sorteado);

        if (!Utils.isNullOrEmpty(callback)) {
            callback.onNumeroSorteado();
        }

        return sorteado;
    }

    public boolean isTodasPossibilidadesAtingidas() {
        List<Integer> temp = valoresPossiveis;
        temp.removeAll(listaSorteados);
        return temp.size() == 0;
    }

    private int getNumeroSorteadoComLimites() {
        return randomNumberGenerator.nextInt(min, max);
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

    public boolean hasLimitesMinMax() {
        return hasLimiteMinimo() && hasLimiteMaximo();
    }

    private boolean hasLimiteMinimo() {
        return !Utils.isNullOrEmpty(min);
    }

    private boolean hasLimiteMaximo() {
        return !Utils.isNullOrEmpty(max);
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    public List<Integer> getListaSorteados() {
        return listaSorteados;
    }

    public void setCallback(OnSorteiroRealizado callback) {
        this.callback = callback;
    }
}
