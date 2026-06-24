package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.AvaliarRestauranteActivity;
import com.example.myapplication.DetalhesDoRestauranteActivity;
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

        binding.btnVerMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetalhesDoRestauranteActivity.class);
                intent.putExtra("restaurante", restaurante);
                getContext().startActivity(intent);
            }
        });

        binding.btnAvaliarRestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AvaliarRestauranteActivity.class);
                intent.putExtra("restaurante", restaurante);
                getContext().startActivity(intent);
            }
        });

        return convertView;

    }
}
