package br.com.caelum.zhit.factory;


public interface GitObjectFactory<T> {

	T build(String objectContent);

}
