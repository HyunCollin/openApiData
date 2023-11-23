package com.api.feature.apilog.entity;

import com.api.core.entity.BaseCreateAt;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
public class ApiLog extends BaseCreateAt {

  @Id
  private Double idx;
  private String reqApiKey;
  private String reqIp;
  private String reqMethod;
  private String reqUrl;
  private String reqParam;
  private String reqBody;
  private String reqHeader;

  private int resStatus;
  private String resData;

  @Builder
  public ApiLog(double idx, String reqApiKey, String reqIp, String reqMethod, String reqUrl, String reqParam, String reqBody, String reqHeader, int resStatus, String resData) {
    this.idx = idx;
    this.reqApiKey = reqApiKey;
    this.reqIp = reqIp;
    this.reqMethod = reqMethod;
    this.reqUrl = reqUrl;
    this.reqParam = reqParam;
    this.reqBody = reqBody;
    this.reqHeader = reqHeader;
    this.resStatus = resStatus;
    this.resData = resData;
  }

  public void setUpResponse(int status, String responseBody) {
    this.resStatus = status;
    this.resData = responseBody;
  }

  public void setUpException(int value, String message) {
    this.resStatus = value;
    this.resData = message;
  }

  public void rejectedByBadRequest() {
    if (this.reqApiKey == null) {
      this.reqApiKey = "rejectedByBadRequest";
    }
  }

  public void setUpApiKeyAndToken(String reqApiKey) {
    this.reqApiKey = reqApiKey;
  }

  public void setUpWhenApiKeyIsNull(String message) {
    if (this.reqApiKey == null) {
      this.reqApiKey = message;
    }
  }

  public void setUpReqBody(String reqBody) {
    this.reqBody = reqBody;
  }

  public boolean existHealthCheckServerIp() {
    return "10.10.1.105".equals(this.reqIp) || "10.10.1.250".equals(this.reqIp);
  }

  public String printDetail() {
    return String.format(" reqApiKey %s, reqIp %s, reqMethod %s, reqUrl %s, reqParam %s, reqBody %s, reqHeader %s, resStatus %s, resData %s"
        , this.reqApiKey, this.reqIp, this.reqMethod, this.reqUrl, this.reqParam, this.reqBody, this.reqHeader, this.resStatus, this.resData);
  }
}
