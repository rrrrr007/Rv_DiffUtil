package com.example.ch.rv_diffutil;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ch on 2017/8/18.
 */

public class AdapterDiffCallback extends DiffUtil.Callback {
    List<String> oldList;
    List<String> newList;

    public AdapterDiffCallback(List<String> oldList, List<String> newList) {
        if (null ==oldList){
            this.oldList = new ArrayList<>();
        }else {
            this.oldList = oldList;
        }
        if (null ==newList){
            this.newList = new ArrayList<>();
        }else {
            this.newList = newList;
        }

    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getClass().equals(newList.get(newItemPosition).getClass());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }


}
