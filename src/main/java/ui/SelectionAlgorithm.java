package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Cursor.*;

/**
 * This class allows you to stretch and move any component
 * with the right-hand pressed mouse.
 *
 * After created, this object needs to be added
 * to the component as MouseListener and MouseMotionListener.
 */
public class SelectionAlgorithm extends MouseAdapter {
    protected final JComponent selection;
    private final int borderSize;

    protected int cursorX;
    protected int cursorY;
    protected boolean onRight;
    protected boolean onLeft;
    protected boolean onTop;
    protected boolean onBottom;

    private boolean mouseDragged;
    private boolean moveEnabled;


    //After created, this object needs to be added
    // to the component as MouseListener and MouseMotionListener.
    public SelectionAlgorithm(JComponent selection,
                              int borderSize) {
        this.selection = selection;
        this.borderSize = borderSize;
        moveEnabled = true;
    }


    public void setMoveEnabled(boolean moveEnabled) {
        this.moveEnabled = moveEnabled;
    }

    public boolean isCloseToTheSides() {
        return onLeft || onRight || onTop || onBottom;
    }

    public boolean isMouseDragged() {
        return mouseDragged;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!mouseDragged) {
            cursorX = e.getX();
            cursorY = e.getY();
            mouseDragged = true;
            identifySides();
        }
        final int dx = e.getX() - cursorX;
        final int dy = e.getY() - cursorY;

        checkBorderCrossings(dx, dy);
        resizeSelection(dx, dy);
        updateCursor();
    }

    private void resizeSelection(int dx, int dy) {
        int xPos = selection.getX();
        int yPos = selection.getY();
        int width = selection.getWidth();
        int height = selection.getHeight();

        if (onLeft) {
            xPos += dx;
            width -= dx;
        } else if (onRight) {
            width += dx;
            cursorX += dx;
        }

        if (onTop) {
            yPos += dy;
            height -= dy;
        } else if (onBottom) {
            height += dy;
            cursorY += dy;
        }

        if (!isCloseToTheSides() && moveEnabled) {
            xPos += dx;
            yPos += dy;
        }

        selection.setBounds(xPos, yPos, width, height);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!mouseDragged) {
            cursorX = e.getX();
            cursorY = e.getY();
            identifySides();
            updateCursor();
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDragged = false;
        updateCursor();
    }

    private void identifySides() {
        onLeft = cursorX < borderSize;
        onRight = cursorX > selection.getWidth() - borderSize;
        onTop = cursorY < borderSize;
        onBottom = cursorY > selection.getHeight() - borderSize;

        if (onRight && onLeft) {
            onLeft = false;
        }

        if (onTop && onBottom) {
            onBottom = false;
        }
    }

    private void checkBorderCrossings(int dx, int dy) {
        final boolean fromRightToLeft = onRight && (cursorX + dx <= 0);
        if (fromRightToLeft) {
            onRight = false;
            onLeft = true;
        }

        final boolean fromLeftToRight = onLeft && (cursorX + dx >= selection.getWidth());
        if (fromLeftToRight) {
            onLeft = false;
            onRight = true;
        }

        final boolean fromBottomToTop = onBottom && (cursorY + dy <= 0);
        if (fromBottomToTop) {
            onBottom = false;
            onTop = true;
        }

        final boolean fromTopToBottom = onTop && (cursorY + dy >= selection.getHeight());
        if (fromTopToBottom) {
            onTop = false;
            onBottom = true;
        }
    }

    protected void updateCursor() {
        if (onTop && onLeft) {
            setCursor(NW_RESIZE_CURSOR);
        } else if (onBottom && onLeft) {
            setCursor(SW_RESIZE_CURSOR);
        } else if (onTop && onRight) {
            setCursor(NE_RESIZE_CURSOR);
        } else if (onBottom && onRight) {
            setCursor(SE_RESIZE_CURSOR);
        } else if (onLeft) {
            setCursor(W_RESIZE_CURSOR);
        } else if (onRight) {
            setCursor(E_RESIZE_CURSOR);
        } else if (onTop) {
            setCursor(N_RESIZE_CURSOR);
        } else if (onBottom) {
            setCursor(S_RESIZE_CURSOR);
        } else {
            if (moveEnabled) {
                showMoveCursor();
            } else {
                setCursor(DEFAULT_CURSOR);
            }
        }
    }

    private void showMoveCursor() {
        if (mouseDragged) {
            setCursor(HAND_CURSOR);
        } else {
            setCursor(MOVE_CURSOR);
        }
    }

    private void setCursor(int cursorType) {
        selection.setCursor(getPredefinedCursor(cursorType));
    }
}
