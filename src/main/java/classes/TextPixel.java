package classes;

public class TextPixel {
    private int column;
    private int row;
    private char symbol;

    public TextPixel(int column, int row, char symbol) {
        this.column = column;
        this.row = row;
        this.symbol = symbol;
    }

    public TextPixel(TextPosition position, char symbol) {
        this(position.getColumn(), position.getRow(), symbol);
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public TextPosition getPosition() {
        return new TextPosition(column, row);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextPixel textPixel = (TextPixel) o;

        if (column != textPixel.column) return false;
        if (row != textPixel.row) return false;
        return symbol == textPixel.symbol;
    }

    @Override
    public String toString() {
        return "TextPixel{" +
                         "column=" + column +
                         ", row=" + row +
                         ", symbol=" + symbol +
                         '}';
    }

    @Override
    public int hashCode() {
        int result = column;
        result = 31 * result + row;
        result = 31 * result + (int) symbol;
        return result;
    }
}
