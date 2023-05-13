package com.wblazej.controllers;

import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.wblazej.helpers.JSONParser;
import com.wblazej.models.onlinegame.Clan;
import com.wblazej.models.onlinegame.Request;
import com.wblazej.services.OnlineGameService;

import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promise;

public class OnlineGameController {
	public static Promise<HttpResponse> handle(HttpRequest request) {
		return request.loadBody().map($ -> {
			try {
				Request data = JSONParser.parseObject(request, Request.class);

				List<List<Clan>> queue = OnlineGameService.process_queue(data);

				return HttpResponse.ok200()
						.withJson(JSON.toJSONString(queue));
			} catch (JSONException e) {
				return HttpResponse.ofCode(400);
			}
		});
	}
}
