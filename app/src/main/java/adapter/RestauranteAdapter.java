package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.databinding.ItemRestauranteBinding;

import java.util.ArrayList;

import model.Restaurante;

public class RestauranteAdapter extends ArrayAdapter<Restaurante> {

    public RestauranteAdapter(Context context, ArrayList<Restaurante> restaurantes) {
        super(context, 0, restaurantes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Restaurante restaurante = getItem(position);

        ItemRestauranteBinding binding;

        if (convertView == null){
            binding = ItemRestauranteBinding.inflate(LayoutInflater.from(getContext()),parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else{
            binding = (ItemRestauranteBinding) convertView.getTag();
        }

        binding.txtViewNomeRestaurante.setText(restaurante.getNomeRestaurante());
        binding.txtViewEnderecoRestaurante.setText(restaurante.getEnderecoRestaurante());
        binding.idRestaurante.setText(Integer.toString(restaurante.getIdRestaurante()));

        return convertView;
    }
}
