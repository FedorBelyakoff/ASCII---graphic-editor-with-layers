package classes;

public class TextPosition {
    private final int column;
    private final int row;

    public TextPosition(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public TextPosition(TextPosition p) {
        column = p.column;
        row = p.row;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextPosition that = (TextPosition) o;

        if (row != that.row) return false;
        return column == that.column;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }


    @Override
    public String toString() {
        return "TextPosition{" +
                         "column=" + column +
                         ", row=" + row +
                         '}';
    }
}
