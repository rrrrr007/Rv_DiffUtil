package com.example.ch.rv_diffutil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ch on 2017/8/18.
 */

public class NewDiffCallback extends DiffUtil.Callback {
    List<String> oldList;
    List<String> newList;

    public NewDiffCallback(List<String> oldList, List<String> newList) {
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

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        String oldStr = oldList.get(oldItemPosition);
        String newStr = oldList.get(newItemPosition);
        Bundle payload = new Bundle();
        if (!oldStr.equals(newStr)){
            payload.putString("str",newStr);
        }
        if (payload.size()==0){
            return null;
        }else {
            return payload;
        }
    }
}
