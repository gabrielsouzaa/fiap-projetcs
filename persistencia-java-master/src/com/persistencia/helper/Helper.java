package com.persistencia.helper;

import java.util.List;


public interface Helper<T> {
	
	public String update(T object);
	public Object find(String nomeColuna, String parametroConsulta);
	public List<T> findAll();
	
}
