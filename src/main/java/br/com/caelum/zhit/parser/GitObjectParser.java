package br.com.caelum.zhit.parser;


public interface GitObjectParser<T> {

	T parse(String objectContent);

}
