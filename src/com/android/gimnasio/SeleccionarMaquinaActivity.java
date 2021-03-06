package com.android.gimnasio;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.android.gimnasio.R.id;
import com.android.gimnasio.api.Maquina;
import com.android.gimnasio.api.TipoEjercicio;
import com.android.gimnasio.api.Usuario;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.DropBoxManager.Entry;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class SeleccionarMaquinaActivity extends Activity{

	private Maquina maquina;
	private TextView titulo;
	private ArrayList<Integer> ids_maquinas,ids_maquinas_seleccionadas;
	private LinearLayout l1;
	private int num_row=0,id_usuario_loggeado;
	private Button siguiente;
	private Usuario usuario;
	public void onCreate(Bundle savedInstanceState)
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seleccionar_maquina);
		l1=(LinearLayout)findViewById(R.id.linear);
		l1.addView(desloggear(),num_row);
		num_row+=1;
		usuario=new Usuario(this);
		maquina=new Maquina(this);
		ids_maquinas=maquina.getIdsMaquinas();
		id_usuario_loggeado=usuario.getIdUsuarioLoggeado();
		titulo=new TextView(this);
		siguiente=new Button(this);
		siguiente.setLayoutParams(new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		siguiente.setText("Seleccionar Tipo Ejercicio");
		ids_maquinas_seleccionadas=new ArrayList<Integer>();
		siguiente.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//si no selecciona maquinas no pasa a la siguiente actividad y envia un mensaje
				if(ids_maquinas_seleccionadas.size()!=0)
				{

					//pasa a la siguente actividad
					Intent i=new Intent(getApplicationContext(), MaquinaEjercicioActivity.class);
					i.putIntegerArrayListExtra("ids_maquinas_seleccionadas", ids_maquinas_seleccionadas);
					startActivity(i);
				}else{
					Toast.makeText(getApplicationContext(), "Seleccione alguna maquina", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		//si el numero de elementos de la lista es impar,entonces num_filas teendra numero par
		int num_filas=(ids_maquinas.size()%2==1)?ids_maquinas.size()-1:ids_maquinas.size();
		int resto=ids_maquinas.size()%2;
		this.llenar_encabezado();
		int indice_che=0;
		int indice_img=0;
		Log.d("ids",""+ids_maquinas.toString());
		for (int fila = 0; fila <num_filas; fila++) 
			{
			Log.d("fila= ",fila+"");
					LinearLayout l2=crearLinearLayout();
					for(int col=0;col<2;col++)//2 maquinas por fila
					{
						switch ((fila%2)) {
						case 0:
							Log.d("checkbox= "+indice_che,(fila%2)+"");

								l2.addView(this.crearCheckBox(indice_che));
								indice_che+=1;
							
							break;
						case 1:
							Log.d("image= "+indice_img,(fila%2)+"");

								l2.addView(this.crearImageView(indice_img));
								indice_img+=1;
								break;
						}	 
				    	
					}
					
					 l1.addView(l2,num_row);
					 num_row+=1;
			}
		if(resto==1)
		{
			LinearLayout l2=crearLinearLayout();
			l2.addView(this.crearCheckBox(indice_che));
			LinearLayout l3=crearLinearLayout();
			l3.addView(this.crearImageView(indice_img));
			 l1.addView(l2,num_row);
			 num_row+=1;
			 l1.addView(l3,num_row);
			 num_row+=1;
		}
		 l1.addView(new TextView(this),num_row);
		 num_row+=1;
		 l1.addView(siguiente,num_row);
		 num_row+=1;
	}

	public void onBackPressed(){
		Intent i=new Intent(this, HomeActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
	}
	public LinearLayout crearLinearLayout()
	{
		LinearLayout l2=new LinearLayout(this);
	    l2.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	    l2.setOrientation(LinearLayout.HORIZONTAL);
	    return l2;
	}
	public CheckBox crearCheckBox(int indice_maquina)
	{
	int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 140, getResources().getDisplayMetrics());
	int	heigth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
	CheckBox checkBox = new CheckBox(this);
	//checkBox.setGravity(Gravity.CENTER);
	checkBox.setId(ids_maquinas.get(indice_maquina));
	checkBox.setText(maquina.getNombre(ids_maquinas.get(indice_maquina)));
	checkBox.setTextSize(10);
	checkBox.setTextColor(Color.GRAY);
	LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, heigth);
	lp.setMargins(20, 0, 0, 0);
	checkBox.setLayoutParams(lp);
	checkBox.setOnClickListener(new View.OnClickListener() {
	      @Override
	      public void onClick(View v) {
	                //is chkIos checked?
	        if (((CheckBox) v).isChecked()) {
	        	 Toast.makeText(getBaseContext(),  "checkeado "+v.getId(),Toast.LENGTH_SHORT).show();
                 ids_maquinas_seleccionadas.add(v.getId());  
	        }else {
	        	Toast.makeText(getBaseContext(),  "Descheckeado "+v.getId(),Toast.LENGTH_SHORT).show();
                ids_maquinas_seleccionadas.remove((Object)v.getId()); 
	        }

	      }
	    });
	
	return checkBox;	
	}
	public ImageView crearImageView(int indice_maquina)
	{
		int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 140, getResources().getDisplayMetrics());
		int	heigth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 140, getResources().getDisplayMetrics());
		ImageView imagen = new ImageView(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, heigth);
		lp.setMargins(20, 0, 0, 0);	     
		imagen.setLayoutParams(lp);
	       imagen.setImageBitmap(maquina.getImagen(ids_maquinas.get(indice_maquina), this));
	        return imagen;
	}
	public void llenar_encabezado()
	{
		titulo.setBackgroundResource(R.drawable.titulo_seleccion_de_maquinas);
		titulo.setTextSize(50);
		titulo.setPadding(20,2,0,0);
		l1.addView(titulo,num_row,new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		num_row+=1;
		
	}
	public TextView desloggear()
	{
		TextView text=new TextView(this);
		Usuario u=new Usuario(this);
		int id_usuario_loggeado=u.getIdUsuarioLoggeado();
		String nombre=u.getNombre(id_usuario_loggeado);
		text.setText(nombre.substring(0,1).toUpperCase()+nombre.substring(1)+"\tX Cerrar Sesion");
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
		lp.setMargins(100, 5, 0, 5);
		text.setLayoutParams(lp);
		text.setTextSize(18);
		text.setTextColor(Color.parseColor("#DF0101"));
		text.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//lo envio a login activity
				Toast.makeText(getApplicationContext(),"Usuario desloggeado",Toast.LENGTH_SHORT).show();
				Intent i=new Intent(getApplicationContext(), LoginActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
		return text;
		
	}
	
	public TextView datosUsuario()
	{
		TextView text=new TextView(this);
		Usuario u=new Usuario(this);
		int id_usuario_loggeado=u.getIdUsuarioLoggeado();
		String nombre=u.getNombre(id_usuario_loggeado);
		String peso=u.getPeso(id_usuario_loggeado)+"";
		String edad =u.getEdad(id_usuario_loggeado);
		String estatura=""+u.getEstatura(id_usuario_loggeado);
		String imc =u.getImc(id_usuario_loggeado);
		String sexo=u.getSexo(id_usuario_loggeado);
		text.setText("Datos del Usuario: \n\n"+
					"Nombre: "+nombre+"\nPeso: "+peso+"\n"+
					"Estatura: "+estatura+"\nEdad: "+edad+"\n"+
					"Sexo: "+sexo+"\nImc: "+imc+"\n");
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
		lp.setMargins(10, 5, 2, 10);
		text.setLayoutParams(lp);
		text.setTextSize(15);
		text.setTextColor(Color.parseColor("#29088A"));
		return text;
		
	}
}
