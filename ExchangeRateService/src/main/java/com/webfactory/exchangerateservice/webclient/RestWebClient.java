package com.webfactory.exchangerateservice.webclient;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.webfactory.exchangerateservice.webclient.exceptions.NullRequestBodyException;
import com.webfactory.exchangerateservice.webclient.exceptions.NullRequestTypeException;

public class RestWebClient {
	private OkHttpClient client;
	private String url;
	private String data;
	private HashMap<String, String> headers;
	private HashMap<String, String> parameters;
	private RequestType requestType;
	private DataType dataType;
	private Integer connectionTimeout;

	public RestWebClient(OkHttpClient client) {
		this.client = client;
	}

	public RestWebClient(String url, String data, HashMap<String, String> parameters,
			HashMap<String, String> headers) {
		this.url = url;
		this.data = data;
		this.parameters = parameters;
		this.headers = headers;
	}

	public RestWebClient() {

	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public HashMap<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setClient(OkHttpClient client) {
		this.client = client;
	}

	public OkHttpClient getClient() {
		return this.client;
	}

	public String sendRequest() throws Exception {
		Request.Builder builder = getRequestBuilder();
		if (this.client == null) {
			this.client = new OkHttpClient();
		}
		this.client.setConnectTimeout(this.connectionTimeout != null ? this.connectionTimeout : 20, TimeUnit.SECONDS);
		Response response = this.client.newCall(builder.build()).execute();
		return response.body().string();
	}

	private Request.Builder getRequestBuilder() throws Exception {
		RequestBody body = null;
		Request.Builder builder = null;
		if (this.requestType == RequestType.POST) {
			body = getRequestBody();
			builder = new Request.Builder().url(this.url).post(body);
		} else if (this.requestType == RequestType.GET || this.requestType == RequestType.DELETE) {
			HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
			if (parameters != null) {
				Iterator<Map.Entry<String, String>> entries = parameters.entrySet().iterator();
				while (entries.hasNext()) {
					Map.Entry<String, String> entry = entries.next();
					urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
				}
			}
			if (this.requestType == RequestType.DELETE) {
				builder = new Request.Builder().url(urlBuilder.build().toString()).delete();
			} else {
				builder = new Request.Builder().url(urlBuilder.build().toString()).get();
			}
		} else if (this.requestType == RequestType.PATCH) {
			HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
			if (parameters != null) {
				Iterator<Map.Entry<String, String>> entries = parameters.entrySet().iterator();
				while (entries.hasNext()) {
					Map.Entry<String, String> entry = entries.next();
					urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
				}
			}
			body = getRequestBody();
			builder = new Request.Builder().url(urlBuilder.build().toString()).patch(body);
		} else {
			throw new NullRequestTypeException("Request Type cannot be null");
		}
		if (this.headers != null) {
			Set<Entry<String, String>> set = headers.entrySet();
			Iterator<Entry<String, String>> iterator = set.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = (Entry<String, String>) iterator.next();
				builder.addHeader(entry.getKey().toString(), entry.getValue().toString());
			}
		}
		return builder;
	}

	private RequestBody getRequestBody() throws Exception {
		MediaType mType = null;
		if (this.dataType != null) {
			if (this.dataType == DataType.JSON) {
				mType = MediaType.parse("application/json");
			} else if (this.dataType == DataType.XML) {
				mType = MediaType.parse("application/xml");
			} else {

			}
		} else {
			mType = MediaType.parse("application/json");
		}
		if (this.data == null) {
			throw new NullRequestBodyException("Request Body must not be null");
		}
		RequestBody body = RequestBody.create(mType, data);
		return body;
	}
}
