package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/*
 * RESPOSTAS SOBRE ADAPTERS PERSONALIZADOS:
 *
 * 1. Como melhora a apresentação/usabilidade?
 *    R: Um Adapter personalizado permite controle total sobre o layout de cada linha.
 *    Ao contrário do ArrayAdapter padrão que exibe apenas uma String, aqui podemos combinar
 *    Imagens (ImageView), Textos variados (TextView) e outros componentes em um único item.
 *    Isso melhora a usabilidade pois informações visuais (como a foto de um Pet ou Planeta)
 *    ajudam o usuário a identificar o item mais rápido do que apenas lendo um texto.
 *
 * 2. Como criar um adapter para PETs?
 *    R: A lógica é a mesma usada aqui para "Planet".
 *       - Crie uma classe "Pet" (modelo) com atributos (id, nome, foto).
 *       - Crie um XML de layout para o item (ex: item_pet.xml) com ImageView e TextView.
 *       - Crie a classe "PetAdapter" estendendo ArrayAdapter<Pet>.
 *       - No construtor, receba a lista de Pets.
 *       - Sobrescreva o método getView() para vincular os dados do Pet ao layout.
 */
public class PlanetAdapter extends ArrayAdapter<Planet> {

    public PlanetAdapter(Context context, List<Planet> planets) {
        super(context, 0, planets);
    }

    /*
     * 3. Qual método é fundamental? (getView)
     *    R: O método fundamental é o getView().
     *
     *    EXPLICAÇÃO E FUNCIONAMENTO:
     *    Ele é responsável por criar e retornar a visualização (View) de CADA item da lista.
     *    Sempre que a lista precisa mostrar um elemento na tela, ela chama este método.
     *
     *    Passo a passo do seu funcionamento (Exemplificado):
     *    1. Reciclagem: Verifica se uma View antiga (convertView) pode ser reutilizada para economizar memória.
     *    2. Inflar: Se não houver View para reutilizar, cria uma nova a partir do XML (LayoutInflater).
     *    3. Vinculação: Busca os componentes visuais (ImageView, TextView) pelo ID.
     *    4. Dados: Obtém o objeto de dados da posição atual (ex: o objeto Pet na posição 0).
     *    5. População: Preenche os componentes com os dados (ex: coloca a foto e o nome do Pet).
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Verificação de reciclagem (Performance)
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.template_item, parent, false);
        }

        // Recuperação dos componentes da tela
        ImageView img = convertView.findViewById(R.id.imgPlanet);
        TextView name = convertView.findViewById(R.id.namePlanet);

        // Obtenção do dado atual
        Planet planet = getItem(position);

        // População dos dados (Exemplo de funcionamento prático)
        if(planet != null) {
            // Se fosse um objeto PET, seria:
            // img.setImageResource(pet.getFoto());
            // name.setText(pet.getNome());
            img.setImageResource(planet.image);
            name.setText(planet.name);
        }

        return convertView;
    }
}
