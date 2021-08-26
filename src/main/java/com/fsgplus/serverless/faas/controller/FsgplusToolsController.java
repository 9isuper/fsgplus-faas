package com.fsgplus.serverless.faas.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsgplus.serverless.faas.constant.AESType;
import com.fsgplus.serverless.faas.utils.AESUtil;
import com.fsgplus.serverless.faas.utils.AESUtils;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName FsgplusToolsController @Description @Author admin @Date 8/24/2021
 *            3:03 PM @Version 1.0
 */
@RestController
@RequestMapping("fsgplus/tools")
@Slf4j
public class FsgplusToolsController {

	@RequestMapping(path = "aesEncryptByUserCenter", method = { RequestMethod.GET, RequestMethod.POST })
	public String aesEncrypt(@RequestParam(value = "unicode", required = false) String unicode,
			@RequestParam(value = "secret", required = false) String secret) throws Exception {
		return AESUtils.encrypt(unicode, secret);
	}

	@RequestMapping(path = "corpLoginDataDecrypt", method = { RequestMethod.GET, RequestMethod.POST })
	public String corpLoginDataDecrypt(@RequestParam(value = "data", required = false) String data,
			@RequestParam(value = "aeskey", required = false) String aeskey,
			@RequestParam(value = "aestype", required = false) AESType aestype) {
		return AESUtil.decrypt(data, aeskey, aestype);
	}

	@RequestMapping(path = "corpLoginDataEncrypt", method = { RequestMethod.GET, RequestMethod.POST })
	public String corpLoginDataEncrypt(@RequestParam(value = "unicode", required = true) String unicode,
			@RequestParam(value = "oid", required = false) String oid,
			@RequestParam(value = "registType", required = false) String registType,
			@RequestParam(value = "timestamp", required = false) String timestamp,
			@RequestParam(value = "orgCode", required = false) String orgCode,
			@RequestParam(value = "next", required = false) String next,
			@RequestParam(value = "aeskey", required = false) String aeskey,
			@RequestParam(value = "aestype", required = false) AESType aestype) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("unicode", unicode);
		jsonObject.addProperty("oid", oid);
		if (StringUtils.isNotBlank(registType)) {
			jsonObject.addProperty("registType", registType);
		}
		jsonObject.addProperty("timestamp",
				StringUtils.isBlank(timestamp) ? String.valueOf(System.currentTimeMillis() / 1000) : timestamp);
		jsonObject.addProperty("orgCode", orgCode);
		jsonObject.addProperty("next", next);

		log.info(jsonObject.toString());
		return AESUtil.encrypt(jsonObject.toString(), aeskey, aestype);
	}
}
