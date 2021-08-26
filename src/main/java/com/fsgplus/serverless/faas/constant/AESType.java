package com.fsgplus.serverless.faas.constant;


public enum AESType {
  AES_128(128),

  AES_192(192),

  AES_256(256),
  ;

  private int type;

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  AESType(int type) {
    this.type = type;
  }
}
