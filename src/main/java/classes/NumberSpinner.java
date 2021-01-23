package classes;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.function.Consumer;

public class NumberSpinner extends JSpinner {
    private final int max;
    private final int min;
    private final Consumer<Integer> consumer;
    private boolean first = true;

    public NumberSpinner(int min, int max, Consumer<Integer> consumer) {
        super(new SpinnerNumberModel());
        if (min > max) {
            throw new IllegalArgumentException("Minimum > maximum!");
        }
        this.consumer = consumer;
        this.max = max;
        this.min = min;

        addChangeListener(new ModelSupplier());
    }


    private class ModelSupplier implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            if (first) {
                first = false;
                return;
            }
            final NumberSpinner spinner = (NumberSpinner) e.getSource();

            final Integer value = (Integer) spinner.getValue();


            if (value > max) {
                spinner.setValue(max);
            }

            if (value < min) {
                spinner.setValue(min);
            }

            if (value >= min && value <= max) {
                consumer.accept(value);
            }

        }
    }



}
