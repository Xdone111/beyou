package byou.yadun.wallet.wallet.Transaction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import byou.yadun.wallet.R;

/**
 * Created by Administrator on 2017/8/30.
 */

public class ItemFragmentCurrentEntrust extends Fragment {
    private Spinner spinnerA;//全部
    private Spinner spinnerN;//限时委托
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //动态找到布局文件，再从这个布局中find出TextView对象
        View contextView = inflater.inflate(R.layout.fragment_item_current, container, false);
        spinnerA= (Spinner) contextView.findViewById(R.id.spinnerAll);
        spinnerN= (Spinner) contextView.findViewById(R.id.spinnerNow);
        final String arr[]=new String[]{"全部", "卖出", "买入"};
        final String arr1[]=new String[]{"限时委托", "卖出", "买入"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arr);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arr1);
        spinnerA.setAdapter(arrayAdapter);
        spinnerN.setAdapter(arrayAdapter1);

        //注册事件
        spinnerA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner spinner=(Spinner) parent;
                Toast.makeText(getActivity(), "xxxx"+spinner.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "没有改变的处理", Toast.LENGTH_LONG).show();
            }

        });
        spinnerN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner spinner=(Spinner) parent;
                Toast.makeText(getActivity(), "xxxx"+spinner.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "没有改变的处理", Toast.LENGTH_LONG).show();
            }

        });
        return contextView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
