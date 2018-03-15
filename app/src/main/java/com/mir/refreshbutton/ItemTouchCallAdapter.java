package com.mir.refreshbutton;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018-03-15
 * @desc
 */

public interface ItemTouchCallAdapter {

    void onMove(int fromPosition, int toPosition);

    void onSwipe(int position);

}
