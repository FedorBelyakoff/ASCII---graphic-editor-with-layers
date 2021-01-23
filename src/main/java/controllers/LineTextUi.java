package controllers;

import classes.TextPixel;
import classes.TextPosition;

import java.util.HashSet;
import java.util.Set;

public class LineTextUi {

    private Set<TextPixel> textPixels;
    private char symbol;

    public LineTextUi() {
        textPixels = new HashSet<>();
    }

    public char getChar() {
        return symbol;
    }

    public void setChar(char ch) {
        this.symbol = ch;
    }

    public Set<TextPixel> getTextPixels() {
        return textPixels;
    }

    public void draw(TextPosition firstPos,
                     TextPosition secondPos) {
        textPixels = new HashSet<>();
        drawBresenhamLine(firstPos.getColumn(), firstPos.getRow(),
                         secondPos.getColumn(), secondPos.getRow());
    }

    private int sign (int x) {
        return (x > 0) ? 1 : (x < 0) ? -1 : 0;
        //возвращает 0, если аргумент (x) равен нулю; -1, если x < 0 и 1, если x > 0.
    }

    private void drawBresenhamLine (int xstart, int ystart,
                                    int xend, int yend) {
        /**
         * xstart, ystart - начало;
         * xend, yend - конец;
         * "g.drawLine (x, y, x, y);" используем в качестве "setPixel (x, y);"
         * Можно писать что-нибудь вроде g.fillRect (x, y, 1, 1);
         */

        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;

        dx = xend - xstart;//проекция на ось икс
        dy = yend - ystart;//проекция на ось игрек

        incx = sign(dx);
        /*
         * Определяем, в какую сторону нужно будет сдвигаться. Если dx < 0, т.е. отрезок идёт
         * справа налево по иксу, то incx будет равен -1.
         * Это будет использоваться в цикле постороения.
         */
        incy = sign(dy);
        /*
         * Аналогично. Если рисуем отрезок снизу вверх -
         * это будет отрицательный сдвиг для y (иначе - положительный).
         */

        if (dx < 0) dx = -dx;//далее мы будем сравнивать: "if (dx < dy)"
        if (dy < 0) dy = -dy;//поэтому необходимо сделать dx = |dx|; dy = |dy|
        //эти две строчки можно записать и так: dx = Math.abs(dx); dy = Math.abs(dy);

        if (dx > dy)
        //определяем наклон отрезка:
        {
            /*
             * Если dx > dy, то значит отрезок "вытянут" вдоль оси икс, т.е. он скорее длинный, чем высокий.
             * Значит в цикле нужно будет идти по икс (строчка el = dx;), значит "протягивать" прямую по иксу
             * надо в соответствии с тем, слева направо и справа налево она идёт (pdx = incx;), при этом
             * по y сдвиг такой отсутствует.
             */
            pdx = incx;
            pdy = 0;
            es = dy;
            el = dx;
        } else//случай, когда прямая скорее "высокая", чем длинная, т.е. вытянута по оси y
        {
            pdx = 0;
            pdy = incy;
            es = dx;
            el = dy;//тогда в цикле будем двигаться по y
        }

        x = xstart;
        y = ystart;
        err = el / 2;


        //ставим первую точку
        textPixels.add(new TextPixel(x, y, symbol)); // TODO: 22.09.2020 Проверить позиции аргументов
        //все последующие точки возможно надо сдвигать, поэтому первую ставим вне цикла

        for (int t = 0; t < el; t++)//идём по всем точкам, начиная со второй и до последней
        {
            err -= es;
            if (err < 0) {
                err += el;
                x += incx;//сдвинуть прямую (сместить вверх или вниз, если цикл проходит по иксам)
                y += incy;//или сместить влево-вправо, если цикл проходит по y
            } else {
                x += pdx;//продолжить тянуть прямую дальше, т.е. сдвинуть влево или вправо, если
                y += pdy;//цикл идёт по иксу; сдвинуть вверх или вниз, если по y
            }

            //ставим первую точку
            textPixels.add(new TextPixel(x, y, symbol)); // TODO: 22.09.2020 Проверить позиции аргументов
        }
    }
}
