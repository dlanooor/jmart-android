package com.ronaldJmartBO.jmart_android.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ronaldJmartBO.R;
import com.ronaldJmartBO.jmart_android.model.ProductCategory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Filter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Filter extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static boolean isFiltered = false;
    public static String nameFilter;
    public static int lowestPriceFilter, highestPriceFilter;
    public static boolean newFilter, usedFilter;
    public static ProductCategory categoryFilter;

    public Filter() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Filter.
     */

    public static Filter newInstance(String param1) {
        Filter fragment = new Filter();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        final EditText filterNameInput = (EditText) view.findViewById(R.id.filterNameInput);
        final EditText lowestPriceInput = (EditText) view.findViewById(R.id.lowestPriceInput);
        final EditText highestPriceInput = (EditText) view.findViewById(R.id.highestPriceInput);
        final CheckBox filterNew = (CheckBox) view.findViewById(R.id.filterNew);
        final CheckBox filterUsed = (CheckBox) view.findViewById(R.id.filterUsed);
        final Spinner spinnerProductCategory = (Spinner) view.findViewById(R.id.spinnerProductCategory);

        final Button clearButton = (Button) view.findViewById(R.id.createProductClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                filterNameInput.getText().clear();
                lowestPriceInput.getText().clear();
                highestPriceInput.getText().clear();
                filterNew.setChecked(false);
                filterUsed.setChecked(false);
            }
        });

        final Button applyButton = (Button) view.findViewById(R.id.createProductApply);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFiltered = true;

                if(filterNameInput.getText().toString().isEmpty())
                    Toast.makeText(getContext(), "Please Fill In Filter Name", Toast.LENGTH_SHORT).show();
                else if(lowestPriceInput.getText().toString().isEmpty())
                    Toast.makeText(getContext(), "Please Fill In Lowest Price Filter", Toast.LENGTH_SHORT).show();
                else if(highestPriceInput.getText().toString().isEmpty())
                    Toast.makeText(getContext(), "Please Fill In Highest Price Filter", Toast.LENGTH_SHORT).show();
                else if(Double.parseDouble(highestPriceInput.getText().toString()) < Double.parseDouble(lowestPriceInput.getText().toString()))
                    Toast.makeText(getContext(), "Highest Price should be Higher than Lowest Price", Toast.LENGTH_SHORT).show();
                else if(!filterNew.isChecked() && !filterUsed.isChecked())
                    Toast.makeText(getContext(), "Please Fill In Product Condition Filter", Toast.LENGTH_SHORT).show();
                else {
                    nameFilter = filterNameInput.getText().toString();
                    lowestPriceFilter = Integer.parseInt(lowestPriceInput.getText().toString());
                    highestPriceFilter = Integer.parseInt(highestPriceInput.getText().toString());

                    if(filterNew.isChecked())
                        newFilter = true;
                    else
                        newFilter = false;
                    if(filterUsed.isChecked())
                        usedFilter = true;
                    else
                        usedFilter = false;

                    categoryFilter = ProductCategory.valueOf(spinnerProductCategory.getSelectedItem().toString());

                    Toast.makeText(getActivity(), "Filter Applied", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}