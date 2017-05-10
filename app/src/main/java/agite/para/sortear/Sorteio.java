package agite.para.sortear;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by pedrofsn on 09/05/2017.
 */

public class Sorteio implements Serializable {

    private Integer min;
    private Integer max;
    private boolean limitesExclusivos;

    public Sorteio(Integer max) {
        this.max = max;
    }

    public Sorteio(String min, String max) {
        int mMin = Integer.valueOf(min);
        int mMax = Integer.valueOf(max);

        this.min = Math.min(mMin, mMax);
        this.max = Math.max(mMin, mMax);
    }

    public int getSorteado() {
        Random random = new Random();
        int sorteado = 0;

        if (isLimitesAtivados()) {
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
        if (isLimitesAtivados()) {
            return max > min && (min + 1 < max);
        }

        return hasValidacaoMinima();
    }

    private boolean hasValidacaoMinima() {
        return !Utils.isNullOrEmpty(max) && max > 0;
    }

    public boolean isLimitesAtivados() {
        return !Utils.isNullOrEmpty(min) && !Utils.isNullOrEmpty(max);
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
