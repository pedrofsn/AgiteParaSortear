package agite.para.sortear.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import agite.para.sortear.domain.OnSorteiroRealizado;
import agite.para.sortear.utils.Constantes;
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
    private RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

    public Sorteio(Integer max) {
        this.max = max;
    }

    public Sorteio(String min, String max) {
        int mMin = Integer.valueOf(min);
        int mMax = Integer.valueOf(max);

        this.min = Math.min(mMin, mMax);
        this.max = Math.max(mMin, mMax);
    }

    public int getSorteado(boolean permitirNumerosRepetidos) {
        int sorteado = Constantes.VALOR_INVALIDO;

        if (permitirNumerosRepetidos) {
            if (hasLimitesMinMax()) {
                sorteado = getNumeroSorteadoComLimites();

            } else if (hasLimiteMaximo()) {
                sorteado = random.nextInt(max + 1);
            }
        } else {
            if (hasLimitesMinMax()) {
                sorteado = getNumeroSorteadoComLimites();
                while (listaSorteados.contains(sorteado) && isTodasPossibilidadesAtingidas(listaSorteados)) {
                    sorteado = getNumeroSorteadoComLimites();
                }

            } else if (hasLimiteMaximo()) {
                sorteado = random.nextInt(max + 1);
                while (listaSorteados.contains(sorteado) && isTodasPossibilidadesAtingidas(listaSorteados)) {
                    sorteado = random.nextInt(max + 1);
                }
            }
        }

        listaSorteados.add(sorteado);

        if (!Utils.isNullOrEmpty(callback)) {
            callback.onNumeroSorteado();
        }

        return sorteado;
    }

    private boolean isTodasPossibilidadesAtingidas(List<Integer> list) {
        if (!Utils.isNullOrEmpty(list)) {
            int temp = max;
            while (list.contains(temp)) {
                temp = temp - 1;

                if ((!Utils.isNullOrEmpty(min) && max.intValue() == min.intValue()) || (null == min && 0 == max)) {
                    Utils.log("estourou!");
                    return true;
                }
            }
        }
        return false;
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

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public List<Integer> getListaSorteados() {
        return listaSorteados;
    }

    public void setCallback(OnSorteiroRealizado callback) {
        this.callback = callback;
    }
}
