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
    //    private boolean limitesExclusivos;
    private Random random = new Random();
    private List<Integer> listaSorteados;

    public Sorteio(Integer max) {
        this.max = max;
        this.listaSorteados = new ArrayList<>();
    }

    public Sorteio(String min, String max) {
        int mMin = Integer.valueOf(min);
        int mMax = Integer.valueOf(max);

        this.min = Math.min(mMin, mMax);
        this.max = Math.max(mMin, mMax);

        this.listaSorteados = new ArrayList<>();
    }

    public int getSorteado(boolean permitirNumerosRepetidos) {
        int sorteado;

        if (permitirNumerosRepetidos) {
            if (hasLimitesMinMax()) {
                sorteado = getNumeroSorteadoComLimites();
            } else {
                sorteado = random.nextInt();
            }
        } else {
            if (hasLimitesMinMax()) {
                sorteado = getNumeroSorteadoComLimites();
                while (listaSorteados.contains(sorteado)) {
                    sorteado = getNumeroSorteadoComLimites();
                }
            } else {
                sorteado = random.nextInt();
                while (listaSorteados.contains(sorteado)) {
                    sorteado = random.nextInt();
                }
            }
        }

        listaSorteados.add(sorteado);
        return sorteado;
    }

    private int getNumeroSorteadoComLimites() {
        return min - new Random().nextInt(Math.abs(max - min));
    }

//    private boolean isDentroDosLimites(int sorteado) {
//        return limitesExclusivos ? isAlcanceLimitesExclusivos(sorteado) : isAlcanceLimitesInclusivos(sorteado);
//    }

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

    public boolean hasLimitesMinMax() {
        return hasLimiteMinimo() && hasLimiteMaximo();
    }

    private boolean hasLimiteMinimo() {
        return !Utils.isNullOrEmpty(min);
    }

    private boolean hasLimiteMaximo() {
        return !Utils.isNullOrEmpty(max);
    }

//    public boolean isLimitesExclusivos() {
//        return limitesExclusivos;
//    }

//    public void setLimitesExclusivos(boolean limitesExclusivos) {
//        this.limitesExclusivos = limitesExclusivos;
//    }

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
