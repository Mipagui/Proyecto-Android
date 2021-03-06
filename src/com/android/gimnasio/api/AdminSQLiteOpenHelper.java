package com.android.gimnasio.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

	private static String nombre_db="gimnasio";

	private HashMap<String, String> columnas_usuario,columnas_maquina;
	
	public AdminSQLiteOpenHelper(Context context,CursorFactory factory, int version) 
	{
		super(context,nombre_db , factory, version);
		 
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		Log.d("Create uSUARIO","");
		db.execSQL(this.getCreateTable(Usuario.nombreTabla, Usuario.getColumnas()));
		Log.d("Create maquina",""+ Maquina.getColumnas());
		db.execSQL(this.getCreateTable(Maquina.nombreTabla, Maquina.getColumnas()));
		Log.d("Create tipoejercicio","");
		
		db.execSQL(this.getCreateTable(TipoEjercicio.nombreTabla, TipoEjercicio.getColumnas()));
		
		db.execSQL(this.getCreateTable(TipoEjercicioUsuario.nombreTabla, TipoEjercicioUsuario.getColumnas()));
		db.execSQL(this.getCreateTable(RequerimientoEjercicio.nombreTabla, RequerimientoEjercicio.getColumnas()));

		
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) 
	{
		db.execSQL("drop table if exists "+RequerimientoEjercicio.nombreTabla);
		db.execSQL("drop table if exists "+TipoEjercicioUsuario.nombreTabla);
		db.execSQL("drop table if exists "+TipoEjercicio.nombreTabla);
		db.execSQL("drop table if exists "+Usuario.nombreTabla);
		db.execSQL("drop table if exists "+Maquina.nombreTabla);
		db.execSQL(this.getCreateTable(Usuario.nombreTabla, Usuario.getColumnas()));
		db.execSQL(this.getCreateTable(Maquina.nombreTabla, Maquina.getColumnas()));
		db.execSQL(this.getCreateTable(TipoEjercicio.nombreTabla, TipoEjercicio.getColumnas()));
		db.execSQL(this.getCreateTable(TipoEjercicioUsuario.nombreTabla, TipoEjercicioUsuario.getColumnas()));
		db.execSQL(this.getCreateTable(RequerimientoEjercicio.nombreTabla, RequerimientoEjercicio.getColumnas()));

		

	}

	public String getCreateTable(String tabla,HashMap<String,String> columnas)
	{

		Iterator<String> clave_iterador=columnas.keySet().iterator();
		String create_table="create table "+tabla+" ( ";
		List<String> fks=new ArrayList<String>();
		while(clave_iterador.hasNext())
		{
			String clave=clave_iterador.next();
			if(!(columnas.get(clave).contains("FOREIGN") || columnas.get(clave).contains("foreign")))
				{
				create_table+=clave+" "+columnas.get(clave)+",";	
				}else{
				fks.add(clave);	
				}
		}
		for(int i=0;i<fks.size();i++)
		{
			create_table+=fks.get(i)+" "+columnas.get(fks.get(i))+",";
		}
		create_table=create_table.substring(0, create_table.length()-1)+")";
		Log.d("Create table "+tabla+"= ",create_table);
		return create_table;
	}

	public String getNombreDb()
	{
		return nombre_db;
		
	}
}
