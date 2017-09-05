package com.example.ominext.storedeviceonline.ui.find;

import java.util.ArrayList;

/**
 * Created by Ominext on 9/5/2017.
 */

public interface FindView {
    void getListFindSuccess(ArrayList<String> listFind);

    void getListFindFailed(String s);
}
