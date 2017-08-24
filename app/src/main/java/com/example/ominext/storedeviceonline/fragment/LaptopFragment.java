package com.example.ominext.storedeviceonline.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ominext.storedeviceonline.R;
import com.example.ominext.storedeviceonline.adapter.LaptopAdapter;
import com.example.ominext.storedeviceonline.adapter.NewProductAdapter;
import com.example.ominext.storedeviceonline.data.model.Product;
import com.example.ominext.storedeviceonline.listener.OnItemClickListener;
import com.example.ominext.storedeviceonline.until.CheckConnection;
import com.example.ominext.storedeviceonline.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LaptopFragment extends Fragment implements OnItemClickListener {
    @BindView(R.id.rv_laptop)
    RecyclerView rvLaptop;
    Unbinder unbinder;
    LaptopAdapter adapter;
    View itemView;
    List<Product> productList = new ArrayList<>();
    int idProductType = 0;
    int idProduct = 0;
    String nameProduct = "";
    int priceProduct = 0;
    String imageProduct = "";
    String describeProduct = "";
    boolean loading = false;
//    boolean limitData = false;
//    HandlerLaptop handlerLaptop;

    public LaptopFragment() {
    }

    public static LaptopFragment newInstance() {
        LaptopFragment fragment = new LaptopFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemView = inflater.inflate(R.layout.progressbar, null);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_laptop, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void init() {
        if (CheckConnection.haveNetWorkConnection(getContext())) {
            getLaptop();
//            loadMoreLaptop();
        } else {
            CheckConnection.showToast(getContext(), "Haven't internet");
            Log.e("==============>", "Haven't internet");

        }
        adapter = new LaptopAdapter(productList, getContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rvLaptop.setLayoutManager(layoutManager);
        rvLaptop.setHasFixedSize(true);
        rvLaptop.setAdapter(adapter);
        adapter.setClickListener(this);
        adapter.notifyDataSetChanged();
    }

//    private void loadMoreLaptop() {
//        loading = true;
//        rvLaptop.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            int pastVisibleItem, visibleItemCount, totalItemCount;
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0) {
//                    visibleItemCount = ((LinearLayoutManager) recyclerView.getLayoutManager()).getChildCount();
//                    totalItemCount = ((LinearLayoutManager) recyclerView.getLayoutManager()).getItemCount();
//                    pastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
//                    if (loading) {
//                        if (((visibleItemCount + pastVisibleItem) >= totalItemCount) && (totalItemCount != 0)) {
//                            loading = false;
////                            ThreadLaptop threadLaptop = new ThreadLaptop();
////                            threadLaptop.start();
//                            Log.e("...", "Last Item Wow!");
//                            //Do pagination.. i.e. fetch new data
//                        }
//                    }
//                }
//            }
//        });
//    }

    //    lấy ra sản phẩm là điện thoại
    private void getLaptop() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Server.urlLaptop, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if ((response != null) && (response.length() > 0)) {
                    for (int i = 0; i < response.length(); i++) {
                        rvLaptop.removeView(itemView);
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idProductType = jsonObject.getInt("IdProductType");
                            idProduct = jsonObject.getInt("IdProduct");
                            nameProduct = jsonObject.getString("nameProduct");
                            priceProduct = jsonObject.getInt("priceProduct");
                            imageProduct = jsonObject.getString("imageProduct");
                            describeProduct = jsonObject.getString("describeProduct");
                            productList.add(new Product(idProduct, nameProduct, priceProduct, imageProduct, describeProduct, idProductType));
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast(getContext(), error.toString());
                Log.e("==============>", error.toString());
            }
        });
        requestQueue.add(arrayRequest);
    }

    @Override
    public void onItemClick(View view, int position) {
        DetailProductFragment fragment = DetailProductFragment.newInstance();
        Bundle bundle = new Bundle();
        Product product = productList.get(position);
        bundle.putString("name", product.getNameProduct());
        bundle.putInt("price", product.getPriceProduct());
        bundle.putString("describe", product.getDescribeProduct());
        bundle.putString("image", product.getImageProduct());
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
