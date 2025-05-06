package com.plantapps.MessageProducer.model;

public class Message
{
	private Long id;
	private String data;
	public Message()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public Message(Long id, String data)
	{
		super();
		this.id = id;
		this.data = data;
	}
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getData()
	{
		return data;
	}
	public void setData(String data)
	{
		this.data = data;
	}
}
