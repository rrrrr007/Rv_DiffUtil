package com.example.ch.rv_diffutil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 来自：
 * http://www.jianshu.com/p/6f9a52365b3e
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(new ArrayList<>(Arrays.asList("11", "12", "13", "14", "15")));
        rv.setAdapter(adapter);
        findViewById(R.id.addz).setOnClickListener(this);
        findViewById(R.id.move).setOnClickListener(this);
        findViewById(R.id.change).setOnClickListener(this);
        findViewById(R.id.remove).setOnClickListener(this);
        findViewById(R.id.diff).setOnClickListener(this);
        findViewById(R.id.newdiff).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addz:
                adapter.getModel().add(0,"添加");
                adapter.notifyItemInserted(0);
                break;
            case R.id.move:
                adapter.getModel().remove(0);
                adapter.notifyItemRemoved(0);
                break;
            case R.id.change:
                adapter.getModel().remove(0);
                adapter.getModel().add(0,"改变");
                adapter.notifyItemChanged(0);
                break;
            case R.id.remove:
                String s = adapter.getModel().get(0);
                adapter.getModel().add(2,s);
                adapter.getModel().remove(0);
                adapter.notifyItemMoved(0,2);
                break;
            case R.id.diff:
                //和arrayList 对比变化notify
                ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("11", "12", "13", "14", "15"));
                DiffUtil.DiffResult result = DiffUtil.calculateDiff(new AdapterDiffCallback(adapter.getModel()
                        ,arrayList));
                result.dispatchUpdatesTo(adapter);
                //这种方法可以fix add 0 不滑动
                //xx(result)
                adapter.setModel(arrayList);
                break;
            case R.id.newdiff:
                ArrayList<String> arrayList1 = new ArrayList<>(Arrays.asList("11", "12", "13", "14", "15"));
                DiffUtil.DiffResult result1 = DiffUtil.calculateDiff(new NewDiffCallback(adapter.getModel()
                        ,arrayList1));
                result1.dispatchUpdatesTo(adapter);
                //这种方法可以fix add 0 不滑动
                //xx(result1)
                adapter.setModel(arrayList1);
                break;
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder>{
        private ArrayList<String> model;

        public ArrayList<String> getModel() {
            return model;
        }

        public void setModel(ArrayList<String> model) {
            this.model = model;
        }

        public MyAdapter(ArrayList<String> model) {
            this.model = model;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.adapter, parent,false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.tv.setText(model.get(position));
        }

        @Override
        public int getItemCount() {
            return model.size();
        }

        //高级


        @Override
        public void onBindViewHolder(Holder holder, int position, List<Object> payloads) {
            if (payloads.isEmpty()){
                super.onBindViewHolder(holder, position, payloads);
            }else {
                Bundle bundle = (Bundle) payloads.get(0);
                String str = model.get(position);
                holder.tv.setText(str);//或者holder.tv.setText(bundle.getString("str"))
            }

        }

        class Holder extends RecyclerView.ViewHolder {
            TextView tv;
            public Holder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.adapter_tv);
            }
        }
    }

    //这种方法可以fix add 0 不滑动
    private void xx(DiffUtil.DiffResult result){
        result.dispatchUpdatesTo(new ListUpdateCallback() {
            @Override
            public void onInserted(int position, int count) {
                adapter.notifyItemRangeInserted(position, count);
                if (position == 0) {
                    rv.scrollToPosition(0);
                }
            }

            @Override
            public void onRemoved(int position, int count) {
                adapter.notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                adapter.notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count, Object payload) {
                adapter.notifyItemRangeChanged(position, count, payload);
            }
        });
    }


}

