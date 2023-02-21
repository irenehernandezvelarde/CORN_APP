package com.example.cornapp.view.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cornapp.UtilsHTTP;
import com.example.cornapp.databinding.FragmentProfileBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        setupListeners();
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        return binding.getRoot();
    }


    public void setupListeners(){
        binding.fab.setOnClickListener(view -> {

            viewModel.updateUser(
                    binding.profileUserNameValue.getText(),
                    binding.profileUserSurnameValue.getText(),
                    binding.profileContactTelfValue.getText(),
                    binding.profileEmailEditValue.getText()

            );
            JSONObject obj = null;
            try {
                obj = new JSONObject("{}");
                obj.put("type", "profiles");

                UtilsHTTP.sendPOST("http" + "://" + "10.0.2.2:" + 3000 + "/dades", obj.toString(), (response) -> {
                    JSONObject objResponse = null;

                    try {

                        objResponse = new JSONObject(response);
                        if (objResponse.getString("status").equals("OK")) {
                            JSONArray JSONlist = objResponse.getJSONArray("result");
                            JSONObject user = null;
                            for (int i = 0; i < JSONlist.length(); i++) {
                                // Get console information
                                user = JSONlist.getJSONObject(i);
                                // Fill template with console information
                                System.out.println(user);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }

}